package com.komar.repository;

import com.komar.domain.account.Account;
import com.komar.domain.account.exception.AccountAlreadyExists;
import com.komar.domain.account.exception.AccountNotFound;
import com.komar.domain.account.transfer.AccountTO;

public interface AccountDAO extends GenericDAO<Account>{

	Account findAccount(String login) throws AccountNotFound;
	void saveAccount(AccountTO account) throws AccountAlreadyExists;
}
