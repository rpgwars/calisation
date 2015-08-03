package com.calisation.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.komar.domain.account.Account;

public class AccountValidator implements Validator{


	@Override
	public void validate(Object object, Errors errors) {
		
		ValidationUtils.rejectIfEmpty(errors, "activityName", "", "Specify activity name");
	}
	
	@Override
	public boolean supports(Class<?> cls) {
		return Account.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}

}
