package com.github.mrgatto.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.util.Assert;

public class SocketTLV {

	private static final int BUFFER_SIZE = 1024;

	public static void sendContent(byte[] content, OutputStream output) throws IOException {
		Assert.notNull(content, "content must not be null");
		Assert.notNull(output, "output must not be null");

		DataOutputStream dos = new DataOutputStream(output);
		dos.writeInt(content.length);
		dos.write(content);
	}

	public static byte[] receiveContent(InputStream input) throws IOException {
		Assert.notNull(input, "input must not be null");

		DataInputStream dis = new DataInputStream(input);
		int len = dis.readInt();

		return receiveAll(dis, len);
	}

	private static byte[] receiveAll(DataInputStream input, int length) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		while (output.size() < length) {
			byte[] buffer = new byte[Math.min(BUFFER_SIZE, length - output.size())];
			input.read(buffer);
			output.write(buffer);
		}

		return output.toByteArray();
	}

}
