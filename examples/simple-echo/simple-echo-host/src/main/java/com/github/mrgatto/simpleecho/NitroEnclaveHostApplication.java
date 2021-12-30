package com.github.mrgatto.simpleecho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.github.mrgatto.autoconfigure.EnableNitroEnclavesHostSide;
import com.github.mrgatto.host.NitroEnclaveClient;
import com.github.mrgatto.model.EnclaveRequest;
import com.github.mrgatto.model.EnclaveResponse;
import com.github.mrgatto.simlpeecho.Actions;
import com.github.mrgatto.simlpeecho.model.MyPojoData;
import com.github.mrgatto.simlpeecho.model.MyPojoDataResult;

@SpringBootApplication
@ComponentScan({ "com.github.mrgatto.simpleecho" })
@EnableNitroEnclavesHostSide
public class NitroEnclaveHostApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(NitroEnclaveHostApplication.class, args);

		NitroEnclaveClient client = ctx.getBean(NitroEnclaveClient.class);

		MyPojoData pojo = new MyPojoData();
		pojo.setValue("Hello World!");

		EnclaveRequest<MyPojoData> request = new EnclaveRequest<>();
		request.setAction(Actions.ECHO.name());
		request.setData(pojo);

		EnclaveResponse<MyPojoDataResult> response = client.send(request);

		if (response.getIsError()) {
			System.out.println(String.format("Something went wrong: %s", response.getError()));
			System.out.println(response.getErrorStacktrace());
		} else {
			System.out.println(response.getData().getValue());
		}

		System.out.println(String.format("Enclave execution time %sms", response.getDuration()));

	}

}
