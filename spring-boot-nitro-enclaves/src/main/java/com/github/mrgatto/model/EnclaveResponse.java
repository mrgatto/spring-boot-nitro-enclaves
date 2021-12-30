package com.github.mrgatto.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

public class EnclaveResponse<T> {

	@JsonProperty("action")
	private String action;

	@JsonProperty("data")
	@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@modelClass")
	private T data;

	@JsonProperty("start-date")
	private Instant startDate;

	@JsonProperty("duration")
	private Long duration;

	@JsonProperty("isError")
	private Boolean isError = false;

	@JsonProperty("error")
	@JsonInclude(Include.NON_NULL)
	private String error;

	@JsonProperty("errorStacktrace")
	@JsonInclude(Include.NON_NULL)
	private String errorStacktrace;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Boolean getIsError() {
		return isError;
	}

	public void setIsError(Boolean isError) {
		this.isError = isError;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorStacktrace() {
		return errorStacktrace;
	}

	public void setErrorStacktrace(String errorStacktrace) {
		this.errorStacktrace = errorStacktrace;
	}

	@Override
	public String toString() {
		return "EnclaveResponse [action=" + action + ", isError=" + isError + ", error=" + error + "]";
	}

}
