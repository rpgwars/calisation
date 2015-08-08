package com.komar.service.application;

import com.komar.domain.account.Account;
import com.komar.domain.account.exception.AccountAlreadyExists;
import com.komar.domain.account.exception.AccountNotFound;
import com.komar.domain.account.transfer.AccountTO;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface AccountService extends UserDetailsService{
	
	AccountTO getAccountByLogin(String login) throws AccountNotFound;
	void saveAccount(AccountTO account) throws AccountAlreadyExists;

}