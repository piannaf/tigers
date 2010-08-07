package com.tiffany.validator;

import org.springframework.validation.*;
import com.tiffany.webapp.controller.EmailExample;

public class EmailExampleValidator implements Validator {
	public boolean supports(Class clazz) {
		return clazz.equals(EmailExample.class);
	}
	
	public void validate(Object command, Errors errors) {
		EmailExample email = (EmailExample)command;
		String[] sendTo = email.getTo();
		String subject = email.getSubject().trim();
		String content = email.getContent().trim();
		if (sendTo.length <= 0) {
			errors.rejectValue("to", "errors.required", new Object[] {"Send To"}, null);
		}
		if (subject.equals("")) {
			errors.rejectValue("subject", "errors.required", new Object[] {"Subject"}, null);
		}
		if (content.equals("")) {
			errors.rejectValue("content", "errors.required", new Object[] {"Content"}, null);
		}
	}
}
