package com.github.mrgatto.enclave.server.network.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.github.mrgatto.enclave.server.network.ListenerConnection;

public class TCPSocketConnection implements ListenerConnection {

	private final Socket socket;

	public TCPSocketConnection(Socket socket) {
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
