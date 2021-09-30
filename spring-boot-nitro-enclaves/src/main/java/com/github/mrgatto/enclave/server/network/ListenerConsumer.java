package com.github.mrgatto.enclave.server.network;

import java.io.IOException;

public interface ListenerConsumer {

	void process(ListenerConnection connection) throws IOException;

}
