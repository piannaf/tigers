package com.tiffany.webapp.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.math.BigDecimal;
import java.lang.Long;

import javax.servlet.http.*;

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
//import com.tiffany.service.ParameterNamesManager;
import com.tiffany.service.ParameterThresholdsManager;

public class ReportExceedanceController implements Controller {
	
    private GenericManager<Sample, Long> sampleManager = null;
    private GenericManager<Sampler, Long> samplerManager = null;
    //private GenericManager<ParameterNames, Long> parameterNamesManager = null;
    private GenericManager<ParameterThresholds, Long> parameterThresholdsManager = null;
	
    public void setSampleManager(GenericManager<Sample, Long> sampleManager) {
        this.sampleManager = sampleManager;
    }
    public void setSamplerManager(GenericManager<Sampler, Long> samplerManager) {
        this.samplerManager = samplerManager;
    }
    /* public void setParameterNamesManager(GenericManager<ParameterNames, Long> parameterNamesManager) {
        this.parameterNamesManager = parameterNamesManager;
    } */
    public void setParameterThresholdsManager(GenericManager<ParameterThresholds, Long> parameterThresholdsManager) {
        this.parameterThresholdsManager = parameterThresholdsManager;
    }

	
	public class ReportExceedanceController_Data {
		public Long sampleId;
		public Date sampleTaken;
		public String paramName;
		public String value;
		public BigDecimal magnitude;
		
		public Long getSampleId() { return sampleId; }
		public Date getSampleTaken() { return sampleTaken; }
		public String getParamName() { return paramName; }
		public String getValue() { return value; }
		public BigDecimal getMagnitude() { return magnitude; }
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        
		String samplerTag = request.getParameter("tag");
		if(samplerTag == null || samplerTag.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tag not specified");
			return null;
		}
		
		/* ParameterNames parameterName = ((ParameterNamesManager)parameterNamesManager).getId(param);
		if(parameterName == null)
			throw new Exception("Parameter name could not be found");
		String paramName = parameterName.getInternal_name(); */
		
		Sampler sampler = ((SamplerManager)samplerManager).getByTag(samplerTag);
		if(sampler == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not find sampler");
			return null;
		}
		String waterbody = sampler.getWaterbody().getName();
		List<ParameterThresholds> thresholds = ((ParameterThresholdsManager)parameterThresholdsManager).findByWaterBody(waterbody);
		
		
		HashMap<String, Long> exceedCounts = new HashMap<String, Long>(8);
		exceedCounts.put("ph", new Long(0));
		exceedCounts.put("ec", new Long(0));
		exceedCounts.put("temperature", new Long(0));
		exceedCounts.put("collar_depth", new Long(0));
		exceedCounts.put("arsenic", new Long(0));
		exceedCounts.put("grease", new Long(0));
		exceedCounts.put("fluoride", new Long(0));
		exceedCounts.put("chromium", new Long(0));
		
		List<Sample> samples = ((SampleManager)sampleManager).findSamplesByTag(samplerTag);
		BigDecimal exceedance, value;
		String paramName;
		List<ReportExceedanceController_Data> data = new ArrayList<ReportExceedanceController_Data>();
		ReportExceedanceController_Data datum;
		if(samples != null && thresholds != null)
			for(Sample sample : samples) {
				for(ParameterThresholds pt : thresholds) {
					paramName = pt.getParameter().getInternal_name();
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
					
					if(value != null) {
						String valueString = Sample.getValueDisplayString(value);
						if(value.compareTo(new BigDecimal(0)) < 0) // "<0.5" type values are treated as 0
							value = new BigDecimal(0);
						//exceedance = 0;
						if(pt.getMin() != null && pt.getMin().compareTo(value) > 0)
							exceedance = pt.getMin().subtract(value);
						else if(pt.getMax() != null && pt.getMax().compareTo(value) < 0)
							exceedance = value.subtract(pt.getMax());
						else // no exceedance
							continue;
						
						exceedCounts.put(paramName, new Long(exceedCounts.get(paramName).longValue()+1));
						
						datum = new ReportExceedanceController_Data();
						datum.sampleId = sample.getId();
						datum.sampleTaken = sample.getDate_taken();
						datum.paramName = pt.getParameter().getName();
						datum.value = valueString;
						datum.magnitude = exceedance;
						data.add(datum);
					}
				}
			}
		
		
		return new ModelAndView()
			.addObject("data", data)
			.addObject("tag", samplerTag)
			.addObject("thresholds", thresholds)
			.addObject("exceedCounts", exceedCounts);
	}
}
