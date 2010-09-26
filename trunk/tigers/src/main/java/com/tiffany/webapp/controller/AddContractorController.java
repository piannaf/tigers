package com.tiffany.webapp.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.MailException;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationTrustResolver;
import org.springframework.security.AuthenticationTrustResolverImpl;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.tiffany.Constants;
import com.tiffany.model.Address;
import com.tiffany.model.Role;
import com.tiffany.model.User;
import com.tiffany.service.RoleManager;
import com.tiffany.service.UserExistsException;
import com.tiffany.webapp.util.RequestUtil;

public class AddContractorController extends BaseFormController {
	 private RoleManager roleManager;

	 public void setRoleManager(RoleManager roleManager) {
	     this.roleManager = roleManager;
	 }
	 public AddContractorController() {
	     setCommandName("user");
	     setCommandClass(User.class);
	 }
//==================================================================================
	public ModelAndView processFormSubmission(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Object command,
                                              BindException errors)
    throws Exception {
        if (request.getParameter("cancel") != null) {
            return new ModelAndView(getCancelView());
        }
	    return super.processFormSubmission(request, response, command, errors);
    }
//====================================================================================
    protected ModelAndView showForm(HttpServletRequest request,
                                    HttpServletResponse response,
                                    BindException errors)
    throws Exception {

    // not officer not adding
    	if (!request.isUserInRole("ROLE_OFFICER")) {
    		response.sendError(HttpServletResponse.SC_FORBIDDEN);
    		log.warn("User '" + request.getRemoteUser() + "' is trying to add contractor '" + request.getParameter("id") + "'");
    		throw new AccessDeniedException("You do not have permission to add contractor.");
    	} else {
    		String userId = request.getParameter("id");
    		if (userId != null && !userId.trim().equals("")) {
    			User user;
    			try {
    				user = getUserManager().getUser(userId);
    			} catch (Exception e) {
    				log.debug("exception");
    				throw new AccessDeniedException("You do not have permission to modify other users.");
    			}
	    		if (!user.getRoles().contains(new Role("ROLE_CONTRACTOR"))) {
	    			throw new AccessDeniedException("You do not have permission to modify other users.");
	    		}
    		}
    	}
        return super.showForm(request, response, errors);
    }
//=======================================================================================
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	log.debug("formBackingObject");
    	User user = new User();
    	String userId = request.getParameter("id");
    	if (userId != null) {
    		try {
    			user = getUserManager().getUser(userId);
    			user.setConfirmPassword(user.getPassword());
    		} catch (Exception e) {
    			log.debug("exception");
    		}
    	} else {
	        user.resetPassword();
	        Address address = new Address();
	        address.setCountry("AU");
	        user.setAddress(address);
	        user.setEnabled(true);
	        user.setConfirmPassword(user.getPassword());
    	}
    	return user;
    }    
  //======================================================================================
    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
    throws Exception {
        log.debug("entering 'onSubmit' method...");

        User user = (User) command;
        user.getRoles().clear();
        user.addRole(roleManager.getRole("ROLE_CONTRACTOR"));
        boolean isNew = isAdd(user);
        //user.getRoles().clear();
        //user.addRole(new Role("ROLE_CONTRACTOR"));
        Locale locale = request.getLocale();

        Integer originalVersion = user.getVersion();
        log.debug("saving user...");
        try {
            getUserManager().saveUser(user);
        } catch (AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (UserExistsException e) {
            errors.rejectValue("username", "errors.existing.user",
                               new Object[] {user.getUsername(), user.getEmail()}, "duplicate user");

            // redisplay the unencrypted passwords
            user.setPassword(user.getConfirmPassword());
            // reset the version # to what was passed in
            user.setVersion(originalVersion);
            return showForm(request, response, errors);
        }
        //=================================================================
        // send email to new user
        if (isNew) {
	        log.debug("sending email...");
	        message.setSubject(getText("signup.email.subject", locale));                
	        Map<String, Serializable> model = new HashMap<String, Serializable>();
	        model.put("message", getText("signup.email.message", locale));                
	        try {
	            //sendUserMessage(user, getText("signup.email.message", locale), RequestUtil.getAppURL(request));
	            sendUserMessage(user, model);
	        } catch (MailException me) {
	            saveError(request, getText("email.failed", locale));
	            log.debug("MailException");
	        } catch (Exception e) {
	            log.debug("can't send email");
	        }
	        log.debug("finish sending");
	        saveMessage(request, getText("user.added", user.getCompanyName(), locale));
        } else {
        	saveMessage(request, "Contractor '" + user.getUsername() + "' has been updated.");
        }
        //==================================================================  
        
        // return to main Menu
        return new ModelAndView(getSuccessView());
    }	
//====================================================================================================    
    /* before validation.
     * if it is a cancel request, then don't need to validate the command.
     */
//    protected void onBind(HttpServletRequest request, Object command) throws Exception {
//    	
//    }    
    public boolean isAdd(User user) {
    	if (user.getId() == null) return true;
    	return getUserManager().getUser(user.getId().toString()) == null;
    }
    
}
