package com.komar.repository;

import com.komar.domain.account.Account;
import com.komar.domain.cloudstorage.StorageProvider;
import com.komar.domain.cloudstorage.resource.Clip;
import com.komar.domain.cloudstorage.resource.transfer.ResourceType;
import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ClipDAOImpl extends GenericDAOImpl<Clip> implements ClipDAO {

    @Override
    public Clip saveClip(PutResultTO putResultTO, Account account, String name, ResourceType resourceType, boolean withAudio) {
        Clip clip = new Clip();
        clip.setAccount(account);
        clip.setRetrievalData(putResultTO.getRetrievalData());
        clip.setRetrievalLink(putResultTO.getRetrievalLink());
        clip.setStorageProvider(StorageProvider.valueOf(putResultTO.getStorageProviderName()));
        clip.setName(name);
        clip.setDateTime(LocalDateTime.now());
        clip.setWithAudio(withAudio);
        clip.setResourceType(resourceType);
        account.addClip(clip);
        return clip;
    }

    @Override
    public List<Clip> getClips(String login) {
        CriteriaQuery<Clip> clipFromAccountCriteriaQuery =
                this.queryUtils.getJoinedCriteriaConditionOnJoined(entityManager, Clip.class, Clip.accountColumn, Account.emailColumn, login);
        TypedQuery<Clip> typedQuery = entityManager.createQuery(clipFromAccountCriteriaQuery);
        List<Clip> resultList = typedQuery.getResultList();
        return resultList;
    }

    @Override
    public void deleteClip(String login, String url) {
        CriteriaQuery<Clip> clipFromAccountCriteriaQuery =
                this.queryUtils.getJoinedCriteriaConditionOnJoiningAndJoined(entityManager, Clip.class, Clip.accountColumn,
                        Account.emailColumn, login, Clip.retrievalLinkColumn, url);
        TypedQuery<Clip> typedQuery = entityManager.createQuery(clipFromAccountCriteriaQuery);
        Clip clip = typedQuery.getSingleResult();
        if(clip != null) {
            clip.setAccount(null);
            delete(clip);
        }
    }
}
