package com.komar.service.resource;

import com.komar.domain.cloudstorage.resource.transfer.ResourceType;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceTypeResolver {

    private final static Logger logger = Logger.getLogger(ResourceTypeResolver.class.getName());

    private Map<String, ResourceType> mimeTypeResourceTypeMap;
    private Map<String, String> mimeTypeExtensionMap;

    public Optional<ResourceType> resolve(String mimeType){
        if(mimeTypeResourceTypeMap.get(mimeType) == null)
            return Optional.empty();
        return Optional.of(mimeTypeResourceTypeMap.get(mimeType));
    }

    public String getExtension(String mimeType) {
        if(!mimeTypeExtensionMap.containsKey(mimeType)){
            logger.log(Level.SEVERE, "Mime type resolver confiugration error - no extension for give mime type");
            throw new RuntimeException("Wrong mime type / extension configuration");
        }
        return mimeTypeExtensionMap.get(mimeType);
    }

    public void setMimeTypeResourceTypeMap(Map<String, ResourceType> mimeTypeResourceTypeMap) {
        this.mimeTypeResourceTypeMap = mimeTypeResourceTypeMap;
    }

    public void setMimeTypeExtensionMap(Map<String, String> mimeTypeExtensionMap) {
        this.mimeTypeExtensionMap = mimeTypeExtensionMap;
    }
}
