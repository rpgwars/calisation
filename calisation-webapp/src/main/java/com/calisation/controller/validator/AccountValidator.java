package com.calisation.controller.validator;

import com.komar.domain.account.transfer.AccountTO;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

public class AccountValidator implements Validator{

	public static final String emailField = "email";
	public static final String passwordField = "password";
	public static final String repeatedPasswordField = "repeatedPassword";
	public static final String nameField = "name";

	private static final String emailError = "validation.account.email.error";
	private static final String passwordError = "validation.account.password.error";
	private static final String repeatedPasswordError = "validation.account.repeatedpassword.error";
	private static final String nameError = "validation.account.name.error";
	public static final String accountError = "validation.account.emailAlreadyExisting.error";

	private static final String passwordRegEx = "([a-zA-Z0-9]).{7,20}";
	private static final String nameRegEx = "([a-zA-Z0-9]).{4,20}";

	private final Pattern passwordPattern = Pattern.compile(passwordRegEx);
	private final Pattern namePattern = Pattern.compile(nameRegEx);

	private EmailValidator emailValidator;

	@Override
	public boolean supports(Class<?> cls) {
		return AccountTO.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object object, Errors errors) {

		if(object instanceof AccountTO)
		{
			AccountTO accountTO = (AccountTO) object;
			if(!emailValidator.isValid(accountTO.getEmail())){
				errors.rejectValue(emailField, emailError);
			}

			if(!namePattern.matcher(accountTO.getName()).matches()){
				errors.rejectValue(nameField, nameError);
			}

			if(!passwordPattern.matcher(accountTO.getPassword()).matches()){
				errors.rejectValue(passwordField, passwordError);
			}

			if(!accountTO.getPassword().equals(accountTO.getRepeatedPassword())){
				errors.rejectValue(repeatedPasswordField, repeatedPasswordError);
			}
		}
		else
			throw new RuntimeException();

	}

	public void setEmailValidator(EmailValidator emailValidator) {
		this.emailValidator = emailValidator;
	}
}
