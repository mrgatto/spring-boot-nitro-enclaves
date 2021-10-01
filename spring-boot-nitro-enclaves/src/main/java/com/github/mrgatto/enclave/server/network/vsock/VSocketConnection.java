package com.github.mrgatto.enclave.server.network.vsock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.github.mrgatto.enclave.server.network.ListenerConnection;

import solutions.cloudarchitects.vsockj.VSock;

public class VSocketConnection implements ListenerConnection {

	private final VSock socket;

	public VSocketConnection(VSock socket) {
		this.socket = socket;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.socket.getInputStream();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return this.socket.getOutputStream();
	}

	@Override
	public void close() throws IOException {
		this.socket.close();
	}

	@Override
	public String toString() {
		return this.socket.toString();
	}

}
