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
import com.tiffany.service.UserExistsException;
import com.tiffany.service.UserManager;

public class AssignLaboratoryController extends BaseFormController {
	private SamplerManager samplerManager;
	private UserManager userManager;
	
	public void setSamplerManager(SamplerManager samplerManager) {
		this.samplerManager = samplerManager;
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public AssignLaboratoryController() {
		setCommandName("assignLab");
        setCommandClass(AssignLab.class);
	}
	//====================================================================================
    protected ModelAndView showForm(HttpServletRequest request,
                                    HttpServletResponse response,
                                    BindException errors)
    throws Exception {

    // not officer not adding
    	if (!request.isUserInRole("ROLE_CONTRACTOR")) {
    		response.sendError(HttpServletResponse.SC_FORBIDDEN);
    		log.warn("User '" + request.getRemoteUser() + "' is trying to assign sampler to laboratory.");
    		throw new AccessDeniedException("You do not have permission to assign sampler.");
    	} 
        return super.showForm(request, response, errors);
    }
	//=============
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	log.debug("\n===== formBackingObject =====");
    	AssignLab assignLab = new AssignLab();    	
    	return assignLab;
    }
	//=======================referenceData=============================================
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
		log.debug("referenceData...");
		Locale locale = request.getLocale();
		Map<String, Object> referenceData = new HashMap();
		
		User contractor = userManager.getUserByUsername((request.getRemoteUser()));
		//========= sampler id list ==========
		List<Sampler> samplerList = samplerManager.getMySamplers(contractor);
		List<String> samplerIdList = new ArrayList<String>();
		if (samplerList.size() != 0) { 
			for (Sampler sampler : samplerList)	samplerIdList.add(sampler.getTag());
		} else {
			saveError(request, getText("assignLaboratory.noSamplers", locale));
		}
		
		//========= laboratory list ==========
		List<User> laboratoryList = userManager.getMyLaboratories(request.getRemoteUser());
		List<String> labList = new ArrayList<String>();
		if (laboratoryList.size() != 0) {
			for (User user: laboratoryList) labList.add(user.getUsername());
		} else {
			saveError(request, getText("assignLaboratory.noLabs", locale));
		}
		//======================================
		log.debug("sampler#: "+samplerList.size()+" lab#: "+labList.size());
		//java.util.Collections.sort(samplerIdList);
		referenceData.put("samplerIdList", samplerIdList);
		referenceData.put("labList", labList);
		return referenceData;
	}
    //=======================onSubmit=============================================
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
            BindException errors) throws Exception {
    	log.debug("entering 'onSubmit' method...");
    	
    	
    	
    	String success = getSuccessView();
    	AssignLab assignLab = (AssignLab)command;  	
    	String labname = assignLab.getLab();
    	String samplerId = assignLab.getSamplerId();
    	log.debug("assign sampler '" + samplerId + " ' to user '" + labname + " '");
    	
    	Sampler sampler = samplerManager.getByTag(samplerId);
		User lab = userManager.getUserByUsername(labname);
		
		if (sampler != null && lab != null) {
			sampler.setLaboratory(lab);
	    	try {
	    		samplerManager.save(sampler);
	    	} catch (Exception e) {
	    		saveError(request, "The laboratory " + labname + " has been assigned to " +
		    			"the sampler '" + samplerId + "'.................not really");
	    		showForm(request, response, errors);
	    	}
	    	saveMessage(request, "The laboratory " + labname + " has been assigned to " +
	    			"the sampler '" + samplerId + "'.");
            return new ModelAndView("mainMenu");
    	} else {
    		saveError(request, "Invalid sampler or laboratory");
    		showForm(request, response, errors);
    	}
		return new ModelAndView("mainMenu");
    }
  //====================== onBind ==================================
//    protected void onBind(HttpServletRequest request, Object command) throws Exception {
//    	log.debug("\n===== onBind =====");
//        // if the user is being deleted, turn off validation
//        if (request.getParameter("emailTo") != null) {
//            super.setValidateOnBinding(false);
//        } else {
//            super.setValidateOnBinding(true);
//        }
//    }
}
