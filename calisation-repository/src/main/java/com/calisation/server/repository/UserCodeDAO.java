package com.calisation.server.repository;

import com.calisation.server.domain.UserCode;
import com.calisation.server.domain.transfer.request.UserCodeRequest;
import com.calisation.server.repository.exception.UserCodeNotFoundException;

public interface UserCodeDAO extends GenericDAO<UserCode> {

	UserCode getUserCode(String userCode, String challange) throws UserCodeNotFoundException;
	UserCode getUserCode(String userCode) throws UserCodeNotFoundException;
	void saveUserCode(UserCodeRequest userCodeRequest);
	
}
