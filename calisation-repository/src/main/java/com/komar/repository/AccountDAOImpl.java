package com.komar.repository;

import com.komar.domain.account.transfer.AccountTO;
import com.komar.domain.cloudstorage.resource.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.komar.domain.account.Account;
import com.komar.domain.account.exception.AccountAlreadyExists;
import com.komar.domain.account.exception.AccountNotFound;
import com.komar.domain.account.exception.NotFound;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

@Repository
public class AccountDAOImpl extends GenericDAOImpl<Account> implements AccountDAO {

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
    public void saveAccount(AccountTO account) throws AccountAlreadyExists {
        try {
            findAccount(account.getEmail());
            throw new AccountAlreadyExists();
        } catch (AccountNotFound e) {
            super.save(new Account(account));
            return;
        }
    }
}
