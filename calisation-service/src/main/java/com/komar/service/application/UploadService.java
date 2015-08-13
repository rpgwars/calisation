package com.komar.service.application;

import com.komar.domain.account.exception.AccountNotFound;
import com.komar.domain.account.exception.ResourceNotFound;
import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;
import com.komar.domain.resource.transfer.UploadedFile;

public interface UploadService {

    void saveResource(PutResultTO putResultTO, String login, UploadedFile uploadedFile) throws AccountNotFound;
    void linkResources(Integer referencingResourceId, Integer referencedResourceId, String login, String name) throws ResourceNotFound, AccountNotFound;
}
