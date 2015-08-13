package com.komar.domain.cloudstorage.resource;

import com.komar.domain.account.Account;
import com.komar.domain.cloudstorage.StorageProvider;
import com.komar.domain.cloudstorage.resource.transfer.ResourceType;
import com.komar.domain.resource.transfer.ResourceTO;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table
@Entity
public class Resource {

    public static final String idColumn = "id";
    public static final String accountColumn = "account";

    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "referencingResource", fetch = FetchType.EAGER, targetEntity = ResourceLink.class)
    private List<ResourceLink> referencingResources = new ArrayList<>();*/

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referencedResource", fetch = FetchType.EAGER, targetEntity = ResourceLink.class)
    private List<ResourceLink> referencedResources = new ArrayList<>();

    /*
    public List<ResourceLink> getReferencingResources() {
        return referencingResources;
    }

    public void linkReferencingResource(ResourceLink resourceLink){
        referencingResources.add(resourceLink);
    }

    public void setReferencingResources(List<ResourceLink> referencingResources) {
        this.referencingResources = referencingResources;
    }
    */
    public List<ResourceLink> getReferencedResources() {
        return referencedResources;
    }

    public void linkReferencedResource(ResourceLink resourceLink){
        this.referencedResources.add(resourceLink);
    }

    public void setReferencedResources(List<ResourceLink> referencedResources) {
        this.referencedResources = referencedResources;
    }

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

    public static Resource getResource(ResourceType resourceType){
        switch (resourceType){
            case AUDIO:
                return new AudioResource();
            case VIDEO:
                return new VideoResource();
            default:
                throw new RuntimeException("");
        }
    }

    public ResourceTO toTO(){
        ResourceTO resourceTO = new ResourceTO();
    }
}
