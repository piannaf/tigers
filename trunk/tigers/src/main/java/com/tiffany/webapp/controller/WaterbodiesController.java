package com.tiffany.webapp.controller;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tiffany.service.WaterbodyManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class controls the "waterbodies" list screen
 *
 * @author Jane
 */
public class WaterbodiesController implements Controller {

	private final Log log = LogFactory.getLog(SamplerController.class);
    private WaterbodyManager waterbodyManager;

    public void setWaterbodyManager(WaterbodyManager waterbodyManager) {
            this.waterbodyManager = waterbodyManager;
    }

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String waterbody = request.getParameter("waterbody");

		if (null != waterbody && !waterbody.isEmpty()) {
            return new ModelAndView().addObject(waterbodyManager.findLikeName(waterbody));
		}
		else {
			return new ModelAndView().addObject(waterbodyManager.getAll());
		}
	}

}

