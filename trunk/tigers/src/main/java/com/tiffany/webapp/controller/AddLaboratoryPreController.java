package com.tiffany.webapp.controller;

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
import org.springframework.web.servlet.view.RedirectView;

import com.tiffany.Constants;
import com.tiffany.service.SamplerManager;
import com.tiffany.service.UserExistsException;
import com.tiffany.service.UserManager;
import com.tiffany.model.User;
import com.tiffany.webapp.controller.Tag;

public class AddLaboratoryPreController extends BaseFormController {
	private SamplerManager samplerManager;
	private UserManager userManager;
	
	public void setSamplerManager(SamplerManager samplerManager) {
		this.samplerManager = samplerManager;
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public AddLaboratoryPreController() {
		setCommandName("lab");
        setCommandClass(Tag.class);
	}
	//=============
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	log.debug("\n===== formBackingObject =====");
    	Tag tag = new Tag();    	
    	return tag;
    }
	//=======================referenceData=============================================
//    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
//		log.debug("referenceData...");
//		Locale locale = request.getLocale();
//		Map<String, Object> refData = new HashMap();
//		User remoteUser = userManager.getUserByUsername((request.getRemoteUser()));
//		List<String> samplerIdList = samplerManager.getTagListForLaboratory(remoteUser);
//		java.util.Collections.sort(samplerIdList);
//		refData.put("samplerIdList", samplerIdList);
//		if (samplerIdList.size() == 0)	saveMessage(request, getText("findContractor.noTag", locale));
//		return refData;
//	}
    //=======================onSubmit=============================================
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
            BindException errors) throws Exception {
    	log.debug("entering 'onSubmit' method...");
    	
    	
    	
    	String success = getSuccessView();
    	Tag tag = (Tag)command;  	
    	String username = tag.getTag();
    	log.debug(username);
    	User lab = null;
    	try {
    		lab = userManager.getUserByUsername(username);
    	} catch (Exception e) {
    		log.debug(e.toString());
    	}
    	//==========================
    	if (lab != null) {
    		User me = userManager.getUserByUsername(request.getRemoteUser());
        	me.addChildren(lab);
        	try {
                userManager.saveUser(me);
            } catch (AccessDeniedException ade) {
                // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
                log.warn(ade.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return null;
            } catch (UserExistsException e) {
            	
            }
            saveMessage(request, "The laboratory " + lab.getUsername() + " has been added.");
            return new ModelAndView("mainMenu");
        //=====================================
    	} else {
    		log.debug("add new user with username: " + username);
    		return new ModelAndView("redirect:/contractor/addlaboratory.html?username=" + username);
    	}
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
