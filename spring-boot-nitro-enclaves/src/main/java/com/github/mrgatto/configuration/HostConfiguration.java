package com.github.mrgatto.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.mrgatto.host.NitroEnclaveClient;
import com.github.mrgatto.host.network.HostClient;
import com.github.mrgatto.host.network.TCPSocketHostClient;
import com.github.mrgatto.host.network.VSockHostClient;
import com.github.mrgatto.network.DefaultSocketTLV;
import com.github.mrgatto.network.SocketTLV;
import com.github.mrgatto.utils.JsonMapper;

@Configuration
public class HostConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(HostConfiguration.class);

	@Value("${nitro.enclave.port:5000}")
	private Integer port;

	@Value("${cid:0}")
	private Integer cid;

	@Bean
	@ConditionalOnMissingBean
	public SocketTLV socketTLV() {
		return new DefaultSocketTLV();
	}

	@Bean
	@ConditionalOnProperty(value = "nitro.enclave.network-mode", havingValue = "tcp", matchIfMissing = true)
	public HostClient tcpSocketHostClient(SocketTLV socketTLV) {
		LOG.info("Creating TCP Socket Client on port {}...", this.port);
		return new TCPSocketHostClient(this.port, socketTLV);
	}

	@Bean
	@ConditionalOnProperty(value = "nitro.enclave.network-mode", havingValue = "vsock")
	public HostClient vsockHostClient(SocketTLV socketTLV) {
		LOG.info("Creating VSock Client on cid {}, port {}...", this.cid, this.port);
		return new VSockHostClient(this.port, this.cid, socketTLV);
	}

	@Bean
	public NitroEnclaveClient nitroEnclaveClient(HostClient hostClient, JsonMapper jsonMapper) {
		return new NitroEnclaveClient(jsonMapper, hostClient);
	}

}
