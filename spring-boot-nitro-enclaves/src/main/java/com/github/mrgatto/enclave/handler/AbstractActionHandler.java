package com.github.mrgatto.enclave.handler;

import org.springframework.util.Assert;

import com.github.mrgatto.model.EnclaveRequest;

public abstract class AbstractActionHandler<IN, OUT> implements ActionHandler<IN, OUT> {

	@Override
	public boolean canHandle(EnclaveRequest<IN> request) {
		Assert.notNull(request, "request must not be null");
		return this.canHandle(request.getAction());
	}

	@Override
	public OUT execute(EnclaveRequest<IN> request) {
		Assert.notNull(request, "request must not be null");
		return this.execute(request.getData());
	}

	public abstract boolean canHandle(String action);

	public abstract OUT execute(IN request);

}
