package com.tiffany.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tiffany.Constants;
import com.tiffany.model.User;
import com.tiffany.service.UserExistsException;
import com.tiffany.service.UserManager;

import org.springframework.security.AccessDeniedException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * Simple class to retrieve a list of users from the database.
 *
 * <p>
 * <a href="UserController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class AddExistingLabController implements Controller {
    private transient final Log log = LogFactory.getLog(AddExistingLabController.class);
    public static final String MESSAGES_KEY = "successMessages";
    private UserManager mgr = null;

    public void setUserManager(UserManager userManager) {
        this.mgr = userManager;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'handleRequest' method...");
        }
        
        if (!request.isUserInRole("ROLE_CONTRACTOR")) {
        	response.sendError(HttpServletResponse.SC_FORBIDDEN);
            log.warn("User '" + request.getRemoteUser() + "' is trying to edit user with id '" +
                     request.getParameter("id") + "'");

            throw new AccessDeniedException("You do not have permission to add laboratories.");
        }
        
        String userId = request.getParameter("id");
        if (userId != null) {
        	User lab = mgr.getUser(userId);
        	User me = mgr.getUserByUsername(request.getRemoteUser());
        	me.addChildren(lab);
        	try {
                mgr.saveUser(me);
            } catch (AccessDeniedException ade) {
                // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
                log.warn(ade.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return null;
            } catch (UserExistsException e) {
            	
            }
            saveMessage(request, "The laboratory " + lab.getUsername() + " has been added.");
        } 
        
        return new ModelAndView("/contractor/availableLaboratoryList", Constants.USER_LIST, mgr.getAvailableLaboratories(request.getRemoteUser()));
    }

//================================================================================
    public void saveError(HttpServletRequest request, String error) {
        List errors = (List) request.getSession().getAttribute("errors");
        if (errors == null) {
            errors = new ArrayList();
        }
        errors.add(error);
        request.getSession().setAttribute("errors", errors);
    }
    
    public void saveMessage(HttpServletRequest request, String msg) {
        List messages = (List) request.getSession().getAttribute(MESSAGES_KEY);

        if (messages == null) {
            messages = new ArrayList();
        }

        messages.add(msg);
        request.getSession().setAttribute(MESSAGES_KEY, messages);
    }
}
