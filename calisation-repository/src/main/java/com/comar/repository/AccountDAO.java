package com.comar.repository;

import com.komar.domain.account.Account;
import com.komar.domain.account.exception.AccountAlreadyExists;
import com.komar.domain.account.exception.AccountNotFound;

public interface AccountDAO extends GenericDAO<Account>{

	Account findAccount(String login) throws AccountNotFound;
	void saveAccount(Account account) throws AccountAlreadyExists; 
}
