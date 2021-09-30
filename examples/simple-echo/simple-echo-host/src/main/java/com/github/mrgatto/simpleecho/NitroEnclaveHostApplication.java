package com.github.mrgatto.simpleecho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.github.mrgatto.autoconfigure.EnableNitroEnclavesHostSide;
import com.github.mrgatto.host.NitroEnclaveClient;
import com.github.mrgatto.model.EnclaveRequest;
import com.github.mrgatto.model.EnclaveResponse;
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
		request.setAction("echo");
		request.setData(pojo);

		EnclaveResponse<MyPojoDataResult> resp = client.send(request);
		System.out.println(resp.getData().getValue());
	}

}
