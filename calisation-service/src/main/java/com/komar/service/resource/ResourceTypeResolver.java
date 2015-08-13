package com.komar.service.resource;

import com.komar.domain.cloudstorage.resource.transfer.ResourceType;

import java.util.Map;
import java.util.Optional;

public class ResourceTypeResolver {

    private Map<String, ResourceType> mimeTypeResourceTypeMap;

    public Optional<ResourceType> resolve(String mimeType){
        if(mimeTypeResourceTypeMap.get(mimeType) == null)
            return Optional.empty();
        return Optional.of(mimeTypeResourceTypeMap.get(mimeType));
    }

    public void setMimeTypeResourceTypeMap(Map<String, ResourceType> mimeTypeResourceTypeMap) {
        this.mimeTypeResourceTypeMap = mimeTypeResourceTypeMap;
    }
}
