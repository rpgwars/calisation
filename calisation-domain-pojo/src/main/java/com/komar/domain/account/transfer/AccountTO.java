package com.komar.domain.account.transfer;

import java.util.List;

public class AccountTO {
	
	private String name; 
	private String email; 
	private String password;
	private String repeatedPassword;
	private List<AccountRoleTO> accountRoles;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepeatedPassword() {
		return repeatedPassword;
	}
	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
	public List<AccountRoleTO> getAccountRoles() {
		return accountRoles;
	}
	public void setAccountRoles(List<AccountRoleTO> accountRoles) {
		this.accountRoles = accountRoles;
	}

}
