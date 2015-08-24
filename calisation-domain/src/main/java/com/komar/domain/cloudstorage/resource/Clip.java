package com.komar.domain.cloudstorage.resource;

import com.komar.domain.account.Account;
import com.komar.domain.cloudstorage.StorageProvider;
import com.komar.domain.cloudstorage.resource.transfer.ResourceType;
import com.komar.domain.resource.transfer.ClipTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity
public class Clip {

    public static final String accountColumn = "account";
    public static final String retrievalLinkColumn = "retrievalLink";

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Id
    @GeneratedValue
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    private String retrievalData;

    public String getRetrievalData() {
        return retrievalData;
    }

    public void setRetrievalData(String retrievalData) {
        this.retrievalData = retrievalData;
    }

    @Column
    private String retrievalLink;

    public String getRetrievalLink() {
        return retrievalLink;
    }

    public void setRetrievalLink(String retrievalLink) {
        this.retrievalLink = retrievalLink;
    }

    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    private LocalDateTime dateTime;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Enumerated
    private StorageProvider storageProvider;

    public StorageProvider getStorageProvider() {
        return storageProvider;
    }

    public void setStorageProvider(StorageProvider storageProvider) {
        this.storageProvider = storageProvider;
    }

    @Column
    public boolean withAudio;

    public boolean isWithAudio() {
        return withAudio;
    }

    public void setWithAudio(boolean withAudio) {
        this.withAudio = withAudio;
    }

    @Enumerated
    private ResourceType resourceType;

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ClipTO toTO(){
        return new ClipTO(name, retrievalLink, withAudio, resourceType);
    }
}
