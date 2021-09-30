package com.github.mrgatto.enclave.server.network;

import java.io.Closeable;
import java.io.IOException;

public interface Listener extends Closeable {

	void start();

	ListenerConnection accept() throws IOException;

}
