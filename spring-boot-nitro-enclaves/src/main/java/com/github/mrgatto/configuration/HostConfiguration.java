package com.github.mrgatto.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.mrgatto.host.NitroEnclaveClient;
import com.github.mrgatto.host.network.HostClient;
import com.github.mrgatto.host.network.TCPSocketHostClient;
import com.github.mrgatto.host.network.VSockHostClient;
import com.github.mrgatto.utils.JsonMapper;

@Configuration
public class HostConfiguration {

	@Value("${nitro.enclave.port:5000}")
	private Integer port;

	@Value("${cid:0}")
	private Integer cid;

	@Bean
	@ConditionalOnProperty(value = "nitro.enclave.network-mode", havingValue = "tcp", matchIfMissing = true)
	public HostClient tcpSocketHostClient() {
		TCPSocketHostClient tcpHostClient = new TCPSocketHostClient(this.port);
		return tcpHostClient;
	}

	@Bean
	@ConditionalOnProperty(value = "nitro.enclave.network-mode", havingValue = "vsock")
	public HostClient vsockHostClient() {
		VSockHostClient tcpHostClient = new VSockHostClient(this.port, this.cid);
		return tcpHostClient;
	}

	@Bean
	public NitroEnclaveClient nitroEnclaveClient(HostClient hostClient, JsonMapper jsonMapper) {
		NitroEnclaveClient nitroClient = new NitroEnclaveClient(jsonMapper, hostClient);
		return nitroClient;
	}

}
