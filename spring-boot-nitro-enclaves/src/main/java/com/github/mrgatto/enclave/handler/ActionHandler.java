package com.github.mrgatto.enclave.handler;

import com.github.mrgatto.model.EnclaveRequest;

public interface ActionHandler<IN, OUT> {

	boolean canHandle(EnclaveRequest<IN> request);

	OUT execute(EnclaveRequest<IN> request);

}
