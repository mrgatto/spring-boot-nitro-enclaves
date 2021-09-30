package com.github.mrgatto.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.mrgatto.enclave.server.NitroEnclaveServer;
import com.github.mrgatto.enclave.server.network.DefaultListenerConsumer;
import com.github.mrgatto.enclave.server.network.Listener;
import com.github.mrgatto.enclave.server.network.ListenerConsumer;
import com.github.mrgatto.enclave.server.network.tcp.TCPSocketListener;
import com.github.mrgatto.enclave.server.network.vsock.VSockListener;
import com.github.mrgatto.utils.JsonMapper;

@Configuration
public class EnclaveConfiguration {

	@Value("${nitro.enclave.port:5000}")
	private Integer port;

	@Bean
	@ConditionalOnProperty(value = "nitro.enclave.network-mode", havingValue = "tcp", matchIfMissing = true)
	public TCPSocketListener tcpSocketListener() {
		TCPSocketListener tcpHostClient = new TCPSocketListener(this.port);
		return tcpHostClient;
	}

	@Bean
	@ConditionalOnProperty(value = "nitro.enclave.network-mode", havingValue = "vsock")
	public VSockListener vsockListener() {
		VSockListener tcpHostClient = new VSockListener(this.port);
		return tcpHostClient;
	}

	@Bean
	@ConditionalOnMissingBean
	public ListenerConsumer listenerConsumer(JsonMapper jsonMapper) {
		DefaultListenerConsumer listenerConsumer = new DefaultListenerConsumer(jsonMapper);
		return listenerConsumer;
	}

	@Bean
	public NitroEnclaveServer nitroEnclaveServer(Listener clientListener, ListenerConsumer listenerConsumer) {
		NitroEnclaveServer nitroEnclaveServer = new NitroEnclaveServer(clientListener, listenerConsumer);
		return nitroEnclaveServer;
	}

}
