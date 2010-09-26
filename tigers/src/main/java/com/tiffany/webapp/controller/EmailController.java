package com.tiffany.webapp.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailException;
import org.springframework.security.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.tiffany.Constants;
import com.tiffany.model.Role;
import com.tiffany.model.User;
import com.tiffany.service.UserManager;

public class EmailController extends BaseFormController {
	private final Log log = LogFactory.getLog(EmailController.class);
	public static final int ADMIN = 1;
    public static final int OFFICER = 2;
    public static final int CONTRACTOR = 3;
    public static final int LABORATORY = 4;
    private UserManager userManager;
    
    public void setUserManager(UserManager userManager) {
    	this.userManager = userManager;
    }  
    
    public EmailController() {
    	setCommandClass(Email.class);
    	setCommandName("email");
    }
    //====================== showForm =============================================
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response,
            BindException errors) throws Exception {
    	log.debug("");
    	if (!isFormSubmission(request)) {
    		log.debug("new form");
	    	if (request.getParameter("id") == null) {
	    		saveError(request, "Please specify a user for email.");
	    		return new ModelAndView("redirect:mainMenu.html");
	    	} else {
	    		User target;
	    		String userId = request.getParameter("id");
	    		try {
	        		target = userManager.getUser(userId);
	        	} catch (Exception e) {
	        		saveError(request, "User for email not exist.");
	            	return new ModelAndView("redirect:mainMenu.html");
	        	}
	        	//===== contractor =====
		        if (request.isUserInRole("ROLE_CONTRACTOR")) {
		        	if (!isOk(CONTRACTOR, request.getRemoteUser(), target)) {
			        	response.sendError(HttpServletResponse.SC_FORBIDDEN);
			            throw new AccessDeniedException("You do not have permission to view the user with id '." + 
			            		request.getParameter("id") + "'");
		        	}
		        //===== officer =====
		        } else if (request.isUserInRole("ROLE_OFFICER")) {
		        	if (!isOk(OFFICER, request.getRemoteUser(), target)) {
			        	response.sendError(HttpServletResponse.SC_FORBIDDEN);
			            throw new AccessDeniedException("You do not have permission to view the user with id '." + 
			            		request.getParameter("id") + "'");
		        	}
		        //===== laboratory =====
		        } else if (request.isUserInRole("ROLE_LABORATORY")) {
		        	if (!isOk(LABORATORY, request.getRemoteUser(), target)) {
			        	response.sendError(HttpServletResponse.SC_FORBIDDEN);
			            throw new AccessDeniedException("You do not have permission to view the user with id '." + 
			            		request.getParameter("id") + "'");
		        	}
		        //===== invalid user =====
		        } else if (!request.isUserInRole(Constants.ADMIN_ROLE)) {	
		        	response.sendError(HttpServletResponse.SC_FORBIDDEN);
		            throw new AccessDeniedException("You do not have permission to view the user with id '." + 
		            		request.getParameter("id") + "'");
		        }
	    	}
    	} 
    	
    	return super.showForm(request, response, errors);
	}
    //====================== formBackingObject ================================
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	log.debug("");
    	Email email = new Email();
    	if (!isFormSubmission(request)) {
    		log.debug("new form");
		    String userId = request.getParameter("id");
		    if (userId != null) email.setTo(userManager.getUser(userId).getUsername());
    	}
    	return email;
    }
    //====================== referenceData ================================
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
    	log.debug("");
		Email email = (Email)command;
		Map<String, Object> refData = new HashMap();
		try {
			User user = userManager.getUserByUsername(email.getTo());
			refData.put("user", user);
		} catch (Exception e) {
			log.debug("exception");
		}
		return refData;
	}
    //======================= onSubmit =========================================
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
    		Object command, BindException errors) throws Exception {
    	Locale locale = request.getLocale();
    	String success = getSuccessView();
    	Email email = (Email)command;
    	//===== message model ======
		Map model = new HashMap();
		model.put("message", email.getContent());
		User fromUser = userManager.getUserByUsername(request.getRemoteUser());
		User toUser = userManager.getUserByUsername(email.getTo());
		model.put("fromUser", fromUser);
		//==========================
		log.debug("fromUser:"+fromUser.getUsername()+" toUser:"+toUser.getUsername());
		try {
    		sendUserMessage(toUser, email.getSubject(), model);
    		request.getSession().removeAttribute("sendTo");
	    } catch (MailException me) {
	    	saveError(request, getText("email.failed", locale));
	    	ModelAndView mv = new ModelAndView("sendemail", getCommandName(), command);
	    	mv.addObject("user", userManager.getUserByUsername(email.getTo()));
	    	return mv;
	    } catch (Exception e) {
	    	log.debug("Got exception:"+e.toString());
	    }
    	
    	saveMessage(request, getText("email.success", locale));
    	return new ModelAndView(success, getCommandName(), command);
    }
  //================================================================================
    public boolean isOk(int role, String username, User target) {
    	switch (role) {
    	case OFFICER:
    		if (!target.getRoles().contains(new Role("ROLE_CONTRACTOR"))) return false;
    		break;
    	case CONTRACTOR:
    		if (!userManager.isMyLaboratory(username, target)) return false;
    		break;
    	case LABORATORY:
    		if (!userManager.isMyContractor(username, target)) return false;
    		break;
    	}
    	return true;
    }
  //==================================================================================
  
}
