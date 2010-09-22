package com.tiffany.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.beans.NotReadablePropertyException;
import com.tiffany.model.ScreeningFrequency;

/**
 * Author: Jane
 */
public class ScreeningFrequencyValidator implements Validator {

	public boolean supports(Class clazz) {
		return clazz.equals(ScreeningFrequency.class);
	}

	public void validate(Object command, Errors errors) {
		ScreeningFrequency frequency = (ScreeningFrequency)command;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "errors.required", new Object[] {"Description"});
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "frequency", "errors.required", new Object[] {"Frequency"});

        if (frequency.getParameterNames() == null) {
            errors.rejectValue("parameterNames","errors.parameterRequired");
        };
    }
}
