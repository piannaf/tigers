package com.tiffany.webapp.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.Errors;
import org.springframework.validation.BindException;
import com.tiffany.service.ParameterNamesManager;
import com.tiffany.service.ScreeningProgramSamplersManager;
import com.tiffany.service.SamplerManager;
import com.tiffany.service.ScreeningProgramManager;
import com.tiffany.model.ScreeningProgramSamplers;
import com.tiffany.model.ParameterNames;
import com.tiffany.model.Sampler;

import javax.servlet.http.*;
import java.util.*;

/**
 * Author: Jane
 */
public class ScreeningProgramConfirmController extends BaseFormController {

    private ScreeningProgramSamplersManager screeningProgramSamplersManager;
    private SamplerManager samplerManager;
    private ScreeningProgramManager screeningProgramManager;
    private ParameterNamesManager parameterNamesManager;

    public ScreeningProgramConfirmController() {
		setCommandClass(ScreeningProgramConfirmForm.class);
		setCommandName("screeningProgramConfirmForm");
        setSessionForm(true);
	}
    public void setScreeningProgramSamplersManager(ScreeningProgramSamplersManager screeningProgramSamplersManager) {
            this.screeningProgramSamplersManager = screeningProgramSamplersManager;
    }
    public void setSamplerManager(SamplerManager samplerManager) {
            this.samplerManager = samplerManager;
    }
    public void setScreeningProgramManager(ScreeningProgramManager screeningProgramManager) {
            this.screeningProgramManager = screeningProgramManager;
    }
    public void setParameterNamesManager(ParameterNamesManager parameterNamesManager) {
            this.parameterNamesManager = parameterNamesManager;
    }
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
        String pageId = request.getParameter("page");
        String screeningProgramId = request.getParameter("id");
        ScreeningProgramConfirmForm screeningProgramConfirmForm = new ScreeningProgramConfirmForm();
        screeningProgramConfirmForm.setPageNo(Integer.parseInt(pageId));
        screeningProgramConfirmForm.setScreeningProgramId(Long.parseLong(screeningProgramId));

		return screeningProgramConfirmForm;
	}

     protected Map referenceData(HttpServletRequest request, Object command,
                                Errors errors) throws Exception {

	    ScreeningProgramConfirmForm screeningProgramConfirmForm = (ScreeningProgramConfirmForm) command;
        Long screeningProgramId = screeningProgramConfirmForm.getScreeningProgramId();
        List<ScreeningProgramSamplers> screeningProgramSamplers = screeningProgramSamplersManager.getSamplers(screeningProgramId);
        List<ScreeningItem> screeningItems = new ArrayList<ScreeningItem>();
        ScreeningItem screeningItem;

        Sampler sampler = samplerManager.get(screeningProgramSamplers.get(0).getId().getSampler());
		char waterbodyType = sampler.getWaterbody().getType();

        List<ParameterNames> parameters = parameterNamesManager.findByType(waterbodyType);

        for (ScreeningProgramSamplers samplers : screeningProgramSamplers) {
            screeningItem = new ScreeningItem();
            screeningItem.setTag(samplerManager.get(samplers.getId().getSampler()).getTag());
            screeningItem.setParameterNames(samplers.getParameterNames());
            screeningItems.add(screeningItem);
        }

        Map referenceData = new HashMap();
        referenceData.put("screeningItemList", screeningItems);
        referenceData.put("parameters", parameters);

	    return referenceData;
    }

	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
			Object command, BindException errors) throws Exception {

        String success;
        Locale locale = request.getLocale();
        ScreeningProgramConfirmForm screeningProgramConfirmForm = (ScreeningProgramConfirmForm) command;
        Long programId = screeningProgramConfirmForm.getScreeningProgramId();

        if (request.getParameter("mycancel") != null) {
            //Remove all evidence of this screening program
            List<ScreeningProgramSamplers> samplers = screeningProgramSamplersManager.getSamplers(programId);
            for (ScreeningProgramSamplers sampler: samplers) {
                screeningProgramSamplersManager.remove(sampler.getId());
            }
            screeningProgramManager.remove(programId);
            saveMessage(request, getText("screeningProgram.cancelled", locale));
            return new ModelAndView("redirect:../mainMenu.html");
        }
        if (request.getParameter("prev") != null) {
            success = "redirect:/officer/parameterselection.html?id=" + programId +  "&page=" + (screeningProgramConfirmForm.getPageNo());
            return new ModelAndView(success);
        }
        /*************************************************************************************************************/
        /* Email processing to go here                                                                               */
        /* Loop through                                                                                              */
        /*         List<ScreeningProgramSamplers> samplers = screeningProgramSamplersManager.getSamplers(programId); */
        /* One email for each contractor listing samplers and parameters to be sampled                               */
        /*************************************************************************************************************/
        success = getSuccessView();
        saveMessage(request, getText("screeningProgram.initiated", locale));
        return new ModelAndView(success);
	}
}
