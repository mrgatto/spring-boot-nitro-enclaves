package com.github.mrgatto.enclave.server;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.github.mrgatto.enclave.server.network.Listener;
import com.github.mrgatto.enclave.server.network.ListenerConnection;
import com.github.mrgatto.enclave.server.network.ListenerConsumer;

public class NitroEnclaveServer {

	private static final Logger LOG = LoggerFactory.getLogger(NitroEnclaveServer.class);

	private final Listener clientListener;

	private final ListenerConsumer listenerConsumer;

	public NitroEnclaveServer(Listener clientListener, ListenerConsumer listenerConsumer) {
		this.clientListener = clientListener;
		this.listenerConsumer = listenerConsumer;
	}

	@PostConstruct
	private void init() {
		LOG.info("Configured Socket Listener: {}", this.clientListener.getClass());
	}

	public void run() {
		Assert.notNull(this.clientListener, "Listener must not be null");

		LOG.info("Starting {} listener", ClassUtils.getSimpleName(this.clientListener.getClass()));
		this.clientListener.start();

		try {
			while (true) {
				try (ListenerConnection conn = this.clientListener.accept()) {
					LOG.info("Accepted connection {}", conn);
					this.listenerConsumer.process(conn);
					LOG.info("End of connection {}", conn);
				} catch (IOException e) {
					LOG.error("Error dealing with connection", e);
				}
			}
		} finally {
			IOUtils.closeQuietly(this.clientListener);
		}
	}

}
