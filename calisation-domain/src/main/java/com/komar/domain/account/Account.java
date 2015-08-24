package com.komar.domain.account;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.komar.domain.account.transfer.AccountRoleTO;
import com.komar.domain.account.transfer.AccountTO;
import com.komar.domain.cloudstorage.resource.Clip;
import com.komar.domain.cloudstorage.resource.Resource;

@Table
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
	
	public static final String emailColumn = "email";

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER, targetEntity = AccountRole.class)
	private List<AccountRole> accountRole = new ArrayList<>();
	
	public List<AccountRole> getAccountRole() {
		return accountRole;
	}
	
	public void addAccountRole(AccountRole accountRole){
		if(!this.accountRole.contains(accountRole)) {
			accountRole.setAccount(this);
			this.accountRole.add(accountRole);
		}
	}

	public void setAccountRole(List<AccountRole> accountRole) {
		for(AccountRole ar : accountRole)
			this.addAccountRole(ar);
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER, targetEntity = Resource.class)
	private List<Resource> resources = new ArrayList<>();

	public List<Resource> getResources() {
		return resources;
	}

	public void addResource(Resource resource){
		this.resources.add(resource);
	}

	public void setResource(List<Resource> resources) {
		this.resources = resources;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER, targetEntity = Clip.class)
	private List<Clip> clips = new ArrayList<>();

	public List<Clip> getClips() {
		return clips;
	}

	public void addClip(Clip clip){
		this.clips.add(clip);
	}

	public void setClips(List<Clip> clips) {
		this.clips = clips;
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
	
	@Column
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Column
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	} 
	
	public Account(){

	}
	
	public Account(AccountTO accountTO){
		this.name = accountTO.getName();
		this.email = accountTO.getEmail();
		this.password = accountTO.getPassword();
		
		accountTO.getAccountRoles()
			.forEach(accountRole -> addAccountRole(new AccountRole(accountRole)));
	}

	public AccountTO toTO(){
		AccountTO accountTO = new AccountTO();
		accountTO.setEmail(email);
		accountTO.setName(name);
		List<AccountRoleTO> accountRoleTOs = accountRole.stream()
				.map(accountRole -> accountRole.toTO())
				.collect(Collectors.toList());
		accountTO.setAccountRoles(accountRoleTOs);
		return accountTO;
	}
}
