package com.komar.service.cloudstorage.put;


import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;
import org.springframework.web.multipart.MultipartFile;

public interface PutService {
	
	PutResultTO put(MultipartFile file) throws PutException;

}
