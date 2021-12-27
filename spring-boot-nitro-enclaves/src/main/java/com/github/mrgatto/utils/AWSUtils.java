package com.github.mrgatto.utils;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mrgatto.model.AWSCredential;

import software.amazon.awssdk.regions.internal.util.EC2MetadataUtils;
import software.amazon.awssdk.regions.internal.util.EC2MetadataUtils.IamSecurityCredential;

public final class AWSUtils {

	private static final Logger LOG = LoggerFactory.getLogger(AWSUtils.class);

	private AWSUtils() {

	}

	public static AWSCredential getAWSSessionToken() {
		Map<String, IamSecurityCredential> securityCredentialsMap = EC2MetadataUtils.getIamSecurityCredentials();

		if (securityCredentialsMap.isEmpty()) {
			return null;
		}

		Entry<String, IamSecurityCredential> entry = securityCredentialsMap.entrySet().iterator().next();
		LOG.info("Reading AWS Credentials from instance profile {}", entry.getKey());

		IamSecurityCredential securityCredentials = entry.getValue();

		AWSCredential credential = new AWSCredential();
		credential.setAccessKeyId(securityCredentials.accessKeyId);
		credential.setSecretAccessKey(securityCredentials.secretAccessKey);
		credential.setSessionToken(securityCredentials.token);
		credential.setRegion(getRegion());

		return credential;
	}

	public static String getRegion() {
		return EC2MetadataUtils.getInstanceInfo().getRegion();
	}

}
