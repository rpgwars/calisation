package com.komar.service.coloudstorage.amazons3.delete;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.komar.service.cloudstorage.delete.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AmazonS3DeleteService implements DeleteService{

    private final static Logger logger = Logger.getLogger(AmazonS3DeleteService.class.getName());

    private String bucketName;
    @Autowired
    private AmazonS3 amazonS3Client;

    @Override
    public void delete(String key, String bucketName) {
        try{
            if(bucketName == null)
                amazonS3Client.deleteObject(new DeleteObjectRequest(this.bucketName, key));
            else
                amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, key));
        } catch (AmazonServiceException ase) {
            String message = String.format("AmazonServiceException %s %s %s %s %s",
                    ase.getMessage(), ase.getStatusCode(), ase.getErrorCode(), ase.getErrorType(), ase.getRequestId());
            logger.log(Level.WARNING, message);
        } catch (AmazonClientException ace) {
            String message = String.format("AmazonClientException %s ", ace.getMessage());
            logger.log(Level.WARNING, message);
        }
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public void setAmazonS3Client(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }
}
