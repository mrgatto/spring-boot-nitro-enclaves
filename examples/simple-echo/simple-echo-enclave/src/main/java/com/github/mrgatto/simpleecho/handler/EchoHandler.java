package com.github.mrgatto.simpleecho.handler;

import org.springframework.stereotype.Component;

import com.github.mrgatto.enclave.handler.AbstractActionHandler;
import com.github.mrgatto.simlpeecho.Actions;
import com.github.mrgatto.simlpeecho.model.MyPojoData;
import com.github.mrgatto.simlpeecho.model.MyPojoDataResult;

@Component
public class EchoHandler extends AbstractActionHandler<MyPojoData, MyPojoDataResult> {

	@Override
	public boolean canHandle(String action) {
		return Actions.ECHO.name().equalsIgnoreCase(action);
	}

	@Override
	public MyPojoDataResult handle(MyPojoData data) {
		MyPojoDataResult result = new MyPojoDataResult();
		result.setValue("ECHO from Enclave: " + data.getValue());

		return result;
	}

}
