package com.github.mrgatto.host.network;

import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mrgatto.utils.SocketTLV;

import solutions.cloudarchitects.vsockj.VSock;
import solutions.cloudarchitects.vsockj.VSockAddress;

public class VSockHostClient implements HostClient {

	private static final Logger LOG = LoggerFactory.getLogger(VSockHostClient.class);

	private Integer port;

	private Integer cid;

	public VSockHostClient(Integer port, Integer cid) {
		this.port = port;
		this.cid = cid;
	}

	@PostConstruct
	private void init() {
		LOG.info("Vocket Client on cid {}, port {}", this.cid, this.port);
	}

	@Override
	public byte[] send(byte[] content) {
		VSock clientSocket = null;
		InputStream in = null;
		OutputStream out = null;

		try {
			clientSocket = new VSock(new VSockAddress(this.cid, this.port));

			in = clientSocket.getInputStream();
			out = clientSocket.getOutputStream();

			SocketTLV.sendContent(content, out);

			byte[] rcvd = SocketTLV.receiveContent(in);
			LOG.info("Received {} bytes", rcvd.length);

			return rcvd;

		} catch (Exception e) {
			LOG.error("VSock error", e);
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(clientSocket);
		}
	}

}
