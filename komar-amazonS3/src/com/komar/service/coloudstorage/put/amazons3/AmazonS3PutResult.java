package com.komar.service.coloudstorage.put.amazons3;

import java.util.Arrays;
import java.util.List;

import com.komar.domain.transfer.cloudstorage.put.PutResult;

public class AmazonS3PutResult extends PutResult{

	private static final String delimiter = "/";
	
	public AmazonS3PutResult(String bucketName, String key) 
	{
		super(new StringBuilder(bucketName).append(delimiter).append(key).toString());
	}
	
	@Override
	protected List<String> getSplittedRetrievalData(String retrievalData) {
		return Arrays.asList(retrievalData.split(delimiter));
	}

}
