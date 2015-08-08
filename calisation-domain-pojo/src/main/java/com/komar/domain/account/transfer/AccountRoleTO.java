package com.komar.domain.account.transfer;

public enum AccountRoleTO {

	ROLE_USER("ROLE_USER");

	AccountRoleTO(String role) {
		this.role = role;
	}

	private String role;

	public String getRole() {
		return role;
	}
}
