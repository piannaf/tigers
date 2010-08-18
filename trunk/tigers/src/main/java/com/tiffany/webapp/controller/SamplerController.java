package com.tiffany.webapp.controller;

import java.util.List;

import javax.servlet.http.*;

import org.apache.commons.logging.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tiffany.model.Sample;
import com.tiffany.model.Sampler;
import com.tiffany.service.GenericManager;
import com.tiffany.service.SampleManager;
import com.tiffany.service.SamplerManager;
/**
 * This class controls the "sample" list screen
 *
 * @author Jane
 */
public class SamplerController implements Controller {
	
	private final Log log = LogFactory.getLog(SamplerController.class);
    private GenericManager<Sampler, Long> samplerManager = null;
    
    public void setSamplerManager(GenericManager<Sampler, Long> samplerManager) {
            this.samplerManager = samplerManager;
    }

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        
		String waterbody = request.getParameter("waterbody");
		
		if (null != waterbody && !waterbody.isEmpty()) {
			List<Sampler> samplerList = ((SamplerManager)samplerManager).findByWaterBody(waterbody);
             
            return new ModelAndView().addObject("samplerList", samplerList);
		}
		else
			return new ModelAndView().addObject(samplerManager.getAll());
	}

}
