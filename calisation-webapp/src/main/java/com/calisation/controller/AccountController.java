package com.calisation.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.komar.domain.account.Account;


@Controller
public class AccountController {

	@RequestMapping(value = "/administration/addDoctor", method=RequestMethod.POST)
	public ModelAndView addAccount(@Valid Account account, BindingResult result){
		
		ModelAndView mav = new ModelAndView("addDoctor");
		if(!doctor.getPassword().equals(doctor.getRepeatedPassword()))
			result.addError(new FieldError("doctor","repeatedPassword","passwords are diffrent"));
		
		if(result.hasErrors()){
			return prepareMaV(doctor);
		}
		else{

			try{
				doctor.addAccountRole(new AccountRole("ROLE_DOCTOR"));
				accountService.saveAccount(doctor);
			}
			catch(AccountAlreadyExists exception){
				result.addError(new FieldError("doctor","email","email already registered"));
				return prepareMaV(doctor);
			} catch (GroupDoesNotExist e) {
				result.addError(new FieldError("doctor","groupId","this group does not exist"));
				return prepareMaV(doctor);
			}

			
		}
					
		mav.setViewName("redirect:/administration/addDoctor.html");
		return mav; 
		 
	}
	
}
