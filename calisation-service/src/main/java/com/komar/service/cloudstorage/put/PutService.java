package com.komar.service.cloudstorage.put;

import com.komar.domain.transfer.cloudstorage.put.PutResult;
import org.springframework.web.multipart.MultipartFile;

public interface PutService {
	
	PutResult put(MultipartFile file) throws PutException;

}
