package com.example.companydemo.core;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates that the {@code java.lang.String} is a valid {@link Phone} value.
 *
 * @author Sasa Bolic
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

	@Override
	public void initialize(Phone paramA) {
	}

	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext ctx) {
		if(phoneNumber == null || phoneNumber.isEmpty()){
			return true;
		}

		return phoneNumber.matches("^\\+(?:[0-9] ?){6,14}[0-9]$");
	}
}