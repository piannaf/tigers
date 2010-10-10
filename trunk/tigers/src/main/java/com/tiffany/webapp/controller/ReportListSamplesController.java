package com.tiffany.webapp.controller;

import java.util.List;

import javax.servlet.http.*;

import org.apache.commons.logging.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tiffany.model.Sample;
import com.tiffany.model.ParameterNames;
import com.tiffany.service.GenericManager;
import com.tiffany.service.SampleManager;
import com.tiffany.service.ParameterNamesManager;

public class ReportListSamplesController implements Controller {
    private static final Log log = LogFactory.getLog(ReportListSamplesController.class);
	
	//private final Log log = LogFactory.getLog(ReportListSamplersController.class);
    private GenericManager<Sample, Long> sampleManager = null;
    private GenericManager<ParameterNames, Long> parameterNamesManager = null;
	
    public void setSampleManager(GenericManager<Sample, Long> sampleManager) {
        this.sampleManager = sampleManager;
    }
    public void setParameterNamesManager(GenericManager<ParameterNames, Long> parameterNamesManager) {
        this.parameterNamesManager = parameterNamesManager;
    }

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//log.debug("entering 'handleRequest' method...");
        
		String samplerTag = request.getParameter("tag");
		if(samplerTag == null || samplerTag.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tag not specified");
			return null;
		}
		
		// TODO: do we need to determine the waterbody?
		// TODO: display exceedances in table?
		
		List<ParameterNames> params = ((ParameterNamesManager)parameterNamesManager).getAll();
		log.debug("params = " + params.toString());
		
		List<Sample> samples = ((SampleManager)sampleManager).findSamplesByTag(samplerTag);
		return new ModelAndView().addObject("sampleList", samples).addObject("params", params).addObject("tag", samplerTag);
	}
}
