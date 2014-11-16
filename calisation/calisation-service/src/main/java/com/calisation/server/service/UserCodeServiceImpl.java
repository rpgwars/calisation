package com.calisation.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.calisation.server.domain.UserCode;
import com.calisation.server.domain.transfer.request.UserCodeRequest;
import com.calisation.server.repository.UserCodeDAO;
import com.calisation.server.repository.exception.UserCodeNotFoundException;
import com.calisation.server.service.exception.UserCodeExistsException;


@Service
public class UserCodeServiceImpl implements UserCodeService{

	@Autowired
	private UserCodeDAO userCodeDAO; 
	
	@Override
	@Transactional
	public void requestUserCode(UserCodeRequest userCodeRequest)
			throws UserCodeExistsException {
		
		try {
			userCodeDAO.getUserCode(userCodeRequest.getUserCode());
		} catch (UserCodeNotFoundException e) {
			userCodeDAO.saveUserCode(userCodeRequest);
			return; 
		}
		
		throw new UserCodeExistsException();
		
	}

	@Override
	@Transactional
	public boolean releaseUserCode(UserCodeRequest userCodeRequest){

		try {
			UserCode userCode = userCodeDAO.getUserCode(userCodeRequest.getUserCode());
			if(userCode.getChallange().equals(userCodeRequest.getChallange())){
				userCodeDAO.delete(userCode);
				return true; 
			}
			return false; 
		} catch (UserCodeNotFoundException e) {
			return true; 
		}
		
		
	}

}
