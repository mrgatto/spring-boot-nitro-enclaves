package com.github.mrgatto.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SocketTLV {

	void sendContent(byte[] content, OutputStream output) throws IOException;

	byte[] receiveContent(InputStream input) throws IOException;

}
