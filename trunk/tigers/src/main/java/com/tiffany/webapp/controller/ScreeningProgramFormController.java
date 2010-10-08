package com.tiffany.webapp.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindException;

import javax.servlet.http.*;

import com.tiffany.service.*;
import com.tiffany.model.Sampler;
import com.tiffany.model.ScreeningProgram;
import com.tiffany.model.ScreeningProgramSamplers;
import com.tiffany.model.ScreeningProgramSamplersId;

import java.util.Locale;
import java.util.List;
import java.util.Calendar;

/**
 * Author: Jane
 */
public class ScreeningProgramFormController extends BaseFormController {

    private WaterbodyManager waterbodyManager;
    private SamplerManager samplerManager;
    private ScreeningProgramManager screeningProgramManager;
    private ScreeningProgramSamplersManager screeningProgramSamplersManager;
    private UserManager userManager;

    public ScreeningProgramFormController() {
		setCommandClass(ScreeningProgramForm.class);
		setCommandName("screeningProgramForm");
        setSessionForm(true);
	}

    public void setWaterbodyManager(WaterbodyManager waterbodyManager) {
            this.waterbodyManager = waterbodyManager;
    }
    public void setSamplerManager(SamplerManager samplerManager) {
            this.samplerManager = samplerManager;
    }
    public void setUserManager(UserManager userManager) {
	    this.userManager = userManager;
    }
    public void setScreeningProgramManager(ScreeningProgramManager screeningProgramManager) {
            this.screeningProgramManager = screeningProgramManager;
    }
    public void setScreeningProgramSamplersManager(ScreeningProgramSamplersManager screeningProgramSamplersManager) {
            this.screeningProgramSamplersManager = screeningProgramSamplersManager;
    }

	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		return new ScreeningProgramForm();
	}
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
			Object command, BindException errors) throws Exception {

        ScreeningProgramForm screeningProgramForm = (ScreeningProgramForm) command;

        try {
		    char waterbodyType = waterbodyManager.getWaterBodyType(screeningProgramForm.getWaterbody()).charAt(0);
        } catch (NullPointerException e) {
            errors.rejectValue("waterbody", "errors.waterbodyNotFound");
        	return showForm(request, errors, getFormView());
        }
       /***********************************************/
       /*  Map display processing to go here, I think */
       /***********************************************/

        if (request.getParameter("next") != null) {
            /***********************************************/
            /*  Populate list from Map selection           */
            /*  Ensure at least one is selected            */
            /***********************************************/
            List<Sampler> samplers = samplerManager.findByWaterBody(screeningProgramForm.getWaterbody());
            ScreeningProgramSamplers screeningProgramSamplers;
            ScreeningProgramSamplersId screeningProgramSamplersId;

            ScreeningProgram screeningProgram = new ScreeningProgram();
            screeningProgram.setComplete('P');
            screeningProgram.setStartDate(Calendar.getInstance().getTime());
            screeningProgram.setUsername(userManager.getUserByUsername(request.getRemoteUser()).getId());
            screeningProgram = screeningProgramManager.save(screeningProgram);

            for (Sampler sampler: samplers) {
                screeningProgramSamplersId = new ScreeningProgramSamplersId();
                screeningProgramSamplersId.setId(screeningProgram.getId());
                screeningProgramSamplersId.setSampler(sampler.getId());

                screeningProgramSamplers = new ScreeningProgramSamplers();
                screeningProgramSamplers.setComplete('P');
                screeningProgramSamplers.setId(screeningProgramSamplersId);
                screeningProgramSamplers.setId(screeningProgramSamplersId);
                screeningProgramSamplersManager.save(screeningProgramSamplers);
            }
            String success = getSuccessView() +"?id=" + screeningProgram.getId() +  "&page=0";
            return new ModelAndView(success);
        }

        ModelAndView mv = new ModelAndView("officer/screeningprogram", getCommandName(), command);
        mv.addObject("firstTime", "nup");
		return mv;

	}
}
