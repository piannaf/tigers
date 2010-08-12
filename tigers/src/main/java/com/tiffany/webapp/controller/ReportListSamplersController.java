package com.tiffany.webapp.controller;

import java.util.List;
import java.util.HashMap;

import javax.servlet.http.*;

import org.apache.commons.logging.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tiffany.model.Waterbody;
import com.tiffany.model.Sampler;
import com.tiffany.service.GenericManager;
import com.tiffany.service.WaterbodyManager;
import com.tiffany.service.SamplerManager;

public class ReportListSamplersController implements Controller {
	
	//private final Log log = LogFactory.getLog(ReportListSamplersController.class);
    private GenericManager<Waterbody, String> waterbodyManager = null;
    private GenericManager<Sampler, String> samplerManager = null;
	
    public void setWaterbodyManager(GenericManager<Waterbody, String> waterbodyManager) {
            this.waterbodyManager = waterbodyManager;
    }
    public void setSamplerManager(GenericManager<Sampler, String> samplerManager) {
            this.samplerManager = samplerManager;
    }

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//log.debug("entering 'handleRequest' method...");
        
		List<Waterbody> waterbodies = ((WaterbodyManager)waterbodyManager).getAll();
		HashMap<String, List<Sampler>> samplers = new HashMap<String, List<Sampler>>(waterbodies.size());
		int i;
		for(i=0; i<waterbodies.size(); i++) {
			String wbName = waterbodies.get(i).getName();
			List<Sampler> waterbodySamplers = ((SamplerManager)samplerManager).findByWaterBody(wbName);
			// ...cause queries in loops are cool
			samplers.put(wbName, waterbodySamplers);
		}
		
		return new ModelAndView().addObject("waterbodies", waterbodies).addObject("samplers", samplers);
	}

}
