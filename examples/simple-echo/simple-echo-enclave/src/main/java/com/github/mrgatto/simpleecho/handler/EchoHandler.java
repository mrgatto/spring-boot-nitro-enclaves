package com.github.mrgatto.simpleecho.handler;

import org.springframework.stereotype.Component;

import com.github.mrgatto.enclave.handler.AbstractActionHandler;
import com.github.mrgatto.simlpeecho.model.MyPojoData;
import com.github.mrgatto.simlpeecho.model.MyPojoDataResult;

@Component
public class EchoHandler extends AbstractActionHandler<MyPojoData, MyPojoDataResult> {

	@Override
	public boolean canHandle(String action) {
		return "echo".equalsIgnoreCase(action);
	}

	@Override
	public MyPojoDataResult execute(MyPojoData request) {
		MyPojoDataResult result = new MyPojoDataResult();
		result.setValue("Echo from Enclave: " + request.getValue());

		return result;
	}

}