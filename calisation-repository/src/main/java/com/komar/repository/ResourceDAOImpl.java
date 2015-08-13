package com.komar.repository;

import com.komar.domain.account.Account;
import com.komar.domain.account.exception.NotFound;
import com.komar.domain.account.exception.ResourceNotFound;
import com.komar.domain.cloudstorage.StorageProvider;
import com.komar.domain.cloudstorage.resource.Resource;
import com.komar.domain.cloudstorage.resource.ResourceLink;
import com.komar.domain.cloudstorage.resource.transfer.ResourceType;
import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;
import com.komar.domain.resource.transfer.UploadedFile;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ResourceDAOImpl extends GenericDAOImpl<Resource> implements ResourceDAO {

    @Override
    public void saveResource(PutResultTO putResultTO, Account account, UploadedFile uploadedFile, ResourceType resourceType) {
        Resource resource = Resource.getResource(resourceType);

        resource.setAccount(account);
        resource.setRetrievalData(putResultTO.getRetrievalData());
        resource.setRetrievalLink(putResultTO.getRetrievalLink());
        resource.setStorageProvider(StorageProvider.valueOf(putResultTO.getStorageProviderName()));
        resource.setName(uploadedFile.getName());
        resource.setDateTime(LocalDateTime.now());

        account.addResource(resource);
    }

    @Override
    public void linkResources(Resource referencingResource, Resource referencedResource, String name) {
        LocalDateTime dateTime = LocalDateTime.now();
        ResourceLink resourceLink = new ResourceLink();
        link(resourceLink, referencingResource, referencedResource, name, dateTime);
        resourceLink = new ResourceLink();
        link(resourceLink, referencedResource, referencingResource, name, dateTime);
    }

    @Override
    public List<Resource> getResources(String login) {
        CriteriaQuery<Resource> resourceFromAccountCriteriaQuery =
                this.queryUtils.getJoinedCriteriaConditionOnJoined(entityManager, Resource.class, Resource.accountColumn, Account.emailColumn, login);
        TypedQuery<Resource> typedQuery = entityManager.createQuery(resourceFromAccountCriteriaQuery);
        List<Resource> resultList = typedQuery.getResultList();
        return resultList;
    }

    private void link(ResourceLink resourceLink, Resource referencingResource, Resource referencedResource,
                      String name, LocalDateTime dateTime){
        resourceLink.setReferencingResource(referencingResource);
        resourceLink.setReferencedResource(referencedResource);
        referencingResource.linkReferencedResource(resourceLink);
        resourceLink.setName(name);
        resourceLink.setDateTime(dateTime);
    }

    @Override
    public Resource findResource(Integer id) throws ResourceNotFound{
        try {
            return this.isExisting(queryUtils.getSimpleCriteria(entityManager, Resource.class, Resource.idColumn, id));
        } catch (NotFound notFound) {
            throw new ResourceNotFound();
        }
    }
}
