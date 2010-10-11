package com.tiffany.webapp.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.springframework.mail.MailException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.tiffany.model.ParameterNames;
import com.tiffany.model.Sampler;
import com.tiffany.model.ScreeningProgramSamplers;
import com.tiffany.model.User;
import com.tiffany.service.ParameterNamesManager;
import com.tiffany.service.SamplerManager;
import com.tiffany.service.ScreeningProgramManager;
import com.tiffany.service.ScreeningProgramSamplersManager;

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

    public void setScreeningProgramSamplersManager(
	    ScreeningProgramSamplersManager screeningProgramSamplersManager) {
	this.screeningProgramSamplersManager = screeningProgramSamplersManager;
    }

    public void setSamplerManager(SamplerManager samplerManager) {
	this.samplerManager = samplerManager;
    }

    public void setScreeningProgramManager(
	    ScreeningProgramManager screeningProgramManager) {
	this.screeningProgramManager = screeningProgramManager;
    }

    public void setParameterNamesManager(
	    ParameterNamesManager parameterNamesManager) {
	this.parameterNamesManager = parameterNamesManager;
    }

    protected Object formBackingObject(HttpServletRequest request)
	    throws Exception {
	String pageId = request.getParameter("page");
	String screeningProgramId = request.getParameter("id");
	ScreeningProgramConfirmForm screeningProgramConfirmForm = new ScreeningProgramConfirmForm();
	screeningProgramConfirmForm.setPageNo(Integer.parseInt(pageId));
	screeningProgramConfirmForm.setScreeningProgramId(Long
		.parseLong(screeningProgramId));

	return screeningProgramConfirmForm;
    }

    protected Map referenceData(HttpServletRequest request, Object command,
	    Errors errors) throws Exception {

	ScreeningProgramConfirmForm screeningProgramConfirmForm = (ScreeningProgramConfirmForm) command;
	Long screeningProgramId = screeningProgramConfirmForm
		.getScreeningProgramId();
	List<ScreeningProgramSamplers> screeningProgramSamplers = screeningProgramSamplersManager
		.getSamplers(screeningProgramId);
	List<ScreeningItem> screeningItems = new ArrayList<ScreeningItem>();
	ScreeningItem screeningItem;

	Sampler sampler = samplerManager.get(screeningProgramSamplers.get(0)
		.getId().getSampler());
	char waterbodyType = sampler.getWaterbody().getType();

	List<ParameterNames> parameters = parameterNamesManager
		.findByType(waterbodyType);

	for (ScreeningProgramSamplers samplers : screeningProgramSamplers) {
	    screeningItem = new ScreeningItem();
	    screeningItem.setTag(samplerManager.get(
		    samplers.getId().getSampler()).getTag());
	    screeningItem.setParameterNames(samplers.getParameterNames());
	    screeningItems.add(screeningItem);
	}

	Map referenceData = new HashMap();
	referenceData.put("screeningItemList", screeningItems);
	referenceData.put("parameters", parameters);

	return referenceData;
    }

    public ModelAndView onSubmit(HttpServletRequest request,
	    HttpServletResponse response, Object command, BindException errors)
	    throws Exception {

	String success;
	Locale locale = request.getLocale();
	ScreeningProgramConfirmForm screeningProgramConfirmForm = (ScreeningProgramConfirmForm) command;
	Long programId = screeningProgramConfirmForm.getScreeningProgramId();

	if (request.getParameter("mycancel") != null) {
	    // Remove all evidence of this screening program
	    List<ScreeningProgramSamplers> samplers = screeningProgramSamplersManager
		    .getSamplers(programId);
	    for (ScreeningProgramSamplers sampler : samplers) {
		screeningProgramSamplersManager.remove(sampler.getId());
	    }
	    screeningProgramManager.remove(programId);
	    saveMessage(request, getText("screeningProgram.cancelled", locale));
	    return new ModelAndView("redirect:../mainMenu.html");
	}
	if (request.getParameter("prev") != null) {
	    success = "redirect:/officer/parameterselection.html?id="
		    + programId + "&page="
		    + (screeningProgramConfirmForm.getPageNo());
	    return new ModelAndView(success);
	}
	/*************************************************************************************************************/
	// Email processing
	MultiMap contractorsSamplers = new MultiHashMap();
	List<ScreeningProgramSamplers> samplers = screeningProgramSamplersManager
		.getSamplers(programId);
	for (ScreeningProgramSamplers sps : samplers) {
	    Sampler sampler = samplerManager.get(sps.getId().getSampler());
	    User contractor = sampler.getContractor();
	    contractorsSamplers.put(contractor, sps);
	}
	List<User> contractorList = new ArrayList<User>();
	contractorList.addAll(contractorsSamplers.keySet());
	for (User contractor : contractorList) {
	    List<ScreeningProgramSamplers> mySpss = (List) contractorsSamplers
		    .get(contractor);
	    DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
	    String subject = "Screening Programe for Sampler ";
	    String content = "Following are the details of the screening programe<br/><br/>";
	    for (ScreeningProgramSamplers sps : mySpss) {
		String startDate = format.format(sps.getScreeningprogram()
			.getStartDate());
		String samplerId = samplerManager.get(sps.getId().getSampler())
			.getTag();
		List<ParameterNames> janesParameterList = sps
			.getParameterNames();
		String parameters = "";
		for (ParameterNames janesParameter : janesParameterList) {
		    parameters += janesParameter.getName() + ", ";
		}
		parameters = parameters.substring(0, parameters.length() - 2);
		// =========================================================
		subject += samplerId + ", ";
		content += "Sampler Id: " + samplerId + "<br/>"
			+ "Start Date: " + startDate + "<br/>" + "Parameters: "
			+ parameters + "<br/><br/>";
	    }
	    subject = subject.substring(0, subject.length() - 2);

	    // ======================== send email
	    // ===============================
	    message.setSubject(subject);
	    Map<String, Serializable> model = new HashMap<String, Serializable>();
	    model.put("content", content);
	    try {
		sendUserMessage(contractor, model);
	    } catch (MailException me) {
		saveError(request, getText("email.failed", locale));
		log.debug(me.toString());
	    } catch (Exception e) {
		log.debug("can't send email");
	    }
	}
	/*
	 * One email for each contractor listing samplers and parameters to be
	 * sampled
	 */
	/*************************************************************************************************************/
	success = getSuccessView();
	saveMessage(request, getText("screeningProgram.initiated", locale));
	return new ModelAndView(success);
    }
}
