package com.tiffany.webapp.controller;

import java.util.List;
import java.util.HashMap;
import java.util.Date;
import java.math.BigDecimal;
import java.lang.Long;

import javax.servlet.http.*;

import org.apache.commons.logging.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tiffany.model.Sample;
import com.tiffany.model.Sampler;
import com.tiffany.model.ParameterNames;
import com.tiffany.model.ParameterThresholds;
import com.tiffany.model.Waterbody;
import com.tiffany.service.GenericManager;
import com.tiffany.service.SampleManager;
import com.tiffany.service.SamplerManager;
import com.tiffany.service.ParameterNamesManager;
import com.tiffany.service.ParameterThresholdsManager;

public class ReportGraphSamplesController implements Controller {
	
	//private final Log log = LogFactory.getLog(ReportListSamplersController.class);
    private GenericManager<Sample, Long> sampleManager = null;
    private GenericManager<Sampler, Long> samplerManager = null;
    private GenericManager<ParameterNames, Long> parameterNamesManager = null;
    private GenericManager<ParameterThresholds, Long> parameterThresholdsManager = null;
	
    public void setSampleManager(GenericManager<Sample, Long> sampleManager) {
        this.sampleManager = sampleManager;
    }
    public void setSamplerManager(GenericManager<Sampler, Long> samplerManager) {
        this.samplerManager = samplerManager;
    }
    public void setParameterNamesManager(GenericManager<ParameterNames, Long> parameterNamesManager) {
        this.parameterNamesManager = parameterNamesManager;
    }
    public void setParameterThresholdsManager(GenericManager<ParameterThresholds, Long> parameterThresholdsManager) {
        this.parameterThresholdsManager = parameterThresholdsManager;
    }

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//log.debug("entering 'handleRequest' method...");
        
		String samplerTag = request.getParameter("tag");
		if(samplerTag == null || samplerTag.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tag not specified");
			return null;
		}
		
		String paramStr = request.getParameter("display_parameter");
		if(paramStr == null || paramStr.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter not specified");
			return null;
		}
		

		Long param = Long.parseLong(paramStr);
		ParameterNames parameterName = ((ParameterNamesManager)parameterNamesManager).getId(param);
		if(parameterName == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameter name");
			return null;
		}
		String paramName = parameterName.getInternal_name();
		
		Sampler sampler = ((SamplerManager)samplerManager).getByTag(samplerTag);
		if(sampler == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not find sampler");
			return null;
		}
		String waterbody = sampler.getWaterbody().getName();
		ParameterThresholds pt = ((ParameterThresholdsManager)parameterThresholdsManager).findByWaterBodyAndId(waterbody, param);
		
		
		List<Sample> samples = ((SampleManager)sampleManager).findSamplesByTag(samplerTag);
		HashMap<Long, BigDecimal> data = new HashMap<Long, BigDecimal>(samples.size());
		BigDecimal value, minValue=null, maxValue=null;
		Date dateFirst = null, dateLast = null, dateCurrent = null;
		if(samples != null)
			for(Sample sample : samples) {
					 if(paramName.equals("ph"))			value = sample.getPh();
				else if(paramName.equals("ec"))			value = sample.getEc();
				else if(paramName.equals("temperature"))value = sample.getTemperature();
				else if(paramName.equals("collar_depth"))value = sample.getCollar_depth();
				else if(paramName.equals("arsenic"))	value = sample.getArsenic();
				else if(paramName.equals("grease"))		value = sample.getGrease();
				else if(paramName.equals("fluoride"))	value = sample.getFluoride();
				else if(paramName.equals("chromium"))	value = sample.getChromium();
				else // should never occur
					throw new Exception("Invalid parameter (" + paramName + ") specified.");
				
				dateCurrent = sample.getDate_taken();
				if(value != null) {
					if(value.compareTo(new BigDecimal(0)) < 0) // "<0.5" type values are treated as 0
						value = new BigDecimal(0);
					data.put(new Long(dateCurrent.getTime()), value);
					if(dateFirst == null || dateCurrent.compareTo(dateFirst) < 0)
						dateFirst = dateCurrent;
					if(dateLast == null || dateCurrent.compareTo(dateLast) > 0)
						dateLast = dateCurrent;
					if(minValue == null || value.compareTo(minValue) < 0)
						minValue = value;
					if(maxValue == null || value.compareTo(maxValue) > 0)
						maxValue = value;
				}
			}
		
		
		String thresMinMaxJS = "";
		if(pt != null) {
			if(minValue == null || (pt.getMin() != null && pt.getMin().compareTo(minValue) < 0))
				minValue = pt.getMin();
			if(maxValue == null || (pt.getMax() != null && pt.getMax().compareTo(maxValue) > 0))
				maxValue = pt.getMax();
			
			if(pt.getMin() != null)
				thresMinMaxJS += "dat.push({data: makeLineArray(" + String.valueOf(pt.getMin()) + "), label: \"Min Thresh.\"});\n";
			if(pt.getMax() != null)
				thresMinMaxJS += "dat.push({data: makeLineArray(" + String.valueOf(pt.getMax()) + "), label: \"Max Thresh.\"});\n";
		}
		
		if(minValue == null) minValue = new BigDecimal(0);
		if(maxValue == null) maxValue = new BigDecimal(0);
		
		Long startTime = new Long(0), endTime = new Long(0);
		if(dateFirst != null)
			startTime = new Long(dateFirst.getTime());
		if(dateLast != null)
			endTime = new Long(dateLast.getTime());
		
		return new ModelAndView()
			.addObject("data", data)
			.addObject("tag", samplerTag)
			.addObject("parameterName", parameterName)
			.addObject("startTime", startTime)
			.addObject("endTime", endTime)
			.addObject("minValue", minValue)
			.addObject("maxValue", maxValue)
			.addObject("thresMinMaxJS", thresMinMaxJS)
			.addObject("params", ((ParameterNamesManager)parameterNamesManager).getAll());
	}
}
