package com.tiffany.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.tiffany.model.Sampler;
import com.tiffany.model.User;
import com.tiffany.service.SamplerManager;
import com.tiffany.service.UserManager;

public class AssignLabPostController extends BaseFormController {
    private SamplerManager samplerManager;
    private UserManager userManager;

    public void setSamplerManager(SamplerManager samplerManager) {
	this.samplerManager = samplerManager;
    }

    public void setUserManager(UserManager userManager) {
	this.userManager = userManager;
    }

    public AssignLabPostController() {
	setCommandName("assignLab");
	setCommandClass(AssignLab.class);
    }

    // ====================================================================================
    protected ModelAndView showForm(HttpServletRequest request,
	    HttpServletResponse response, BindException errors)
	    throws Exception {

	// not officer not adding
	if (!request.isUserInRole("ROLE_CONTRACTOR")) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    log.warn("User '" + request.getRemoteUser()
		    + "' is trying to assign sampler to laboratory.");
	    throw new AccessDeniedException(
		    "You do not have permission to assign sampler.");
	}
	if (request.getParameter("tag") == null) {
	    saveError(request, "Please select a sampler from the list");
	    return new ModelAndView("redirect:/contractor/assignlabpre.html");
	} else {
	    User contractor = userManager.getUserByUsername(request
		    .getRemoteUser());
	    List<Sampler> samplerList = samplerManager
		    .getMySamplers(contractor);
	    Sampler sampler = samplerManager.getByTag(request
		    .getParameter("tag"));
	    if (!samplerList.contains(sampler)) {
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
		log.warn("User '"
			+ request.getRemoteUser()
			+ "' is trying to assign others' sampler to laboratory.");
		throw new AccessDeniedException(
			"You do not have permission to assign this sampler.");
	    }
	}
	return super.showForm(request, response, errors);
    }

    // =============
    protected Object formBackingObject(HttpServletRequest request)
	    throws Exception {
	log.debug("\n===== formBackingObject =====");
	String samplerId = request.getParameter("tag");
	log.debug("request: " + samplerId);
	AssignLab assignLab = new AssignLab();
	if (samplerId != null)
	    assignLab.setSamplerId(samplerId);
	return assignLab;
    }

    // =======================referenceData=============================================
    protected Map referenceData(HttpServletRequest request, Object command,
	    Errors errors) throws Exception {
	log.debug("referenceData...");
	Locale locale = request.getLocale();
	Map<String, Object> referenceData = new HashMap();

	User contractor = userManager.getUserByUsername((request
		.getRemoteUser()));

	String samplerId = request.getParameter("tag");
	Sampler sampler = null;
	if (samplerId != null)
	    sampler = samplerManager.getByTag(samplerId);

	// ========= laboratory list ==========
	List<User> laboratoryList = userManager.getMyLaboratories(request
		.getRemoteUser());
	List<String> labList = new ArrayList<String>();
	if (laboratoryList.size() != 0) {
	    for (User user : laboratoryList)
		labList.add(user.getUsername());
	} else {
	    saveError(request, getText("assignLaboratory.noLabs", locale));
	}
	// ======================================
	log.debug("lab#: " + labList.size());
	java.util.Collections.sort(labList);
	List<String> labSelectList = new ArrayList<String>();
	labSelectList.add("-- none --");
	labSelectList.addAll(labList);
	referenceData.put("labList", labSelectList);
	if (sampler != null)
	    referenceData.put("sampler", sampler);
	return referenceData;
    }

    // =======================onSubmit=============================================
    public ModelAndView onSubmit(HttpServletRequest request,
	    HttpServletResponse response, Object command, BindException errors)
	    throws Exception {
	log.debug("entering 'onSubmit' method...");

	String success = getSuccessView();
	AssignLab assignLab = (AssignLab) command;
	String labname = assignLab.getLab();
	String samplerId = assignLab.getSamplerId();
	log.debug("request: assign sampler '" + samplerId + " ' to user '"
		+ labname + " '");
	Sampler sampler = samplerManager.getByTag(samplerId);
	User lab = null;
	try {
	    lab = userManager.getUserByUsername(labname);
	} catch (Exception e) {
	    log.debug(e.toString());
	}

	if (sampler != null) {
	    sampler.setLaboratory(lab);
	    try {
		samplerManager.save(sampler);
	    } catch (Exception e) {
		saveError(request, "The laboratory " + labname
			+ " has been assigned to " + "the sampler '"
			+ samplerId + "'.................not really");
		showForm(request, response, errors);
	    }
	    if (labname.equals("-- none --")) {
		saveMessage(request, "Laboratory has been removed from "
			+ "the sampler '" + samplerId + "'.");
	    } else {
		saveMessage(request, "The laboratory " + labname
			+ " has been assigned to " + "the sampler '"
			+ samplerId + "'.");
	    }

	    return new ModelAndView(getSuccessView());
	} else {
	    saveError(request, "Invalid sampler or laboratory");
	    showForm(request, response, errors);
	}
	return new ModelAndView(getSuccessView());
    }
    // ====================== onBind ==================================
    // protected void onBind(HttpServletRequest request, Object command) throws
    // Exception {
    // log.debug("\n===== onBind =====");
    // // if the user is being deleted, turn off validation
    // if (request.getParameter("emailTo") != null) {
    // super.setValidateOnBinding(false);
    // } else {
    // super.setValidateOnBinding(true);
    // }
    // }
}
