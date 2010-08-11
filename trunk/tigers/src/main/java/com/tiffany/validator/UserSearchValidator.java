package com.tiffany.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tiffany.webapp.controller.UserSearch;

public class UserSearchValidator implements Validator {
	public boolean supports(Class clazz) {
		return clazz.equals(UserSearch.class);
	}
	
	public void validate(Object command, Errors errors) {
		UserSearch search = (UserSearch)command;
		String username = search.getUsername().trim();
		String companyName = search.getCompanyName().trim();
		String[] roles = search.getRoles();
	}
}
