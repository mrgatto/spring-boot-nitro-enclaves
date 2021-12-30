package com.github.mrgatto.enclave.server.network.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mrgatto.enclave.server.network.Listener;
import com.github.mrgatto.enclave.server.network.ListenerConnection;

public class TCPSocketListener implements Listener {

	private static final Logger LOG = LoggerFactory.getLogger(TCPSocketListener.class);

	private ServerSocket serverSocket;

	private final Integer port;

	public TCPSocketListener(Integer port) {
		this.port = port;
	}

	@Override
	public void start() {
		try {
			LOG.info("Listening for connection on port {}...", this.port);
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			LOG.error("Errror creating TCP ServerSocket", e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void close() {
		LOG.info("Stoping...");
		IOUtils.closeQuietly(this.serverSocket);
	}

	@Override
	public ListenerConnection accept() throws IOException {
		Socket clientSocket = this.serverSocket.accept();
		return new TCPSocketConnection(clientSocket);
	}

}
