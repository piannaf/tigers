package com.tiffany.validator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tiffany.webapp.controller.SampleSearch;
import java.text.SimpleDateFormat;

public class SampleSearchValidator implements Validator {
	public boolean supports(Class clazz) {
		return clazz.equals(SampleSearch.class);
	}
	
	public void validate(Object command, Errors errors) {
		SampleSearch search = (SampleSearch)command;
		
		String from = search.getFrom().trim();
		String to = search.getTo().trim();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = new Date();
		try {
			fromDate = dateFormat.parse("1990-01-01");
		} catch (Exception e) {}
		Date toDate = new Date();
		if (!from.equals("")) {
			try {
				fromDate = dateFormat.parse(from);
			} catch (Exception e) {
				errors.rejectValue("from", "errors.date", new Object[] {search.getFrom()}, null);
			}
		}
		if (!to.equals("")) {
			try {
				toDate = dateFormat.parse(to);
			} catch (Exception e) {
				errors.rejectValue("to", "errors.date", new Object[] {search.getTo()}, null);
			}
		}
		
		if (fromDate.compareTo(toDate) > 0) {
			errors.rejectValue("from", "errors.dateRange", new Object[] {search.getFrom()}, null);
		}
	}
}
