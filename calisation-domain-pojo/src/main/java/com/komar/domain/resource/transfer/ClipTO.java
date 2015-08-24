package com.komar.domain.resource.transfer;

import com.komar.domain.cloudstorage.resource.transfer.ResourceType;

public class ClipTO {

    private String name;
    private String url;
    private boolean withAudio;
    private ResourceType resourceType;

    public ClipTO(String name, String url, boolean withAudio, ResourceType resourceType) {
        this.name = name;
        this.url = url;
        this.withAudio = withAudio;
        this.resourceType = resourceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isWithAudio() {
        return withAudio;
    }

    public void setWithAudio(boolean withAudio) {
        this.withAudio = withAudio;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }
}
