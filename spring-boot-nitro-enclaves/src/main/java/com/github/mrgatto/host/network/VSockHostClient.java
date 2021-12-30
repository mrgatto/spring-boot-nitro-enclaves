package com.github.mrgatto.host.network;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mrgatto.network.SocketTLV;

import solutions.cloudarchitects.vsockj.VSock;
import solutions.cloudarchitects.vsockj.VSockAddress;

public class VSockHostClient extends AbstractSocketHostClient {

	private static final Logger LOG = LoggerFactory.getLogger(VSockHostClient.class);

	private final Integer cid;

	public VSockHostClient(Integer port, Integer cid, SocketTLV socketTLV) {
		super(port, socketTLV);
		this.cid = cid;
	}

	@Override
	public byte[] send(byte[] content) {
		VSock clientSocket = null;
		InputStream in = null;
		OutputStream out = null;

		try {
			clientSocket = new VSock(new VSockAddress(this.cid, this.port));

			in = clientSocket.getInputStream();
			out = clientSocket.getOutputStream();

			this.socketTLV.sendContent(content, out);

			byte[] rcvd = this.socketTLV.receiveContent(in);
			LOG.info("Received {} bytes", rcvd.length);

			return rcvd;

		} catch (Exception e) {
			LOG.error("VSock error", e);
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(clientSocket);
		}
	}

}
