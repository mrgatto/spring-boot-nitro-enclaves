package com.github.mrgatto.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Wrapper class for JSON serialization/deserialization.
 *
 *
 */
public class JsonMapper {

	private static final Logger LOG = LoggerFactory.getLogger(JsonMapper.class);

	private ObjectMapper objectMapper;

	public JsonMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public byte[] writeValueAsBytes(Object object) {
		try {
			return this.objectMapper.writeValueAsBytes(object);
		} catch (JsonProcessingException e) {
			String error = "Error in serialize object to format";
			LOG.error(error, e);
			throw new IllegalStateException(error);
		}
	}

	public String writeValueAsString(Object object) {
		try {
			return this.objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			String error = "Error in serialize object to format";
			LOG.error(error, e);
			throw new IllegalStateException(error);
		}
	}

	public <T> T readValue(byte[] src, TypeReference<T> valueTypeRef) {
		try {
			return this.objectMapper.readValue(src, valueTypeRef);
		} catch (Exception e) {
			String error = "Error in deserialize format to object";
			LOG.error(error, e);
			throw new IllegalStateException(error);
		}
	}

	public <T> T readValue(byte[] src, Class<T> valueType) {
		try {
			return this.objectMapper.readValue(src, valueType);
		} catch (IOException e) {
			String error = "Error in deserialize format to object";
			LOG.error(error, e);
			throw new IllegalStateException(error);
		}
	}

}
