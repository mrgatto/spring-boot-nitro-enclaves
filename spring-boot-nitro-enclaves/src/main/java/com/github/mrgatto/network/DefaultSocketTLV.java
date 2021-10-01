package com.github.mrgatto.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.util.Assert;

public class DefaultSocketTLV implements SocketTLV {

	private static final int DEFAULT_BUFFER_SIZE = 1024;

	private int bufferSize;

	public DefaultSocketTLV() {
		this(DEFAULT_BUFFER_SIZE);
	}

	public DefaultSocketTLV(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	@Override
	public void sendContent(byte[] content, OutputStream output) throws IOException {
		Assert.notNull(content, "content must not be null");
		Assert.notNull(output, "output must not be null");

		DataOutputStream dos = new DataOutputStream(output);
		dos.writeInt(content.length);
		dos.write(content);
	}

	@Override
	public byte[] receiveContent(InputStream input) throws IOException {
		Assert.notNull(input, "input must not be null");

		DataInputStream dis = new DataInputStream(input);
		int len = dis.readInt();

		return this.receiveAll(dis, len);
	}

	private byte[] receiveAll(DataInputStream input, int length) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		while (output.size() < length) {
			byte[] buffer = new byte[Math.min(this.bufferSize, length - output.size())];
			input.read(buffer);
			output.write(buffer);
		}

		return output.toByteArray();
	}

}
