package com.komar.service.coloudstorage.amazons3.put;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;
import com.komar.domain.resource.transfer.UploadedFile;
import com.komar.service.coloudstorage.amazons3.AmazonS3KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
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
	public PutResultTO put(UploadedFile file) throws PutException{
        //AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider("KOMAR_S3"));
        try {
			String key = keyGenerator.getKey();
			putObject(file.getFile(), key);
			URL url = getObjectUrl(key);
            return new AmazonS3PutResultTO(bucketName, key, url.toString().split("\\?")[0], file.getFile().getContentType());
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

	private URL getObjectUrl(String key) {
		Date expirationDate = getExpirationDate();
		GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, key);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET);
		generatePresignedUrlRequest.setExpiration(expirationDate);
		return amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
	}

	private Date getExpirationDate() {
		Date expirationDate = new Date();
		long milliSeconds = expirationDate.getTime();
		// 2 years
		milliSeconds += 63072000000L;
		expirationDate.setTime(milliSeconds);
		return expirationDate;
	}

	private void putObject(MultipartFile file, String key) throws IOException {
		InputStream inputStream = file.getInputStream();
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(file.getSize());
		PutObjectResult putObjectResult = amazonS3Client.putObject(new PutObjectRequest(
				bucketName, key, inputStream, objectMetadata));
		inputStream.close();
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
