package com.tiffany.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tiffany.Constants;
import com.tiffany.model.User;
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
public class ViewMyContractorsController implements Controller {
    private transient final Log log = LogFactory.getLog(ViewMyContractorsController.class);
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
        
        if (!request.isUserInRole("ROLE_LABORATORY")) {
        	response.sendError(HttpServletResponse.SC_FORBIDDEN);
            log.warn("User '" + request.getRemoteUser() + "' is trying to view contractors.");

            throw new AccessDeniedException("You do not have permission to view contractors.");
        }
        return new ModelAndView("/laboratory/myContractorList", Constants.USER_LIST, mgr.getMyContractors(request.getRemoteUser()));
    }
}
