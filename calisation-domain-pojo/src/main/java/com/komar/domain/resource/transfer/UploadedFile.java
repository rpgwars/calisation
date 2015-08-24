package com.komar.domain.resource.transfer;

import com.komar.domain.cloudstorage.resource.transfer.ResourceType;
import org.springframework.web.multipart.MultipartFile;

public class UploadedFile {

    private String name;
    private MultipartFile file;
    private ResourceType resourceType;
    private String extension;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
