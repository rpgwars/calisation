package com.komar.service.coloudstorage.amazons3.put;

import com.komar.domain.cloudstorage.resource.transfer.put.PutResultTO;

import java.util.Arrays;
import java.util.List;



public class AmazonS3PutResultTO extends PutResultTO {

	private static final String delimiter = "/";
	private static final String AMAZON_S3 = "AMAZON_S3";
	
	public AmazonS3PutResultTO(String bucketName, String key, String retrievalLink, String mimeType)
	{
		super(new StringBuilder(bucketName).append(delimiter).append(key).toString(), retrievalLink, mimeType);
	}
	
	@Override
	public List<String> getSplittedRetrievalData(String retrievalData) {
		return Arrays.asList(retrievalData.split(delimiter));
	}

	@Override
	public String getStorageProviderName() {
		return AMAZON_S3;
	}

}
