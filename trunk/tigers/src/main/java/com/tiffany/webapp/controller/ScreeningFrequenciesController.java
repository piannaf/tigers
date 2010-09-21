package com.tiffany.webapp.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.apache.commons.logging.*;

import javax.servlet.http.*;

import com.tiffany.service.ScreeningFrequencyManager;
import com.tiffany.service.ParameterNamesManager;
import com.tiffany.service.SamplerManager;
import com.tiffany.model.Sampler;

/**
 * Author: Jane
 */
public class ScreeningFrequenciesController implements Controller {

	private final Log log = LogFactory.getLog(SamplerController.class);
    private ScreeningFrequencyManager screeningFrequencyManager;
    private SamplerManager samplerManager;
    private ParameterNamesManager parameterNamesManager;

    public void setScreeningFrequencyManager(ScreeningFrequencyManager screeningFrequencyManager) {
            this.screeningFrequencyManager = screeningFrequencyManager;
    }
    public void setSamplerManager(SamplerManager samplerManager) {
            this.samplerManager = samplerManager;
    }
    public void setParameterNamesManager(ParameterNamesManager parameterNamesManager) {
            this.parameterNamesManager = parameterNamesManager;
    }

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");

        Sampler sampler = samplerManager.get(new Long(id));
		char waterbodyType = sampler.getWaterbody().getType();
		String tag = sampler.getTag();

        ModelAndView mv = new ModelAndView("officer/screeningfrequencies");
		mv.addObject("screeningFrequencyList", screeningFrequencyManager.findBySampler(new Long(id)));
		mv.addObject("parameters", parameterNamesManager.findByType(waterbodyType));
		mv.addObject("heading", "Screening Frequencies for " + tag);
		mv.addObject("samplerid", id);
		return mv;
	}

}
