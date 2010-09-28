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
import com.tiffany.webapp.controller.ListSamplersController;

public class ReportExceedanceListSamplersController extends ListSamplersController {
	public void setWaterbodyManager(GenericManager<Waterbody, Long> waterbodyManager) {
		super.setWaterbodyManager(waterbodyManager);
	}
    public void setSamplerManager(GenericManager<Sampler, Long> samplerManager) {
        super.setSamplerManager(samplerManager);
    }

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return super.handleRequest(request, response);
	}
}
