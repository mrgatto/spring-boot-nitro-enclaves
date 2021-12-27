package com.github.mrgatto.utils;

import java.io.Serializable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mrgatto.configuration.JacksonConfiguration;
import com.github.mrgatto.model.EnclaveRequest;

@SpringBootTest(classes = JacksonConfiguration.class)
public class JsonMapperTest {

	@Autowired
	private JsonMapper jsonMapper;

	@Test
	public void serializationPrimitiveTest() {
		EnclaveRequest<String> request = new EnclaveRequest<>();
		request.setAction("ACTION");
		request.setData("OK");

		byte[] bytes = this.jsonMapper.writeValueAsBytes(request);

		EnclaveRequest<String> readRequest = this.jsonMapper.readValue(bytes, new TypeReference<EnclaveRequest<String>>() {
		});

		Assertions.assertEquals(request.getAction(), readRequest.getAction());
		Assertions.assertEquals(request.getData(), readRequest.getData());
	}

	@Test
	public void serializationBeanTest() {
		EnclaveRequest<MyBean> request = new EnclaveRequest<>();
		request.setAction("ACTION");

		MyBean myBean = new MyBean();
		request.setData(myBean);

		byte[] bytes = this.jsonMapper.writeValueAsBytes(request);

		EnclaveRequest<MyBean> readRequest = this.jsonMapper.readValue(bytes, new TypeReference<EnclaveRequest<MyBean>>() {
		});

		Assertions.assertEquals(request.getAction(), readRequest.getAction());
		Assertions.assertEquals(myBean.value, readRequest.getData().value);
	}

	static class MyBean implements Serializable {

		private static final long serialVersionUID = 1L;

		@JsonProperty
		double value = Math.random();
	}

}
