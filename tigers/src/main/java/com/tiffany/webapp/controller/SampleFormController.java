package com.tiffany.webapp.controller;

import org.apache.commons.lang.StringUtils;

import org.apache.commons.logging.*;
import com.tiffany.service.GenericManager;
import com.tiffany.service.SampleManager;
import com.tiffany.service.SamplerManager;
import com.tiffany.service.UserManager;
import com.tiffany.model.Sample;
import com.tiffany.model.Sampler;
import com.tiffany.webapp.controller.BaseFormController;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SampleFormController extends BaseFormController {
    private final Log log = LogFactory.getLog(SampleController.class);
    private SampleManager sampleManager = null;
    private SamplerManager samplerManager = null;
    private UserManager userManager = null;

    public void setSampleManager(SampleManager sampleManager) {
        this.sampleManager = sampleManager;
    }

    public void setSamplerManager(SamplerManager samplerManager) {
        this.samplerManager = samplerManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public SampleFormController() {
        setCommandClass(Sample.class);
        setCommandName("sample");
    }

    protected Object formBackingObject(HttpServletRequest request)
    throws Exception {
        String id = request.getParameter("id");

        if (!StringUtils.isBlank(id)) {
            return sampleManager.get(new Long(id));
        }
        
        return new Sample();
    } 
    
    protected Map referenceData(HttpServletRequest request, Object command,
            Errors errors) throws Exception {
    	
    	Map referenceData = new HashMap();
        referenceData.put("samplerList", samplerManager.getAll());
    	referenceData.put("laboratoryList", userManager.getLaboratories());
    	
    	return referenceData;
    }
    
    public ModelAndView processFormSubmission(HttpServletRequest request,
	    HttpServletResponse response, Object command, BindException errors) throws Exception {
        log.debug("\n\t!!!! " + (Sample)command);
	
	return super.processFormSubmission(request, response, command, errors);
    }

    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
    throws Exception {
        log.debug("entering 'onSubmit' method...");

        Sample sample = (Sample) command;
        
        boolean isNew = (sample.getId() == null);
        String success = getSuccessView();
        
        Locale locale = request.getLocale();
        
        if (request.getParameter("delete") != null) {
            sampleManager.remove(sample.getId());
            saveMessage(request, getText("sample.deleted", locale));
        } else {
            sample = sampleManager.save(sample);
            String key = (isNew) ? "sample.added" : "sample.updated";
            saveMessage(request, getText(key, sample.getId().toString(), locale));

            if (!isNew) {
                success = "redirect:samplesearch.html";
            } else {
            	success = "redirect:samplesearch.html";
            }
        }

        return new ModelAndView(success);
    }
    //====================================================
    protected void onBind(HttpServletRequest request, Object command)
    throws Exception {
        // if the user is being deleted, turn off validation
        if (request.getParameter("delete") != null || request.getParameter("cancel") != null) {
            super.setValidateOnBinding(false);
        } else {
            super.setValidateOnBinding(true);
        }
    }
}
