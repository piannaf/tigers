package com.tiffany.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tiffany.service.MailEngine;
import com.tiffany.webapp.util.RequestUtil;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;

public class EmailExampleController extends BaseFormController {
	private final Log log = LogFactory.getLog(PasswordHintController.class);
    private MessageSource messageSource = null;
    protected MailEngine mailEngine = null;
    protected SimpleMailMessage message = null;
    
    public void setMessageSource(MessageSource messageSource) {
    	this.messageSource = messageSource;
    }
    
    public void setMailEngine(MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }
    
    public void setMessage(SimpleMailMessage message) {
        this.message = message;
    }
    
    public EmailExampleController() {
    	setCommandClass(EmailExample.class);
    	setCommandName("email");
    }
    
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	log.debug("entering \"formBackingObject\" method in EmailExampleController");
    	EmailExample email = new EmailExample();    	
    	String[] members = {"sean"};//, "jane", "nicholas", "dell", "xing", "justin"};
    	email.setTo(members);
    	return email;
    }
    
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
    		Object command, BindException errors) throws Exception {
    	log.debug("entering 'onSubmit' method at EmailExampleController");
    	Locale locale = request.getLocale();
    	String success = getSuccessView();
    	
    	MessageSourceAccessor text = new MessageSourceAccessor(messageSource, request.getLocale());
    	EmailExample email = (EmailExample)command;
    	String subject = email.getSubject();
    	String[] to = email.getTo();
    	String content = email.getContent();  	
    	message.setSubject("Tigers: " + subject);
    	message.setText(content);
    	for (int i=0; i<to.length; i++) {
    		message.setTo(emailLookup(to[i]));
	    	try {
	    		//mailEngine.sendHtmlWithBannerImage(message, "tigers.jpg");
	    		//mailEngine.sendMessage(message, "greeting.vm", null);
	    		Map model = new HashMap();
	    		model.put("fullname", to[i]);
	    		model.put("message", content);
	    		mailEngine.sendHtmlTemplateWithBannerImage(message, "greeting.vm", model, "tigers.jpg");
	    	} catch (MailException me) {
	    		saveError(request, getText("email.failed", locale));
	    		return new ModelAndView("emailexample", getCommandName(), command);
	    	}
    	}
    	saveMessage(request, getText("email.success", locale));
    	return new ModelAndView(success, getCommandName(), command);
    }
    
    private String emailLookup(String name) {
    	Hashtable<String, String> memberList = new Hashtable();
    	memberList.put("sean", "qqsean@hotmail.com");
    	memberList.put("jane", "jane.harrison@uqconnect.edu.au");
    	memberList.put("justin", "jmancine@gmail.com");
    	memberList.put("xing", "s4143448@student.uq.edu.au");
    	memberList.put("nicholas", "nicholas_cheng_sg@hotmail.com");
    	memberList.put("dell", "tibbar072000@yahoo.com.au");
    	return memberList.get(name);
    }
    
}
