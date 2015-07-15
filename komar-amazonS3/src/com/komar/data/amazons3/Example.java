package com.komar.data.amazons3;


import java.io.IOException;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;
import com.amazonaws.services.s3.model.ObjectListing;

public class Example {


	private static String bucketName = "rpgwars";

	public static void main(String[] args) throws IOException {

		AWSSecurityTokenServiceClient stsClient = new AWSSecurityTokenServiceClient(new ProfileCredentialsProvider("KOMAR_S3"));
		//
		// Start a session.
		GetSessionTokenRequest getSessionTokenRequest = new GetSessionTokenRequest();

		GetSessionTokenResult sessionTokenResult = stsClient.getSessionToken(getSessionTokenRequest);
		Credentials sessionCredentials = sessionTokenResult.getCredentials();
		System.out.println("Session Credentials: " + sessionCredentials.toString());

		// Package the session credentials as a BasicSessionCredentials
		// object for an S3 client object to use.
		BasicSessionCredentials basicSessionCredentials = new BasicSessionCredentials(
				sessionCredentials.getAccessKeyId(), sessionCredentials.getSecretAccessKey(),
				sessionCredentials.getSessionToken());
		AmazonS3Client s3 = new AmazonS3Client(basicSessionCredentials);

		// Test. For example, get object keys for a given bucket.
		ObjectListing objects = s3.listObjects(bucketName);
		System.out.println("No. of Objects = " + objects.getObjectSummaries().size());
	}

}
