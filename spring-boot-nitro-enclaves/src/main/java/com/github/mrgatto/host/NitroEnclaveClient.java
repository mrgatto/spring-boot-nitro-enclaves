package com.github.mrgatto.host;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mrgatto.host.network.HostClient;
import com.github.mrgatto.model.EnclaveRequest;
import com.github.mrgatto.model.EnclaveResponse;
import com.github.mrgatto.utils.JsonMapper;

public class NitroEnclaveClient {

	private static final Logger LOG = LoggerFactory.getLogger(NitroEnclaveClient.class);

	private final JsonMapper jsonMapper;

	private final HostClient hostClient;

	public NitroEnclaveClient(JsonMapper jsonMapper, HostClient hostClient) {
		this.jsonMapper = jsonMapper;
		this.hostClient = hostClient;
	}

	public <IN, OUT> EnclaveResponse<OUT> send(EnclaveRequest<IN> request) {
		Assert.notNull(this.hostClient, "hostClient must not be null");
		Assert.notNull(request, "input must not be null");

		LOG.info("Sending '{}' to enclave...", request);
		byte[] requestBytes = this.jsonMapper.writeValueAsBytes(request);
		byte[] responseBytes =  this.hostClient.send(requestBytes);

		EnclaveResponse<OUT> response = this.jsonMapper.readValue(responseBytes, new TypeReference<EnclaveResponse<OUT>>(){});
		LOG.info("Response for '{}' received", response);

		return response;
	}

}
