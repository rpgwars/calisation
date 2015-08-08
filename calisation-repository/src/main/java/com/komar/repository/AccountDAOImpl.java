package com.komar.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.komar.domain.account.Account;
import com.komar.domain.account.exception.AccountAlreadyExists;
import com.komar.domain.account.exception.AccountNotFound;
import com.komar.domain.account.exception.NotFound;

@Repository
public class AccountDAOImpl extends GenericDAOImpl<Account> implements AccountDAO{
	
	@Override
	@Transactional(readOnly = true)
	public Account findAccount(String login) throws AccountNotFound {

		try {
			return this.isExisting(queryUtils.getSimpleCriteria(entityManager, Account.class, Account.emailColumn, login));
		} catch (NotFound e) {
			throw new AccountNotFound();
		}
	}

	@Override
	@Transactional
	public void saveAccount(Account account) throws AccountAlreadyExists{

		try {
			findAccount(account.getEmail());
			throw new AccountAlreadyExists();
		} catch (AccountNotFound e) {	
			super.save(account);
			return; 
		}
	}
}
