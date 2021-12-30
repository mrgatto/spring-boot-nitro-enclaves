package com.github.mrgatto.host.network;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mrgatto.network.SocketTLV;

public class TCPSocketHostClient extends AbstractSocketHostClient {

	private static final Logger LOG = LoggerFactory.getLogger(TCPSocketHostClient.class);

	public TCPSocketHostClient(Integer port, SocketTLV socketTLV) {
		super(port, socketTLV);
	}

	@Override
	public byte[] send(byte[] content) {
		Socket clientSocket = null;
		InputStream in = null;
		OutputStream out = null;

		try {
			clientSocket = new Socket("localhost", this.port);

			in = clientSocket.getInputStream();
			out = clientSocket.getOutputStream();

			this.socketTLV.sendContent(content, out);

			byte[] rcvd = this.socketTLV.receiveContent(in);
			LOG.info("Received {} bytes", rcvd.length);

			return rcvd;

		} catch (Exception e) {
			LOG.error("Socket error", e);
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(clientSocket);
		}
	}

}
