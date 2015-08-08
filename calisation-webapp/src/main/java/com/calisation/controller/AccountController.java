package com.calisation.controller;

import javax.validation.Valid;

import com.calisation.controller.validator.AccountValidator;
import com.calisation.server.domain.transfer.request.PositionNotificationRequest;
import com.calisation.server.repository.exception.UserCodeNotFoundException;
import com.calisation.server.service.PositionNotificationService;
import com.komar.domain.account.exception.AccountAlreadyExists;
import com.komar.domain.account.transfer.AccountTO;
import com.komar.service.application.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.komar.domain.account.Account;

import java.util.Map;


@Controller
public class AccountController {

	private static final String addAccountView = "addAccount";
	private static final String accountModel = "account";


	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountValidator accountValidator;

	@RequestMapping(value = "/sandbox/addAccount", method=RequestMethod.GET)
	public String addAccount(Map<String,Object> map) {
		map.put(accountModel, new AccountTO());
		return addAccountView;
	}

	@RequestMapping(value = "/sandbox/addAccount", method=RequestMethod.POST)
	public ModelAndView addAccount(@ModelAttribute(accountModel) @Valid AccountTO accountTO, BindingResult bindingResult){

		ModelAndView mav = new ModelAndView();

		if(bindingResult.hasErrors()){
			mav.setViewName(addAccountView);
			mav.addObject(accountModel, accountTO);
			return mav;
		}

		try{
			accountService.saveAccount(accountTO);
		}
		catch(AccountAlreadyExists exception) {
			bindingResult.addError(new FieldError("acccount", AccountValidator.emailField, AccountValidator.accountError));
			mav.addObject(accountModel, accountTO);
			return mav;
		}

		mav.setViewName("redirect:/sandbox/successfulAccount.html");
		return mav;
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}


	@RequestMapping(value = "/logout/success")
	public String logoutSuccess() {
		return "redirect:/login.html";
	}

	public void setAccountValidator(AccountValidator accountValidator) {
		this.accountValidator = accountValidator;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@InitBinder("account")
	protected void initAccountBinder(WebDataBinder binder){
		binder.setValidator(accountValidator);
	}
}
