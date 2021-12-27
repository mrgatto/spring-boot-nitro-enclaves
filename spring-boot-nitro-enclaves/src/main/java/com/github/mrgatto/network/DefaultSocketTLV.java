package com.github.mrgatto.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Conversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class DefaultSocketTLV implements SocketTLV {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultSocketTLV.class);

	@Override
	public void sendContent(byte[] content, OutputStream output) throws IOException {
		Assert.notNull(content, "content must not be null");
		Assert.notNull(output, "output must not be null");

		LOG.trace("Writing {} bytes of data", content.length);

		byte length[] = new byte[Integer.BYTES];
	    Conversion.intToByteArray(content.length, 0, length, 0, length.length);

	    output.write(length);
	    output.write(content);
	}

	@Override
	public byte[] receiveContent(InputStream input) throws IOException {
		Assert.notNull(input, "input must not be null");

		byte[] bytes = new byte[Integer.BYTES];
		IOUtils.read(input, bytes);

		int length = Conversion.byteArrayToInt(bytes, 0, 0, 0, bytes.length);
		LOG.trace("Reading {} bytes of data", length);

		byte[] buffer = new byte[length];
		IOUtils.read(input, buffer);

		return buffer;
	}

}
