package com.tiffany.webapp.controller;

import com.tiffany.service.*;
import com.tiffany.model.*;

import javax.servlet.http.*;

import org.springframework.validation.Errors;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.apache.commons.collections.map.*;
import org.apache.commons.collections.MultiMap;

import java.util.*;
import java.beans.PropertyEditorSupport;

/**
 * Author: Jane
 */
public class ParameterSelectionController extends BaseFormController {

    private ScreeningProgramSamplersManager screeningProgramSamplersManager;
    private ScreeningFrequencyManager screeningFrequencyManager;
    private ScreeningProgramManager screeningProgramManager;
    private SamplerManager samplerManager;
    private ParameterNamesManager parameterNamesManager;

    public ParameterSelectionController() {
		setCommandClass(ParameterSelectionForm.class);
		setCommandName("parameterSelectionForm");
        setSessionForm(true);
	}
    public void setSamplerManager(SamplerManager samplerManager) {
            this.samplerManager = samplerManager;
    }
    public void setScreeningFrequencyManager(ScreeningFrequencyManager screeningFrequencyManager) {
            this.screeningFrequencyManager = screeningFrequencyManager;
    }
    public void setScreeningProgramSamplersManager(ScreeningProgramSamplersManager screeningProgramSamplersManager) {
            this.screeningProgramSamplersManager = screeningProgramSamplersManager;
    }
    public void setScreeningProgramManager(ScreeningProgramManager screeningProgramManager) {
            this.screeningProgramManager = screeningProgramManager;
    }
    public void setParameterNamesManager(ParameterNamesManager parameterNamesManager) {
            this.parameterNamesManager = parameterNamesManager;
    }
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(ParameterNames.class, new ParameterNameEditor());
    }
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
        String pageId = request.getParameter("page");
        String screeningProgramId = request.getParameter("id");
        ParameterSelectionForm parameterSelectionForm = new ParameterSelectionForm();
        parameterSelectionForm.setPageNo(Integer.parseInt(pageId));
        parameterSelectionForm.setScreeningProgramId(Long.parseLong(screeningProgramId));

        List<ScreeningProgramSamplers> samplers = screeningProgramSamplersManager.getSamplers(new Long(screeningProgramId));
        if (null != samplers && samplers.size() > 0) {
            parameterSelectionForm.setNoSamplers(samplers.size());
            ScreeningProgramSamplers sampler = samplers.get(Integer.parseInt(pageId));
            Long samplerId = sampler.getId().getSampler();
            List<ParameterNames> screeningItems = sampler.getParameterNames();
            
            if (null != screeningItems)
                parameterSelectionForm.setParameterNames(screeningItems);
            parameterSelectionForm.setSamplerid(samplerId);
        }

		return parameterSelectionForm;
	}

     protected Map referenceData(HttpServletRequest request, Object command,
                                Errors errors) throws Exception {

	    ParameterSelectionForm parameterSelectionForm = (ParameterSelectionForm) command;

        Sampler sampler = samplerManager.get(parameterSelectionForm.getSamplerid());
		char waterbodyType = sampler.getWaterbody().getType();
		String tag = sampler.getTag();

        List<ParameterNames> parameterNames = parameterNamesManager.findByType(waterbodyType);
        List<ParameterNames> parameters = parameterNamesManager.findByType(waterbodyType);
        List<ScreeningFrequency> frequencies =
                    new ArrayList<ScreeningFrequency>(populateBySampler(sampler, parameters));

        Map referenceData = new HashMap();
      	referenceData.put("heading", "Screening Programme Parameter Selection for " + tag);
        referenceData.put("parameterNames", parameterNames);
        referenceData.put("screeningFrequencyList", frequencies);
        referenceData.put("parameters", parameters);

	    return referenceData;
    }

	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
			Object command, BindException errors) throws Exception {

        String success;
        ParameterSelectionForm parameterSelectionForm = (ParameterSelectionForm) command;
        Long programId = parameterSelectionForm.getScreeningProgramId();
        if (request.getParameter("mycancel") != null) {
            //Remove all evidence of this screening program
            List<ScreeningProgramSamplers> samplers = screeningProgramSamplersManager.getSamplers(programId);
            for (ScreeningProgramSamplers sampler: samplers) {
                screeningProgramSamplersManager.remove(sampler.getId());
            }
            screeningProgramManager.remove(programId);
            Locale locale = request.getLocale();
            saveMessage(request, getText("screeningProgram.cancelled", locale));
            return new ModelAndView("redirect:../mainMenu.html");
        }
        if (request.getParameter("prev") != null) {
            if (parameterSelectionForm.getPageNo() == 0) {
                return new ModelAndView("redirect:/officer/screeningprogram.html");
            }
            success = getSuccessView() +"?id=" + programId +  "&page=" + (parameterSelectionForm.getPageNo() - 1);
            return new ModelAndView(success);
        }
        if (parameterSelectionForm.getParameterNames() == null) {
            errors.rejectValue("parameterNames","errors.parameterRequired");
            return showForm(request, errors, getFormView());
        }
        ScreeningProgramSamplersId screeningProgramSamplersId = new ScreeningProgramSamplersId();
        screeningProgramSamplersId.setId(programId);
        screeningProgramSamplersId.setSampler(parameterSelectionForm.getSamplerid());

        ScreeningProgramSamplers screeningProgramSamplers = new ScreeningProgramSamplers();
        screeningProgramSamplers.setComplete('P');
        screeningProgramSamplers.setId(screeningProgramSamplersId);
        screeningProgramSamplers.setParameterNames(parameterSelectionForm.getParameterNames());

        screeningProgramSamplersManager.save(screeningProgramSamplers);

        if (parameterSelectionForm.getPageNo() == parameterSelectionForm.getNoSamplers() - 1) {
            success = "redirect:/officer/screeningprogramconfirm.html?id=" + programId + "&page=" + parameterSelectionForm.getPageNo();
        }
        else {
            success = getSuccessView() +"?id=" + programId +  "&page=" + (parameterSelectionForm.getPageNo() + 1);
        }
        return new ModelAndView(success);

	}

    private List<ScreeningFrequency> populateBySampler(Sampler sampler, List<ParameterNames> parameters) {
        List<ScreeningFrequency> frequencies= new ArrayList<ScreeningFrequency>();
        List<ScreeningFrequency> returnFrequencies= new ArrayList<ScreeningFrequency>();
        MultiMap mhm = new MultiValueMap();
        ScreeningFrequency frequency;
        List<ParameterNames> depthList = new ArrayList<ParameterNames>();
        ParameterNames depth = new ParameterNames();
        depth.setId(new Long(6));
        depth.setName("Depth to Collar");
        depthList.add(depth);

        //Get screening frequencies
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

        return returnFrequencies;
    }
    private class ParameterNameEditor extends PropertyEditorSupport {

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            setValue(parameterNamesManager.get(Long.valueOf(text)));
        }

        @Override
        public String getAsText() {
            return ((ParameterNames) getValue()).getId().toString();
        }

    }
}
