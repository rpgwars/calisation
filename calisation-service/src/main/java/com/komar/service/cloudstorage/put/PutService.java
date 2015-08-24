package com.komar.service.cloudstorage.put;

import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;
import com.komar.domain.resource.transfer.UploadedFile;

public interface PutService {
	
	PutResultTO put(UploadedFile file) throws PutException;

}
