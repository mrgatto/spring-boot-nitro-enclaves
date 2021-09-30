package com.github.mrgatto.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SocketTLVTest {

	@Test
	void writeReadTest() throws IOException {

		final String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam euismod mi erat,"
				+ " quis cursus quam facilisis et. Aenean vestibulum nisi id posuere aliquam. Nullam gravida a elit id elementum."
				+ " Donec arcu libero, aliquam eu ex vitae, malesuada imperdiet magna. In fermentum, lectus commodo congue vehicula,"
				+ " nunc ante lacinia leo, at sagittis dui elit eget velit. Mauris scelerisque bibendum elit, sed tempus enim fermentum vitae."
				+ " Aliquam erat volutpat. Nullam at vestibulum leo. Suspendisse malesuada neque eu odio rhoncus porta."
				+ " Proin ut fringilla nibh. Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		SocketTLV.sendContent(content.getBytes(), baos);

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		byte[] result = SocketTLV.receiveContent(bais);

		Assertions.assertEquals(content, new String(result));
	}

	@Test
	void writeReadBigTest() throws IOException {

		final String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam euismod mi erat,"
				+ " quis cursus quam facilisis et. Aenean vestibulum nisi id posuere aliquam. Nullam gravida a elit id elementum."
				+ " Donec arcu libero, aliquam eu ex vitae, malesuada imperdiet magna. In fermentum, lectus commodo congue vehicula,"
				+ " nunc ante lacinia leo, at sagittis dui elit eget velit. Mauris scelerisque bibendum elit, sed tempus enim fermentum vitae."
				+ " Aliquam erat volutpat. Nullam at vestibulum leo. Suspendisse malesuada neque eu odio rhoncus porta."
				+ " Proin ut fringilla nibh. Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

		final String bigContent = IntStream.range(0, 10)
			.mapToObj(i -> content)
			.collect(Collectors.joining());

		Assertions.assertTrue(bigContent.getBytes().length > 1024);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		SocketTLV.sendContent(bigContent.getBytes(), baos);

		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		byte[] result = SocketTLV.receiveContent(bais);

		Assertions.assertEquals(bigContent, new String(result));
	}

}