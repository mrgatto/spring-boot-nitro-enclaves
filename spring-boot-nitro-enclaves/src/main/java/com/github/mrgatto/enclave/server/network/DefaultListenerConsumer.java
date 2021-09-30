package com.github.mrgatto.enclave.server.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mrgatto.enclave.handler.ActionHandler;
import com.github.mrgatto.model.EnclaveRequest;
import com.github.mrgatto.model.EnclaveResponse;
import com.github.mrgatto.utils.JsonMapper;
import com.github.mrgatto.utils.SocketTLV;

public class DefaultListenerConsumer implements ListenerConsumer, ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultListenerConsumer.class);

	private List<ActionHandler<?, ?>> handlers;

	private JsonMapper jsonMapper;

	public DefaultListenerConsumer(JsonMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
		this.handlers = new ArrayList<>();
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		contextRefreshedEvent.getApplicationContext().getBeansOfType(ActionHandler.class).values()
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

			byte[] rcvd = SocketTLV.receiveContent(in);
			LOG.debug("Received {} bytes", rcvd.length);

			byte[] output = this.processEnclaveRequest(rcvd);
			LOG.debug("Sending {} bytes", output.length);
			SocketTLV.sendContent(output, out);
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

			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			response.setStacktrace(sw.toString());
		} finally {
			response.setDuration(Duration.between(now, Instant.now()).toMillis());
		}

		return this.jsonMapper.writeValueAsBytes(response);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object processEnclaveRequest(EnclaveRequest request) {
		ActionHandler<?, ?> handler = this.getHandler(request);

		LOG.debug("Executing handler {} for request {}", ClassUtils.getSimpleName(handler), request);
		Object result = handler.execute(request);
		return result;
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
