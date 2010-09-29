package com.tiffany.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import org.apache.commons.logging.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tiffany.model.Sample;
import com.tiffany.model.Sampler;
import com.tiffany.model.User;
import com.tiffany.service.GenericManager;
import com.tiffany.service.SampleManager;
import com.tiffany.service.SamplerManager;
import com.tiffany.service.UserManager;

/**
 * This class controls the "sample" list screen
 * 
 * @author Jane
 */
public class SamplerController implements Controller {

    private final Log log = LogFactory.getLog(SamplerController.class);
    private SamplerManager samplerManager = null;
    private UserManager userManager = null;

    public void setSamplerManager(SamplerManager samplerManager) {
	this.samplerManager = samplerManager;
    }

    public void setUserManager(UserManager userManager) {
	this.userManager = userManager;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	String waterbody = request.getParameter("waterbody");

	User user = userManager.getUserByUsername((request.getRemoteUser()));
	log.debug("\n\t!!!!! " + user.getRoles());

	/* Generate list of samplers based on ownership (role) */
	List<Sampler> samplers;
	if (request.isUserInRole("ROLE_CONTRACTOR")) {
	    User contractor = userManager.getUserByUsername((request
		    .getRemoteUser()));
	    samplers = samplerManager.getMySamplers(contractor);
	} else if (request.isUserInRole("ROLE_LABORATORY")) {
	    User lab = userManager.getUserByUsername((request.getRemoteUser()));
	    samplers = samplerManager.getMySamplers(lab);
	} else {
	    samplers = samplerManager.getAllOrderedByTag();
	}

	/* Weed out the ones in the waterbody we're looking for */
	if (null != waterbody && !waterbody.isEmpty()) {
	    List<Sampler> tmp = new ArrayList<Sampler>();
	    for (Sampler sampler : samplers) {
		if (sampler.getWaterbody().getName().equals(waterbody))
		    tmp.add(sampler);
	    }
	    samplers = tmp;
	}

	return new ModelAndView().addObject(samplers);
    }

}
