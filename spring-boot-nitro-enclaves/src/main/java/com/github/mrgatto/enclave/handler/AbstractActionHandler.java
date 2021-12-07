package com.github.mrgatto.enclave.handler;

import org.springframework.util.Assert;

import com.github.mrgatto.model.EnclaveRequest;

public abstract class AbstractActionHandler<IN, OUT> implements ActionHandler<IN, OUT> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canHandle(EnclaveRequest<IN> request) {
		Assert.notNull(request, "request must not be null");
		return this.canHandle(request.getAction());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OUT handle(EnclaveRequest<IN> request) {
		Assert.notNull(request, "request must not be null");
		return this.handle(request.getData());
	}

	/**
	 * Can this handler handle this action?
	 *
	 * @param action the request action
	 * @return true if yes, false if no
	 */
	public abstract boolean canHandle(String action);

	/**
	 * Handle the request data.
	 *
	 * @param data the request data
	 * @return output data
	 */
	public abstract OUT handle(IN data);

}
