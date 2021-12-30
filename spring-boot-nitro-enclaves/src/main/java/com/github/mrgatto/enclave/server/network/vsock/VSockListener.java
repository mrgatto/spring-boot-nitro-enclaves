package com.github.mrgatto.enclave.server.network.vsock;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mrgatto.enclave.server.network.Listener;
import com.github.mrgatto.enclave.server.network.ListenerConnection;

import solutions.cloudarchitects.vsockj.ServerVSock;
import solutions.cloudarchitects.vsockj.VSock;
import solutions.cloudarchitects.vsockj.VSockAddress;

public class VSockListener implements Listener {

	private static final Logger LOG = LoggerFactory.getLogger(VSockListener.class);

	private ServerVSock serverVSocket;

	private final Integer port;

	public VSockListener(Integer port) {
		this.port = port;
	}

	@Override
	public void start() {
		try {
			LOG.info("Listening for connection on port {}...", this.port);
			this.serverVSocket = new ServerVSock();
			this.serverVSocket.bind(new VSockAddress(VSockAddress.VMADDR_CID_ANY, this.port));
		} catch (IOException e) {
			LOG.error("Errror creating VSock ServerSocket", e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void close() {
		LOG.info("Stopping...");
		IOUtils.closeQuietly(this.serverVSocket);
	}

	@Override
	public ListenerConnection accept() throws IOException {
		VSock vsock = this.serverVSocket.accept();
		return new VSocketConnection(vsock);
	}

}
