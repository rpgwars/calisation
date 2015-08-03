package com.komar.service.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.comar.repository.AccountDAO;
import com.komar.domain.account.Account;
import com.komar.domain.account.exception.AccountAlreadyExists;
import com.komar.domain.account.exception.AccountNotFound;


@Service
public class AccountServiceImpl implements AccountService {

	@Autowired 
	private AccountDAO accountDao; 
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Account getAccountByLogin(String login) throws AccountNotFound {
		return accountDao.findAccount(login);
	}

	@Override
	public void saveAccount(Account account) throws AccountAlreadyExists {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		accountDao.saveAccount(account);
	}

}
