package com.komar.service.application;

import com.komar.domain.account.Account;
import com.komar.domain.account.exception.AccountNotFound;
import com.komar.domain.account.exception.ResourceNotFound;
import com.komar.domain.cloudstorage.resource.Resource;
import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;
import com.komar.domain.resource.transfer.UploadedFile;
import com.komar.repository.AccountDAO;
import com.komar.repository.ResourceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ResourceDAO resourceDAO;

    private final static Logger logger = Logger.getLogger(UploadServiceImpl.class.getName());

    @Override
    @Transactional
    public void saveResource(PutResultTO putResultTO, String login, UploadedFile uploadedFile) throws AccountNotFound {
        Account account = accountDAO.findAccount(login);
        resourceDAO.saveResource(putResultTO, account, uploadedFile, uploadedFile.getResourceType());
    }

    @Override
    @Transactional
    public void linkResources(Integer referencingResourceId, Integer referencedResourceId, String login, String name) throws ResourceNotFound, AccountNotFound {
        Resource referencingResource = resourceDAO.findResource(referencingResourceId);
        Resource referencedResource = resourceDAO.findResource(referencedResourceId);
        Account account = accountDAO.findAccount(login);
        String email = account.getEmail();

        if(!referencingResource.getAccount().getEmail().equals(email) || !referencedResource.getAccount().getEmail().equals(email)){
            logger.log(Level.WARNING, "No such resource id associated with given account. Possible hack attempt");
            throw new ResourceNotFound();
        }

        resourceDAO.linkResources(referencingResource, referencedResource, name);
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void setResourceDAO(ResourceDAO resourceDAO) {
        this.resourceDAO = resourceDAO;
    }
}
