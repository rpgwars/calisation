package com.calisation.server.service;

import com.calisation.server.domain.transfer.request.UserCodeRequest;
import com.calisation.server.repository.exception.UserCodeNotFoundException;
import com.calisation.server.service.exception.UserCodeExistsException;

public interface UserCodeService {

	void requestUserCode(UserCodeRequest userCodeRequest) throws UserCodeExistsException;
	boolean releaseUserCode(UserCodeRequest userCodeRequest);
}
