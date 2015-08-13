package com.calisation.controller.validator;

import com.komar.domain.cloudstorage.resource.transfer.ResourceType;
import com.komar.domain.resource.transfer.UploadedFile;
import com.komar.service.resource.ResourceTypeResolver;
import org.apache.tika.Tika;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public class ResourceTOValidator implements Validator{

    public static final String fileField = "file";

    private static final String fileError = "validation.resource.error";

    private Tika tika;

    @Autowired
    private ResourceTypeResolver resourceTypeResolver;

    @Override
    public boolean supports(Class<?> cls) {
        return UploadedFile.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object object, Errors errors) {
        if(object instanceof UploadedFile){
            UploadedFile uploadedFile = (UploadedFile) object;
            MultipartFile multipartFile = uploadedFile.getFile();
            Metadata metadata = new Metadata();
            metadata.set(Metadata.RESOURCE_NAME_KEY, multipartFile.getOriginalFilename());
            metadata.set(Metadata.CONTENT_TYPE, multipartFile.getContentType());
            try {
                MediaType mimeType = tika.getDetector().detect(
                        TikaInputStream.get(TikaInputStream.get(multipartFile.getInputStream())), metadata);
                Optional<ResourceType> resourceType = resourceTypeResolver.resolve(mimeType.toString());
                if(!resourceType.isPresent())
                    errors.rejectValue(fileField, fileError);
                else
                    uploadedFile.setResourceType(resourceType.get());
            } catch (IOException e) {
                errors.rejectValue(fileField, fileError);
            }
        }
        else
            throw new RuntimeException();
    }

    public void setTika(Tika tika) {
        this.tika = tika;
    }

    public void setResourceTypeResolver(ResourceTypeResolver resourceTypeResolver) {
        this.resourceTypeResolver = resourceTypeResolver;
    }
}
