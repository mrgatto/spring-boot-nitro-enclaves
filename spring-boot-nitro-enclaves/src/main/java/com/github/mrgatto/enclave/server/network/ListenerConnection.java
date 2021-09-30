package com.github.mrgatto.enclave.server.network;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ListenerConnection extends Closeable {

	InputStream getInputStream() throws IOException;

	OutputStream getOutputStream() throws IOException;

}
