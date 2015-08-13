package com.komar.domain.cloudstorage.resource;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity
public class ResourceLink {

    @ManyToOne(targetEntity = Resource.class, fetch = FetchType.EAGER)
    private Resource referencingResource;

    @ManyToOne(targetEntity = Resource.class, fetch = FetchType.EAGER)
    private Resource referencedResource;

    public Resource getReferencingResource() {
        return referencingResource;
    }

    public void setReferencingResource(Resource referencingResource) {
        this.referencingResource = referencingResource;
    }

    public Resource getReferencedResource() {
        return referencedResource;
    }

    public void setReferencedResource(Resource referencedResource) {
        this.referencedResource = referencedResource;
    }

    public ResourceLink(){

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
}
