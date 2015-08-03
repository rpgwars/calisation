package com.komar.domain.account;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.InheritanceType;

import com.komar.domain.account.transfer.AccountRoleTO;
import com.komar.domain.account.transfer.AccountTO;

@Table
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
	
	public static final String emailColumn = "email";

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER, targetEntity = AccountRole.class)
	private List<AccountRole> accountRole = new ArrayList<AccountRole>();
	
	public List<AccountRole> getAccountRole() {
		return accountRole;
	}
	
	public void addAccountRole(AccountRole accountRole){
		if(!this.accountRole.contains(accountRole))
			accountRole.setAccount(this);
			this.accountRole.add(accountRole); 
	}

	public void setAccountRole(List<AccountRole> accountRole) {
		for(AccountRole ar : accountRole)
			this.addAccountRole(ar);
	}
	
	@Id
	@GeneratedValue
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Basic
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Basic
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Basic
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	} 
	
	public static Account map(AccountTO accountTO){
		Account account = new Account(accountTO);
		account.getAccountRole()
			.forEach(accountRole -> accountRole.setAccount(account));
		return account;
	}
	
	public Account(AccountTO accountTO){
		this.name = accountTO.getName();
		this.email = accountTO.getEmail();
		this.password = accountTO.getPassword();
		for(AccountRoleTO accountRoleTO : accountTO.getAccountRoles()){
			addAccountRole(new AccountRole(accountRoleTO.getRole()));
		}
		
		accountTO.getAccountRoles()
			.forEach(accountRole -> addAccountRole(new AccountRole(accountRole.getRole())));
	}
}
