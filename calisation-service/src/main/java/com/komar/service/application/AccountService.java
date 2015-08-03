package com.komar.service.application;

import com.komar.domain.account.Account;
import com.komar.domain.account.exception.AccountAlreadyExists;
import com.komar.domain.account.exception.AccountNotFound;


public interface AccountService {
	
	Account getAccountByLogin(String login) throws AccountNotFound;
	void saveAccount(Account account) throws AccountAlreadyExists;

}