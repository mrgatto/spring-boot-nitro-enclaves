package com.github.mrgatto.enclave.server.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mrgatto.enclave.handler.ActionHandler;
import com.github.mrgatto.model.EnclaveRequest;
import com.github.mrgatto.model.EnclaveResponse;
import com.github.mrgatto.network.SocketTLV;
import com.github.mrgatto.utils.JsonMapper;

public class DefaultListenerConsumer implements ListenerConsumer, ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultListenerConsumer.class);

	private final List<ActionHandler<?, ?>> handlers;

	private final JsonMapper jsonMapper;

	private final SocketTLV socketTLV;

	public DefaultListenerConsumer(JsonMapper jsonMapper, SocketTLV socketTLV) {
		this.jsonMapper = jsonMapper;
		this.socketTLV = socketTLV;
		this.handlers = new ArrayList<>();
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		contextRefreshedEvent.getApplicationContext().getBeansOfType(ActionHandler.class)
			.values()
			.forEach(handler -> this.handlers.add(handler));

		String loadedHandlers = this.handlers.stream()
				.map(ClassUtils::getSimpleName)
				.collect(Collectors.joining(","));

		LOG.info("Loaded handlers: {}", loadedHandlers);
	}

	@Override
	public void process(ListenerConnection connection) throws IOException {
		InputStream in = null;
		OutputStream out = null;

		try {
			in = connection.getInputStream();
			out = connection.getOutputStream();

			byte[] rcvd = this.socketTLV.receiveContent(in);

			byte[] output = this.processEnclaveRequest(rcvd);
			this.socketTLV.sendContent(output, out);
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}
	}

	private byte[] processEnclaveRequest(byte[] rcvd) {
		Instant now = Instant.now();

		EnclaveResponse<Object> response = new EnclaveResponse<>();
		response.setStartDate(now);

		try {
			EnclaveRequest<?> enclaveInput = this.jsonMapper.readValue(rcvd, new TypeReference<EnclaveRequest<Object>>() { });
			response.setAction(enclaveInput.getAction());

			Object contentOutput = this.processEnclaveRequest(enclaveInput);
			response.setData(contentOutput);

		} catch (Throwable e) {
			LOG.error("Error processing enclave request", e);
			response.setIsError(true);
			response.setError(e.getMessage());

			response.setErrorStacktrace(ExceptionUtils.getStackTrace(e));
		} finally {
			response.setDuration(Duration.between(now, Instant.now()).toMillis());
		}

		return this.jsonMapper.writeValueAsBytes(response);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object processEnclaveRequest(EnclaveRequest request) {
		ActionHandler<?, ?> handler = this.getHandler(request);

		LOG.debug("Executing handler {} for request {}", ClassUtils.getSimpleName(handler), request);
		return handler.handle(request);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ActionHandler<?, ?> getHandler(EnclaveRequest request) {
		for (ActionHandler<?, ?> aHandler : this.handlers) {
			if (aHandler.canHandle(request)) {
				return aHandler;
			}
		}

		throw new IllegalStateException("No handler found for request " + request);
	}

}
