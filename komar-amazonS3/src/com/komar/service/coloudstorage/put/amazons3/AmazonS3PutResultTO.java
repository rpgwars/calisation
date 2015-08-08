package com.komar.service.coloudstorage.put.amazons3;

import java.util.Arrays;
import java.util.List;

import com.komar.domain.transfer.cloudstorage.put.PutResultTO;

public class AmazonS3PutResultTO extends PutResultTO {

	private static final String delimiter = "/";
	
	public AmazonS3PutResultTO(String bucketName, String key)
	{
		super(new StringBuilder(bucketName).append(delimiter).append(key).toString());
	}
	
	@Override
	protected List<String> getSplittedRetrievalData(String retrievalData) {
		return Arrays.asList(retrievalData.split(delimiter));
	}

}
