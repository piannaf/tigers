package com.tiffany.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.tiffany.Constants;
import com.tiffany.service.SamplerManager;
import com.tiffany.service.UserManager;
import com.tiffany.model.User;
import com.tiffany.webapp.controller.Tag;

public class FindContractorController extends BaseFormController {
	private SamplerManager samplerManager;
	private UserManager userManager;
	
	public void setSamplerManager(SamplerManager samplerManager) {
		this.samplerManager = samplerManager;
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public FindContractorController() {
		setCommandName("tag");
        setCommandClass(Tag.class);
	}
	//=============
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	log.debug("\n===== formBackingObject =====");
    	Tag tag = new Tag();    	
    	return tag;
    }
	//=======================referenceData=============================================
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
		log.debug("referenceData...");
		Locale locale = request.getLocale();
		Map<String, Object> refData = new HashMap();
		User remoteUser = userManager.getUserByUsername((request.getRemoteUser()));
		List<String> samplerIdList = samplerManager.getTagListForLaboratory(remoteUser);
		refData.put("samplerIdList", samplerIdList);
		if (samplerIdList.size() == 0)	saveMessage(request, getText("findContractor.noTag", locale));
		return refData;
	}
    //=======================onSubmit=============================================
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
            BindException errors) throws Exception {
    	log.debug("entering 'onSubmit' method...");
    	
    	if (request.getParameter("emailTo") != null) {
    		User user = userManager.getUserByUsername(request.getParameter("emailTo"));
    		request.getSession().setAttribute("sendTo", user); 
    		log.debug("sendTo: " +request.getSession(false).getAttribute("sendTo") );
    		return new ModelAndView(new RedirectView("../sendemail.html"));
    	}
    	
    	String success = getSuccessView();
    	Tag tag = (Tag)command;  	
    	
    	User contractor;
    	try {
    		contractor = samplerManager.getContractorByTag2(tag.getTag());
    		log.debug("contractor: "+contractor.getUsername());
    	} catch (Exception e) {
    		errors.rejectValue("tag", "errors.findContractor.invalidTag", new Object [] {"Sampler Id"}, null);
    		return showForm(request, response, errors);    		
    	}
    	User remoteUser = userManager.getUserByUsername((request.getRemoteUser()));
    	List<String> samplerIdList = samplerManager.getTagListForLaboratory(remoteUser);
    	ModelAndView mv = new ModelAndView(success, getCommandName(), command);
        mv.addObject("contractor", contractor);
        mv.addObject("firstTime", "no");
        mv.addObject("id", tag.getTag());
        mv.addObject("samplerIdList",samplerIdList);
        return mv;
    }
  //====================== onBind ==================================
    protected void onBind(HttpServletRequest request, Object command) throws Exception {
    	log.debug("\n===== onBind =====");
        // if the user is being deleted, turn off validation
        if (request.getParameter("emailTo") != null) {
            super.setValidateOnBinding(false);
        } else {
            super.setValidateOnBinding(true);
        }
    }
}
