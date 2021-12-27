package com.github.mrgatto.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.mrgatto.enclave.kms.DefaultKMSClient;
import com.github.mrgatto.enclave.kms.KMSClient;
import com.github.mrgatto.enclave.nsm.DefaultNsmClient;
import com.github.mrgatto.enclave.nsm.NsmClient;
import com.github.mrgatto.enclave.server.NitroEnclaveServer;
import com.github.mrgatto.enclave.server.network.DefaultListenerConsumer;
import com.github.mrgatto.enclave.server.network.Listener;
import com.github.mrgatto.enclave.server.network.ListenerConsumer;
import com.github.mrgatto.enclave.server.network.tcp.TCPSocketListener;
import com.github.mrgatto.enclave.server.network.vsock.VSockListener;
import com.github.mrgatto.network.DefaultSocketTLV;
import com.github.mrgatto.network.SocketTLV;
import com.github.mrgatto.utils.JsonMapper;

@Configuration
public class EnclaveConfiguration {

	@Value("${nitro.enclave.port:5000}")
	private Integer port;

	@Bean
	@ConditionalOnMissingBean
	public SocketTLV socketTLV() {
		return new DefaultSocketTLV();
	}

	@Bean
	@ConditionalOnMissingBean
	public NsmClient nsmClient(JsonMapper jsonMapper) {
		return new DefaultNsmClient(jsonMapper);
	}

	@Bean
	@ConditionalOnMissingBean
	public KMSClient kmsClient() {
		return new DefaultKMSClient();
	}

	@Bean
	@ConditionalOnProperty(value = "nitro.enclave.network-mode", havingValue = "tcp", matchIfMissing = true)
	public TCPSocketListener tcpSocketListener() {
		return new TCPSocketListener(this.port);
	}

	@Bean
	@ConditionalOnProperty(value = "nitro.enclave.network-mode", havingValue = "vsock")
	public VSockListener vsockListener() {
		return new VSockListener(this.port);
	}

	@Bean
	@ConditionalOnMissingBean
	public ListenerConsumer listenerConsumer(JsonMapper jsonMapper, SocketTLV socketTLV) {
		return new DefaultListenerConsumer(jsonMapper, socketTLV);
	}

	@Bean
	public NitroEnclaveServer nitroEnclaveServer(Listener clientListener, ListenerConsumer listenerConsumer) {
		return new NitroEnclaveServer(clientListener, listenerConsumer);
	}

}
