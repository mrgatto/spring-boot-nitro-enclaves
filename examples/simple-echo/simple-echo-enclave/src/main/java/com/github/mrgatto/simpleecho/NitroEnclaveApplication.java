package com.github.mrgatto.simpleecho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.github.mrgatto.autoconfigure.EnableNitroEnclavesEnclaveSide;
import com.github.mrgatto.enclave.server.NitroEnclaveServer;

@SpringBootApplication
@ComponentScan({ "com.github.mrgatto.simpleecho" })
@EnableNitroEnclavesEnclaveSide
public class NitroEnclaveApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(NitroEnclaveApplication.class, args);

		NitroEnclaveServer server = ctx.getBean(NitroEnclaveServer.class);
		server.run();
	}

}
