package com.github.mrgatto.enclave.handler;

import com.github.mrgatto.model.EnclaveRequest;

public interface ActionHandler<IN, OUT> {

	/**
	 * Can this handler handle the given request?
	 *
	 * @param request the incoming request
	 * @return true if yes, false if no
	 */
	boolean canHandle(EnclaveRequest<IN> request);

	/**
	 * Handle the request.
	 *
	 * @param request the incoming request
	 * @return output data
	 */
	OUT handle(EnclaveRequest<IN> request);

}
