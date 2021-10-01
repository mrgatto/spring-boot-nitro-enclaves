package com.github.mrgatto.host.network;

import com.github.mrgatto.network.SocketTLV;

public abstract class AbstractSocketHostClient implements HostClient {

	protected Integer port;

	protected SocketTLV socketTLV;

	public AbstractSocketHostClient(Integer port, SocketTLV socketTLV) {
		this.port = port;
		this.socketTLV = socketTLV;
	}

}
