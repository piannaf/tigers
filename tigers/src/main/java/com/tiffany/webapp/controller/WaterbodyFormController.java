package com.tiffany.webapp.controller;

import com.tiffany.service.WaterbodyManager;
import com.tiffany.service.ParameterNamesManager;
import com.tiffany.service.ParameterThresholdsManager;
import com.tiffany.service.SamplerManager;
import com.tiffany.model.Waterbody;
import com.tiffany.model.ParameterNames;
import com.tiffany.model.ParameterThresholds;
import com.tiffany.model.Sampler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.math.BigDecimal;

/**
 * Author: Jane
 */
public class WaterbodyFormController  extends BaseFormController {
/**
 * This class controls the "waterbody" maintenance form
 *
 * @author Jane
 */
	private WaterbodyManager waterbodyManager;
	private ParameterNamesManager parameterNamesManager;
	private ParameterThresholdsManager parameterThresholdsManager;
	private SamplerManager samplerManager;

    public void setWaterbodyManager(WaterbodyManager waterbodyManager) {
        this.waterbodyManager = waterbodyManager;
    }
    public void setParameterNamesManager(ParameterNamesManager parameterNamesManager) {
        this.parameterNamesManager = parameterNamesManager;
    }
    public void setParameterThresholdsManager(ParameterThresholdsManager parameterThresholdsManager) {
        this.parameterThresholdsManager = parameterThresholdsManager;
    }
    public void setSamplerManager(SamplerManager samplerManager) {
        this.samplerManager = samplerManager;
    }

    public WaterbodyFormController() {
        setCommandClass(WaterbodyForm.class);
        setCommandName("waterbodyForm");
        // This stops the creation of a new command object each time the screen is refreshed
        setSessionForm(true);
    }

    protected Object formBackingObject(HttpServletRequest request)
    throws Exception {
        String id = request.getParameter("id");
        WaterbodyForm waterbodyForm = new WaterbodyForm();
        ParameterThresholds parameterThreshold;
        List<ParameterThresholds> parameterThresholds = new ArrayList();

        if (!StringUtils.isBlank(id)) {
            Waterbody waterbody = waterbodyManager.get(new Long(id));

            waterbodyForm.setWaterbody(waterbody);

            List<ParameterNames> parameterNames = parameterNamesManager.findByType(waterbody.getType());

            for (ParameterNames parameter : parameterNames) {
                parameterThreshold = parameterThresholdsManager.findByWaterBodyAndId(waterbody.getName(),parameter.getId());

                if (null == parameterThreshold) {
                    parameterThreshold = new ParameterThresholds();
                    parameterThreshold.setWaterbody(waterbody);
                    parameterThreshold.setParameter(parameter);
                    parameterThreshold.setMin(new BigDecimal(0));
                    parameterThreshold.setMax(new BigDecimal(0));
                }
                parameterThresholds.add(parameterThreshold);
            }
            waterbodyForm.setParameterThresholds(parameterThresholds);

            return waterbodyForm;
        }

        return waterbodyForm;
    }

    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
    throws Exception {

        WaterbodyForm waterbodyForm = (WaterbodyForm) command;

        boolean isNew = (waterbodyForm.getWaterbody().getId() == null);

        String success = getSuccessView();
        Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
            if (samplersExist(waterbodyForm.getWaterbody().getName())) {
        			errors.rejectValue("waterbody.name", "errors.waterbodyDeletion");
        			return showForm(request, errors, getFormView());
        	}
            else {
        	    waterbodyManager.remove(waterbodyForm.getWaterbody().getId());
        	    saveMessage(request, getText("waterbody.deleted", locale));
            }
        } else if (request.getParameter("update") != null){
            if (!validateThresholds(waterbodyForm.getParameterThresholds(),errors)) {
                return showForm(request, errors, getFormView());
            }
            else {
                for (ParameterThresholds parameterThreshold : waterbodyForm.getParameterThresholds()) {
                    parameterThresholdsManager.save(parameterThreshold);
                }
                saveMessage(request, getText("parameterThresholds.updated", locale));
            }
        } else {
            if (!validateName(waterbodyForm.getWaterbody().getName(),errors)) {
                return showForm(request, errors, getFormView());
            }
        	waterbodyManager.save(waterbodyForm.getWaterbody());
        	String key = (isNew) ? "waterbody.added" : "waterbody.updated";
        	saveMessage(request, getText(key, waterbodyForm.getWaterbody().getName(), locale));
        }

        return new ModelAndView(success);
    }
    private boolean samplersExist(String waterbody) {
		List<Sampler> samplers = samplerManager.findByWaterBody(waterbody);

		return (null != samplers);
	}
    private boolean validateThresholds(List<ParameterThresholds> parameterThresholds,
                                 BindException errors   ) {
		boolean okeydokey = true;
        BigDecimal min;
        BigDecimal max;

        for (ParameterThresholds parameterThreshold : parameterThresholds) {
            min = parameterThreshold.getMin();
            max = parameterThreshold.getMax();

            if (parameterThreshold.getParameter().getId() == 1) {
                if (max.compareTo(new BigDecimal(14)) > 0) {
                    errors.rejectValue("parameterThresholds[0].min", "errors.invalid",
                                        new Object[] {parameterThreshold.getParameter().getName()}, null);
                    okeydokey = false;
                }
            }
            if (min.compareTo(max) < 0) {
                if (min.compareTo(new BigDecimal(0)) < 0) {
                    errors.rejectValue("parameterThresholds[0].min", "errors.invalid",
                                        new Object[] {parameterThreshold.getParameter().getName()}, null);
                    okeydokey = false;
                }
            }
            else {
                errors.rejectValue("parameterThresholds[0].min", "errors.minMax",
                                    new Object[] {parameterThreshold.getParameter().getName()}, null);
                okeydokey = false;
            }
        }

        return okeydokey;

    }

    private boolean validateName(String name, BindException errors) {
		boolean okeydokey = true;

        if (null != waterbodyManager.findLikeName(name)) {
            errors.rejectValue("waterbody", "errors.waterbodyFound");
            okeydokey = false;
        }

        return okeydokey;

    }

}