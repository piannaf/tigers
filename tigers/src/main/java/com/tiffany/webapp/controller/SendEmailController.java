package com.tiffany.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.tiffany.service.MailEngine;
import com.tiffany.service.UserManager;
import com.tiffany.model.User;

public class SendEmailController extends BaseFormController {
	private final Log log = LogFactory.getLog(SendEmailController.class);
    private UserManager userManager;
    
    public void setUserManager(UserManager userManager) {
    	this.userManager = userManager;
    }  
    
    public SendEmailController() {
    	setCommandClass(Email.class);
    	setCommandName("email");
    }
    //====================== onBind ==================================
    
    //====================== isFormSubmission ========================
    
    //====================== showForm =============================================
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response,
            BindException errors) throws Exception {
    	log.debug("\n===== showForm =====");
    	if (request.getSession().getAttribute("sendTo") == null) {
    		throw new AccessDeniedException("You do not have permission to send an email directly.");
    	}
    	return super.showForm(request, response, errors);
	}
    //====================== formBackingObject ================================
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	log.debug("\n===== formBackingObject =====");
    	Email email = new Email();    	
    	email.setTo((String)request.getSession().getAttribute("sendTo"));
    	return email;
    }
    //====================== referenceData ================================
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
		log.debug("\n===== referenceData =====");
		Email email = (Email)command;
		Map<String, Object> refData = new HashMap();
		User user = userManager.getUserByUsername(email.getTo());
		refData.put("company", user.getCompanyName());
		return refData;
	}
    //======================= onSubmit =========================================
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
    		Object command, BindException errors) throws Exception {
    	log.debug("\n===== onSubmit =====");
    	Locale locale = request.getLocale();
    	String success = getSuccessView();
    	
    	//MessageSourceAccessor text = new MessageSourceAccessor(messageSource, request.getLocale());
    	Email email = (Email)command;
    	
    	//===== message model ======
		Map model = new HashMap();
		model.put("message", email.getContent());
		User fromUser = userManager.getUserByUsername(request.getRemoteUser());
		User toUser = userManager.getUserByUsername(email.getTo());
		model.put("fromUser", fromUser);
		//==========================
		log.debug("fromUser:"+fromUser.getUsername()+" toUser:"+toUser.getUsername());
		try {
    		sendUserMessage(toUser, email.getSubject(), model);
    		request.getSession().removeAttribute("sendTo");
	    } catch (MailException me) {
	    	saveError(request, getText("email.failed", locale));
	    	return new ModelAndView("sendemail", getCommandName(), command);
	    } catch (Exception e) {
	    	log.debug("Got exception:"+e.toString());
	    }
    	
    	saveMessage(request, getText("email.success", locale));
    	return new ModelAndView(success, getCommandName(), command);
    }
}
