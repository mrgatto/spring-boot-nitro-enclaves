package com.github.mrgatto.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

public class EnclaveRequest<T> {

	@JsonProperty("id")
	private String id;

	@JsonProperty("action")
	private String action;

	@JsonProperty("data")
	@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@modelClass")
	private T data;

	@JsonProperty("aws-credentials")
	private AWSCredential awsCredentials;

	public EnclaveRequest() {
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public AWSCredential getAwsCredentials() {
		return awsCredentials;
	}

	public void setAwsCredentials(AWSCredential awsCredentials) {
		this.awsCredentials = awsCredentials;
	}

	@Override
	public String toString() {
		return "EnclaveRequest [id=" + id + ", action=" + action + "]";
	}

}
