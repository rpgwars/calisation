package com.komar.repository;

import com.komar.domain.account.Account;
import com.komar.domain.account.exception.ResourceNotFound;
import com.komar.domain.cloudstorage.resource.Resource;
import com.komar.domain.cloudstorage.resource.transfer.ResourceType;
import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;
import com.komar.domain.resource.transfer.UploadedFile;

import java.util.List;


public interface ResourceDAO {

    void saveResource(PutResultTO putResultTO, Account account, UploadedFile uploadedFile, ResourceType resourceType);
    void linkResources(Resource referencingResource, Resource referencedResource, String name);
    List<Resource> getResources(String login);
    Resource findResource(Integer id) throws ResourceNotFound;

}
