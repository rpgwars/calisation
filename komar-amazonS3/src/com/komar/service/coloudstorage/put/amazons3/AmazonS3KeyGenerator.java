package com.komar.service.coloudstorage.put.amazons3;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class AmazonS3KeyGenerator {
	
	private final SecureRandom random = new SecureRandom();

	public String getKey()
	{
		return new BigInteger(130, random).toString(32);
	}

}
