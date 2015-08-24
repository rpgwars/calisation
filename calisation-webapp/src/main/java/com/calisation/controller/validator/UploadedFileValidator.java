package com.calisation.controller.validator;

import com.komar.domain.cloudstorage.resource.transfer.ResourceType;
import com.komar.domain.resource.transfer.Edit;
import com.komar.domain.resource.transfer.UploadedFile;
import com.komar.service.resource.ResourceTypeResolver;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public class UploadedFileValidator implements Validator{

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
            try {
                MimeType mimeType = getMimeType(multipartFile);
                Optional<ResourceType> resourceType = resourceTypeResolver.resolve(mimeType.toString());
                if(!resourceType.isPresent()) {
                    errors.rejectValue(fileField, fileError);
                }
                else{
                    setFileTypeData(uploadedFile, mimeType, resourceType);
                }

            } catch (IOException e) {
                errors.rejectValue(fileField, fileError);
            } catch (MimeTypeException e) {
                errors.rejectValue(fileField, fileError);
            }
        }
        else
            throw new RuntimeException();
    }

    public boolean validateFileName(String name){
        return name.matches("[a-zA-Z0-9]+");
    }

    private MimeType getMimeType(MultipartFile multipartFile) throws IOException, MimeTypeException {
        Metadata metadata = getTikaMetadata(multipartFile);
        MediaType mediaType = tika.getDetector().detect(
                TikaInputStream.get(TikaInputStream.get(multipartFile.getInputStream())), metadata);
        return TikaConfig.getDefaultConfig().getMimeRepository().forName(mediaType.toString());
    }

    public Optional<UploadedFile> validateClip(Edit edit, MultipartFile multipartFile){
        if(edit.getStart() != null && edit.getEnd() != null) {
            if (edit.getStart() < 0)
                return Optional.empty();
            if (edit.getStart() >= edit.getEnd())
                return Optional.empty();
        }

        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setFile(multipartFile);
        try {
            MimeType mimeType = getMimeType(multipartFile);
            Optional<ResourceType> resourceType = resourceTypeResolver.resolve(mimeType.toString());
            if(!resourceType.isPresent()) {
                return Optional.empty();
            }
            else{
                setFileTypeData(uploadedFile, mimeType, resourceType);
            }
        } catch (IOException e) {
            return Optional.empty();
        } catch (MimeTypeException e) {
            return Optional.empty();
        }
        return Optional.of(uploadedFile);
    }

    private void setFileTypeData(UploadedFile uploadedFile, MimeType mimeType, Optional<ResourceType> resourceType) {
        uploadedFile.setResourceType(resourceType.get());
        //Tika returns strange MAC extensions (mpga istead of mp3)
        //extensions that work with moviepy are in configuration
        uploadedFile.setExtension(resourceTypeResolver.getExtension(mimeType.toString()));
    }

    private Metadata getTikaMetadata(MultipartFile multipartFile) {
        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY, multipartFile.getOriginalFilename());
        metadata.set(Metadata.CONTENT_TYPE, multipartFile.getContentType());
        return metadata;
    }

    public void setTika(Tika tika) {
        this.tika = tika;
    }

    public void setResourceTypeResolver(ResourceTypeResolver resourceTypeResolver) {
        this.resourceTypeResolver = resourceTypeResolver;
    }
}
