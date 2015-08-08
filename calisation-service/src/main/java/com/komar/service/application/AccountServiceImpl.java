package com.komar.service.application;


import com.komar.domain.account.AccountRole;
import com.komar.domain.account.transfer.AccountRoleTO;
import com.komar.repository.AccountDAO;
import com.komar.domain.account.Account;
import com.komar.domain.account.exception.AccountAlreadyExists;
import com.komar.domain.account.exception.AccountNotFound;
import com.komar.domain.account.transfer.AccountTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {

	@Autowired 
	private AccountDAO accountDao; 
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public AccountTO getAccountByLogin(String login) throws AccountNotFound {
		return accountDao.findAccount(login).toTO();
	}

	@Override
	public void saveAccount(AccountTO account) throws AccountAlreadyExists {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		account.setAccountRoles(Arrays.asList(AccountRoleTO.ROLE_USER));
		accountDao.saveAccount(new Account(account));
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account;
		try {
			account = accountDao.findAccount(email);
		} catch (AccountNotFound e) {
			throw new UsernameNotFoundException(email);
		}

		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean nonLocked = true;
		return new User(
				account.getEmail(),
				account.getPassword(),
				enabled,
				accountNonExpired,
				credentialsNonExpired,
				nonLocked,
				getGrantedAuthorities(account.getAccountRole()));

	}

	private List<GrantedAuthority> getGrantedAuthorities(List<AccountRole> accountRoles){
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(accountRoles.size());
		accountRoles.forEach(accountRole -> grantedAuthorities.add(new SimpleGrantedAuthority(accountRole.getRole())));
		return grantedAuthorities;
	}
}
