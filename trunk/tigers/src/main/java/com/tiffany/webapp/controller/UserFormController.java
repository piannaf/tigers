package com.tiffany.webapp.controller;

import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationTrustResolver;
import org.springframework.security.AuthenticationTrustResolverImpl;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.apache.commons.lang.StringUtils;
import com.tiffany.Constants;
import com.tiffany.model.Role;
import com.tiffany.model.User;
import com.tiffany.model.Address;
import com.tiffany.service.RoleManager;
import com.tiffany.service.UserExistsException;
import com.tiffany.service.UserManager;
import com.tiffany.webapp.util.RequestUtil;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.mail.MailException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Implementation of <strong>SimpleFormController</strong> that interacts with
 * the {@link UserManager} to retrieve/persist values to the database.
 *
 * <p><a href="UserFormController.java.html"><i>View Source</i></a>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class UserFormController extends BaseFormController {
    private RoleManager roleManager;

    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }
    
    public UserFormController() {
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
        	// from list
            if (StringUtils.equals(request.getParameter("from"), "list")) {
                return new ModelAndView(getCancelView());
            } else {
                return new ModelAndView(getSuccessView());
            }
        }

        return super.processFormSubmission(request, response, command, errors);
    }
//======================================================================================
    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
    throws Exception {
        log.debug("entering 'onSubmit' method...");

        User user = (User) command;
        Locale locale = request.getLocale();
        //====== Delete =============
        if (request.getParameter("delete") != null) {
            getUserManager().removeUser(user.getId().toString());
            saveMessage(request, getText("user.deleted", user.getCompanyName(), locale));

            return new ModelAndView(getSuccessView());
        //====== Update or Add ==========    
        } else {
            /** can change role? **/
            // only attempt to change roles if user is admin for other users,
            // formBackingObject() method will handle populating
            if (request.isUserInRole(Constants.ADMIN_ROLE)) { // is admin
                String[] userRoles = request.getParameterValues("userRoles");
                if (userRoles != null) {
                	if (userRoles.length == 0) {
                		errors.rejectValue("roleList", "errors.required", new Object [] {"Role"}, null);
                		return showForm(request, response, errors);
                	} else if (userRoles.length > 1) {
                		errors.rejectValue("roleList", "errors.toManyRoles.user");
                		return showForm(request, response, errors);
                	} else {
                		user.getRoles().clear();
	                    for (String roleName : userRoles) {
	                        user.addRole(roleManager.getRole(roleName));// add or update role
	                    }
                	}
                } 
            }

            Integer originalVersion = user.getVersion();
            
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

            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                saveMessage(request, getText("user.saved", user.getCompanyName(), locale));

                // return to main Menu
                return new ModelAndView(new RedirectView("mainMenu.html"));
            } else {
                if (StringUtils.isBlank(request.getParameter("version"))) {
                    saveMessage(request, getText("user.added", user.getCompanyName(), locale));

                    // Send an account information e-mail
                    message.setSubject(getText("signup.email.subject", locale));

                    try {
                        sendUserMessage(user, getText("newuser.email.message", user.getCompanyName(), locale),
                                        RequestUtil.getAppURL(request));
                    } catch (MailException me) {
                        saveError(request, me.getCause().getLocalizedMessage());
                    }

                    return new ModelAndView(getSuccessView());
                } else {
                    saveMessage(request, getText("user.updated.byAdmin", user.getCompanyName(), locale));
                }
            }
    
        }
        return showForm(request, response, errors);
    }
//====================================================================================
    protected ModelAndView showForm(HttpServletRequest request,
                                    HttpServletResponse response,
                                    BindException errors)
    throws Exception {

        // If not an adminstrator, make sure user is not trying to add or edit another user
        if (!request.isUserInRole(Constants.ADMIN_ROLE) && !isFormSubmission(request)) {
            if (isAdd(request) || request.getParameter("id") != null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                log.warn("User '" + request.getRemoteUser() + "' is trying to edit user with id '" +
                         request.getParameter("id") + "'");

                throw new AccessDeniedException("You do not have permission to modify other users.");
            }
        }

        return super.showForm(request, response, errors);
    }
//=======================================================================================
    protected Object formBackingObject(HttpServletRequest request)
    throws Exception {
    	log.debug("UserFormController: formBackingObject");
        if (!isFormSubmission(request)) {
        	log.debug("!isFormSubmission==true");
            String userId = request.getParameter("id");

            // if user logged in with remember me, display a warning that they can't change passwords
            log.debug("checking for remember me login...");

            AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
            SecurityContext ctx = SecurityContextHolder.getContext();

            if (ctx.getAuthentication() != null) {
                Authentication auth = ctx.getAuthentication();

                if (resolver.isRememberMe(auth)) {
                    request.getSession().setAttribute("cookieLogin", "true");

                    // add warning message
                    saveMessage(request, getText("userProfile.cookieLogin", request.getLocale()));
                }
            }

            User user;
            // no id specified && not add (check my own profile)
            if (userId == null && !isAdd(request)) {
            	log.debug("no id and not add");
                user = getUserManager().getUserByUsername(request.getRemoteUser());
            // has id specified && has version specified 
            } else if (!StringUtils.isBlank(userId) && !"".equals(request.getParameter("version"))) {
                user = getUserManager().getUser(userId);
            } else {
            	log.debug("add new user");
                user = new User();
                user.addRole(new Role(Constants.USER_ROLE));
                Address address = new Address();
                address.setCountry("AU");
                user.setAddress(address);
                user.setEnabled(true);
            }

            user.setConfirmPassword(user.getPassword());

            return user;
        } else if (request.getParameter("id") != null && !"".equals(request.getParameter("id"))
                && request.getParameter("cancel") == null) {
            // populate user object from database, so all fields don't need to be hidden fields in form
            return getUserManager().getUser(request.getParameter("id"));
        }

        return super.formBackingObject(request);
    }
    
    /* before validation.
     * if it is a cancel request, then don't need to validate the command.
     */
    protected void onBind(HttpServletRequest request, Object command)
    throws Exception {
        // if the user is being deleted, turn off validation
        if (request.getParameter("delete") != null) {
            super.setValidateOnBinding(false);
        } else {
            super.setValidateOnBinding(true);
        }
    }

    /* check whether is it to add a new user
     */
    protected boolean isAdd(HttpServletRequest request) {
        String method = request.getParameter("method");
        return (method != null && method.equalsIgnoreCase("add"));
    }
}
