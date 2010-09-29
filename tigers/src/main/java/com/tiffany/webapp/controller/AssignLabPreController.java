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

import com.tiffany.model.Sampler;
import com.tiffany.model.User;
import com.tiffany.service.SamplerManager;
import com.tiffany.service.UserManager;
/**
 * This class controls the "sample" list screen
 *
 * @author Jane
 */
public class AssignLabPreController implements Controller {
	
	private final Log log = LogFactory.getLog(AssignLabPreController.class);
	public static final String MESSAGES_KEY = "successMessages";
    private SamplerManager samplerManager = null;
    private UserManager userManager = null;

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    public void setSamplerManager(SamplerManager samplerManager) {
            this.samplerManager = samplerManager;
    }

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        
		if (!request.isUserInRole("ROLE_CONTRACTOR")) {
        	response.sendError(HttpServletResponse.SC_FORBIDDEN);
            log.warn("User '" + request.getRemoteUser() + "' is trying to edit user with id '" +
                     request.getParameter("id") + "'");

            throw new AccessDeniedException("You do not have permission to add laboratories.");
        }
		
		User contractor = userManager.getUserByUsername(request.getRemoteUser());
		List<Sampler> samplerList = samplerManager.getMySamplers(contractor);
		if (samplerList.size() == 0) saveError(request, "No sampler available");
		return new ModelAndView("/contractor/assignLabPre").addObject(samplerList);
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
