package com.komar.service.coloudstorage.put.amazons3;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.komar.domain.transfer.cloudstorage.put.PutResult;
import com.komar.service.cloudstorage.put.PutException;
import com.komar.service.cloudstorage.put.PutService;

@Service
public class AmazonS3PutService implements PutService{

	private String bucketName;
	@Autowired
	private AmazonS3KeyGenerator keyGenerator;
	@Autowired
	private AmazonS3 amazonS3Client;
	
	private final static Logger logger = Logger.getLogger(AmazonS3PutService.class.getName());
	
	@Override
	public PutResult put(MultipartFile file) throws PutException{
        //AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider("KOMAR_S3"));
        try {
            InputStream inputStream = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            String key = keyGenerator.getKey();
            PutObjectResult putObjectResult = amazonS3Client.putObject(new PutObjectRequest(
            		                 bucketName, key, inputStream, objectMetadata));
            inputStream.close();
            return new AmazonS3PutResult(bucketName, key);
         } catch (AmazonServiceException ase) {
             
            String message = String.format("AmazonServiceException %s %s %s %s %s",
            		ase.getMessage(), ase.getStatusCode(), ase.getErrorCode(), ase.getErrorType(), ase.getRequestId());
            logger.log(Level.WARNING, message);
            throw new PutException();
        } catch (AmazonClientException ace) {
            String message = String.format("AmazonClientException %s ", ace.getMessage());
            logger.log(Level.WARNING, message);
            throw new PutException();
        } catch (IOException e) {
            String message = String.format("IOException %s ", e.getMessage(), e);
            logger.log(Level.WARNING, message);
            throw new PutException();
		} 
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public void setKeyGenerator(AmazonS3KeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	public void setAmazonS3Client(AmazonS3 amazonS3Client) {
		this.amazonS3Client = amazonS3Client;
	}

}
