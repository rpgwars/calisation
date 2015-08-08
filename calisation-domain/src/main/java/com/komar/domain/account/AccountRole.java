package com.komar.domain.account;

import javax.persistence.*;

import com.komar.domain.account.transfer.AccountRoleTO;

@Table
@Entity
public class AccountRole {
	
	public AccountRole(){

	}
	
	@Id
	@GeneratedValue
	Integer roleId;
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	@Column
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	@ManyToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
	private Account account; 

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public AccountRole(AccountRoleTO accountRoleTO){
		this.role = accountRoleTO.getRole();
	}

	public AccountRoleTO toTO(){
		AccountRoleTO accountRoleTO = AccountRoleTO.valueOf(role);
		return accountRoleTO;
	}

}
