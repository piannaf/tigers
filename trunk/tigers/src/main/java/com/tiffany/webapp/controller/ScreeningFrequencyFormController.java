package com.tiffany.webapp.controller;

import com.tiffany.model.ScreeningFrequency;
import com.tiffany.model.ParameterNames;
import com.tiffany.model.Sampler;
import com.tiffany.service.ScreeningFrequencyManager;
import com.tiffany.service.ParameterNamesManager;
import com.tiffany.service.SamplerManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.*;
import java.beans.PropertyEditorSupport;

/**
 * Author: Jane
 */
public class ScreeningFrequencyFormController extends BaseFormController {

    ScreeningFrequencyManager screeningFrequencyManager;
    ParameterNamesManager parameterNamesManager;
    SamplerManager samplerManager;

    public void setScreeningFrequencyManager(ScreeningFrequencyManager screeningFrequencyManager) {
        this.screeningFrequencyManager = screeningFrequencyManager;
    }
    public void setParameterNamesManager(ParameterNamesManager parameterNamesManager) {
        this.parameterNamesManager = parameterNamesManager;
    }
    public void setSamplerManager(SamplerManager samplerManager) {
            this.samplerManager = samplerManager;
    }

    public ScreeningFrequencyFormController() {
        setCommandClass(ScreeningFrequency.class);
        setCommandName("screeningFrequency");
        // This stops the creation of a new command object each time the screen is refreshed
        setSessionForm(true);
    }


    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.registerCustomEditor(ParameterNames.class, new ParameterNameEditor());
}

    protected Object formBackingObject(HttpServletRequest request)
    throws Exception {
        String samplerId = request.getParameter("samplerid");
        String frequencyId = request.getParameter("frequencyid");

        if (!StringUtils.isBlank(frequencyId)) {
            ScreeningFrequency screeningFrequency = screeningFrequencyManager.get(new Long(frequencyId));

            return screeningFrequency;
        }
        Sampler sampler = samplerManager.get(new Long(samplerId)); 
        // Initialise foreign keys because Spring doesn't like null values
        ScreeningFrequency screeningFrequency = new ScreeningFrequency();
        screeningFrequency.setSampler(sampler);

        return screeningFrequency;
    }

    protected Map referenceData(HttpServletRequest request, Object command,
            Errors errors) throws Exception {

        ScreeningFrequency screeningFrequency = (ScreeningFrequency) command;

        Sampler sampler = samplerManager.get(screeningFrequency.getSampler().getId());
		char waterbodyType = sampler.getWaterbody().getType();
		String tag = sampler.getTag();

        List<ParameterNames> parameterNames = parameterNamesManager.findByType(waterbodyType);

        Map referenceData = new HashMap();
      	referenceData.put("heading", "Screening Frequency for " + tag);
        referenceData.put("parameterNames", parameterNames);
                             
    	return referenceData;
    }

    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
    throws Exception {

        ScreeningFrequency screeningFrequency = (ScreeningFrequency) command;

        boolean isNew = (screeningFrequency.getId() == null);

        String success = getSuccessView() + "?id=" + screeningFrequency.getSampler().getId();

        Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
       		screeningFrequencyManager.remove(screeningFrequency.getId());
        	saveMessage(request, getText("screeningFrequency.deleted", locale));
        } else {

            screeningFrequency = screeningFrequencyManager.save(screeningFrequency);
        	String key = (isNew) ? "screeningFrequency.added" : "screeningFrequency.updated";
        	saveMessage(request, getText(key, locale));
        }

        return new ModelAndView(success);
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
