package com.github.mrgatto.model;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnclaveRequestTest {

	@Test
	public void uuidTest() {
		EnclaveRequest<Object> request = new EnclaveRequest<>();

		Assertions.assertNotNull(request.getId());

		try {
			UUID.fromString(request.getId());
		} catch (Exception e) {
			Assertions.fail("Invalid UUID");
		}
	}

}
