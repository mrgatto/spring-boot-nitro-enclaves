package com.github.mrgatto.enclave.nsm.output;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DescribePcr {

	@JsonProperty("data")
	private List<Integer> data;

	@JsonProperty("lock")
	private Boolean lock;

	public List<Integer> getData() {
		return data;
	}

	public String getDataAsString() {
		return StringUtils.join(this.data, StringUtils.EMPTY);
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}

	public Boolean getLock() {
		return lock;
	}

	public void setLock(Boolean lock) {
		this.lock = lock;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
