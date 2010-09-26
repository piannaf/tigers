package com.tiffany.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.AccessDeniedException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tiffany.Constants;
import com.tiffany.model.Role;
import com.tiffany.model.User;
import com.tiffany.service.UserManager;


/**
 * Simple class to retrieve a list of users from the database.
 *
 * <p>
 * <a href="UserController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class UserInfoController implements Controller {
    private transient final Log log = LogFactory.getLog(UserInfoController.class);
    public static final String MESSAGES_KEY = "successMessages"; 
    public static final int ADMIN = 1;
    public static final int OFFICER = 2;
    public static final int CONTRACTOR = 3;
    public static final int LABORATORY = 4;
    private UserManager mgr = null;

    public void setUserManager(UserManager userManager) {
        this.mgr = userManager;
    }
//====================================================================================
    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response)
    throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'handleRequest' method...");
        }
        String userId = request.getParameter("id");
        User target;
        if (userId != null) {
        	log.debug("userId=" + userId);
        	try {
        		target = mgr.getUser(userId);
        	} catch (Exception e) {
        		saveError(request, "User not exist.");
            	return new ModelAndView("redirect:mainMenu.html");
        	}
        	log.debug("username=" + target.getUsername());
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
	        ModelAndView mv = new ModelAndView("userInfo");
	        mv.addObject("user", target);
            return mv;
        } else {
        	saveError(request, "No user specified.");
        	return new ModelAndView("redirect:mainMenu.html");
        }       
    }
//================================================================================
    public boolean isOk(int role, String username, User target) {
    	switch (role) {
    	case OFFICER:
    		if (!target.getRoles().contains(new Role("ROLE_CONTRACTOR"))) return false;
    		break;
    	case CONTRACTOR:
    		if (!mgr.isMyLaboratory(username, target)) return false;
    		break;
    	case LABORATORY:
    		if (!mgr.isMyContractor(username, target)) return false;
    		break;
    	}
    	return true;
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
