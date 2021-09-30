package com.github.mrgatto.host.network;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mrgatto.utils.SocketTLV;

public class TCPSocketHostClient implements HostClient {

	private static final Logger LOG = LoggerFactory.getLogger(TCPSocketHostClient.class);

	private Integer port;

	public TCPSocketHostClient(Integer port) {
		this.port = port;
	}

	@PostConstruct
	private void init() {
		LOG.info("TCP Socket Client on port {}", this.port);
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

			SocketTLV.sendContent(content, out);

			byte[] rcvd = SocketTLV.receiveContent(in);
			LOG.info("Received {} bytes", rcvd.length);

			return rcvd;

		} catch (Exception e) {
			LOG.error("Socket error", e);
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(clientSocket);
		}
	}

}
