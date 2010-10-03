package com.tiffany.webapp.controller;

import com.tiffany.service.ScreeningFrequencyManager;
import com.tiffany.service.ParameterNamesManager;
import com.tiffany.service.WaterbodyManager;
import com.tiffany.service.SamplerManager;
import com.tiffany.model.Sampler;
import com.tiffany.model.ScreeningFrequency;
import com.tiffany.model.ParameterNames;
import org.apache.commons.logging.*;
import org.apache.commons.collections.map.*;
import org.apache.commons.collections.MultiMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import java.util.*;
import javax.servlet.http.*;

/**
 * Author: Jane
 */
public class SamplingScheduleController extends BaseFormController {

	private final Log log = LogFactory.getLog(SamplerController.class);
    private SamplerManager samplerManager;
    private ScreeningFrequencyManager screeningFrequencyManager;
    private WaterbodyManager waterbodyManager;
    private ParameterNamesManager parameterNamesManager;

    public SamplingScheduleController() {
		setCommandClass(SamplingSchedule.class);
		setCommandName("samplingSchedule");
        setSessionForm(true);
	}
    public void setSamplerManager(SamplerManager samplerManager) {
            this.samplerManager = samplerManager;
    }
    public void setScreeningFrequencyManager(ScreeningFrequencyManager screeningFrequencyManager) {
            this.screeningFrequencyManager = screeningFrequencyManager;
    }
    public void setWaterbodyManager(WaterbodyManager waterbodyManager) {
            this.waterbodyManager = waterbodyManager;
    }
    public void setParameterNamesManager(ParameterNamesManager parameterNamesManager) {
            this.parameterNamesManager = parameterNamesManager;
    }

	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		SamplingSchedule samplingSchedule = new SamplingSchedule();
		samplingSchedule.setGroupBy("Sampler");
		return samplingSchedule;
	}

     protected Map referenceData(HttpServletRequest request, Object command,
                                Errors errors) throws Exception {

	    Map referenceData = new HashMap();

	    List<String> groupByList = new ArrayList<String>();
	    groupByList.add("Sampler");
	    groupByList.add("Frequency");

	    referenceData.put("groupByList", groupByList);

	    return referenceData;
    }

	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
			Object command, BindException errors) throws Exception {

        SamplingSchedule samplingSchedule = (SamplingSchedule) command;
        char waterbodyType;

        try {
		    waterbodyType = waterbodyManager.getWaterBodyType(samplingSchedule.getWaterbody()).charAt(0);
        } catch (NullPointerException e) {
            errors.rejectValue("waterbody", "errors.waterbodyNotFound");
        	return showForm(request, errors, getFormView());
        }
        List<ParameterNames> parameters = parameterNamesManager.findByType(waterbodyType);
        List<ScreeningFrequency> frequencies = new ArrayList<ScreeningFrequency>();

        if (samplingSchedule.getGroupBy().equals("Sampler")) {
            frequencies =
                    new ArrayList<ScreeningFrequency>(populateBySampler(samplingSchedule.getWaterbody(), parameters));
        }
        else {
            frequencies =
                    new ArrayList<ScreeningFrequency>(populateByFrequency(samplingSchedule.getWaterbody(), parameters));
        }
        List<String> groupByList = new ArrayList<String>();
	    groupByList.add("Sampler");
	    groupByList.add("Frequency");

        ModelAndView mv = new ModelAndView("officer/samplingschedule", getCommandName(), command);
		mv.addObject("screeningFrequencyList", frequencies);
		mv.addObject("parameters", parameters);
        mv.addObject("firstTime", "nup");
        mv.addObject("heading", "Sampling Schedule for " + samplingSchedule.getWaterbody());
        mv.addObject("groupByList", groupByList);
		return mv;
	}

    private List<ScreeningFrequency> populateBySampler(String waterbody, List<ParameterNames> parameters) {
        List<ScreeningFrequency> frequencies= new ArrayList<ScreeningFrequency>();
        List<ScreeningFrequency> returnFrequencies= new ArrayList<ScreeningFrequency>();
        MultiMap mhm = new MultiValueMap();
        ScreeningFrequency frequency;
        List<Sampler> samplers = samplerManager.findByWaterBody(waterbody);
        List<ParameterNames> depthList = new ArrayList<ParameterNames>();
        ParameterNames depth = new ParameterNames();
        depth.setId(new Long(6));
        depth.setName("Depth to Collar");
        depthList.add(depth);

        //Get screening frequencies
        for (Sampler sampler : samplers) {
            List<ScreeningFrequency> theseFrequencies = screeningFrequencyManager.findBySampler(sampler.getId());
            if (null != theseFrequencies)
                frequencies.addAll(theseFrequencies);
            //Comprehensive
            frequency = new ScreeningFrequency();
            frequency.setSampler(sampler);
            frequency.setFrequency(sampler.getComp_screening_freq());
            frequency.setDescription("Comprehensive");
            frequency.setParameterNames(parameters);

            frequencies.add(frequency);
            //Depth to collar
            if (null != sampler.getDepth_to_collar_screening_freq()) {
                frequency = new ScreeningFrequency();
                frequency.setSampler(sampler);
                frequency.setFrequency(sampler.getDepth_to_collar_screening_freq());
                frequency.setDescription("Depth to Collar");
                frequency.setParameterNames(depthList);

                frequencies.add(frequency);
            }
            //Do a whole lot of palaver to get the frequencies in the correct order
            for (ScreeningFrequency freq : frequencies) {
                if (freq.getFrequency().equals("daily"))
                    mhm.put(1,freq);
                else if (freq.getFrequency().equals("weekly"))
                    mhm.put(7,freq);
                else if (freq.getFrequency().equals("fortnightly"))
                    mhm.put(14,freq);
                else if (freq.getFrequency().equals("monthly"))
                    mhm.put(31,freq);
                else if (freq.getFrequency().equals("half yearly"))
                    mhm.put(187,freq);
                else if (freq.getFrequency().equals("yearly"))
                    mhm.put(365,freq);
            }
            Set S = mhm.keySet();
            TreeSet T = new TreeSet(S);
            Iterator I = T.iterator();

            while (I.hasNext()) {
                returnFrequencies.addAll((List<ScreeningFrequency>)mhm.get(I.next()));
            }
            mhm.clear();
            frequencies.clear();
        }

        return returnFrequencies;
    }
    private List<ScreeningFrequency> populateByFrequency(String waterbody, List<ParameterNames> parameters) {
        List<ScreeningFrequency> frequencies= new ArrayList<ScreeningFrequency>();
        List<ScreeningFrequency> returnFrequencies= new ArrayList<ScreeningFrequency>();
        MultiMap mhm = new MultiValueMap();
        ScreeningFrequency frequency;
        List<Sampler> samplers = samplerManager.findByWaterBody(waterbody);
        List<ParameterNames> depthList = new ArrayList<ParameterNames>();
        ParameterNames depth = new ParameterNames();
        depth.setId(new Long(6));
        depth.setName("Depth to Collar");
        depthList.add(depth);

        //Get screening frequencies
        for (Sampler sampler : samplers) {
            List<ScreeningFrequency> theseFrequencies = screeningFrequencyManager.findBySampler(sampler.getId());
            if (null != theseFrequencies)
                frequencies.addAll(theseFrequencies);
            //Comprehensive
            frequency = new ScreeningFrequency();
            frequency.setSampler(sampler);
            frequency.setFrequency(sampler.getComp_screening_freq());
            frequency.setDescription("Comprehensive");
            frequency.setParameterNames(parameters);

            frequencies.add(frequency);
            //Depth to collar
            if (null != sampler.getDepth_to_collar_screening_freq()) {
                frequency = new ScreeningFrequency();
                frequency.setSampler(sampler);
                frequency.setFrequency(sampler.getDepth_to_collar_screening_freq());
                frequency.setDescription("Depth to Collar");
                frequency.setParameterNames(depthList);

                frequencies.add(frequency);
            }
        }
        //Do a whole lot of palaver to get the frequencies in the correct order
        for (ScreeningFrequency freq : frequencies) {
            if (freq.getFrequency().equals("daily"))
                mhm.put(1,freq);
            else if (freq.getFrequency().equals("weekly"))
                mhm.put(7,freq);
            else if (freq.getFrequency().equals("fortnightly"))
                mhm.put(14,freq);
            else if (freq.getFrequency().equals("monthly"))
                mhm.put(31,freq);
            else if (freq.getFrequency().equals("half yearly"))
                mhm.put(187,freq);
            else if (freq.getFrequency().equals("yearly"))
                mhm.put(365,freq);
        }
        Set S = mhm.keySet();
        TreeSet T = new TreeSet(S);
        Iterator I = T.iterator();

        while (I.hasNext()) {
            returnFrequencies.addAll((List<ScreeningFrequency>)mhm.get(I.next()));
        }

        return returnFrequencies;
    }
}
