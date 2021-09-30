package com.github.mrgatto.enclave.kms;

import com.github.mrgatto.model.AWSCredential;

public interface KMSDecryptor {

	String decrypt(AWSCredential credential, String content);

}
