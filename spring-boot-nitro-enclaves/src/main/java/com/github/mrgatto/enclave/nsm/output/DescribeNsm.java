package com.github.mrgatto.enclave.nsm.output;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DescribeNsm {

	@JsonProperty("digest")
	private String digest;

	@JsonProperty("locked_pcrs")
	private List<Integer> lockedPcrs;

	@JsonProperty("max_pcrs")
	private Integer maxPcrs;

	@JsonProperty("module_id")
	private String moduleId;

	@JsonProperty("version_major")
	private Integer versionMajor;

	@JsonProperty("version_minor")
	private Integer versionMinor;

	@JsonProperty("version_patch")
	private Integer versionPatch;

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public List<Integer> getLockedPcrs() {
		return lockedPcrs;
	}

	public void setLockedPcrs(List<Integer> lockedPcrs) {
		this.lockedPcrs = lockedPcrs;
	}

	public Integer getMaxPcrs() {
		return maxPcrs;
	}

	public void setMaxPcrs(Integer maxPcrs) {
		this.maxPcrs = maxPcrs;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public Integer getVersionMajor() {
		return versionMajor;
	}

	public void setVersionMajor(Integer versionMajor) {
		this.versionMajor = versionMajor;
	}

	public Integer getVersionMinor() {
		return versionMinor;
	}

	public void setVersionMinor(Integer versionMinor) {
		this.versionMinor = versionMinor;
	}

	public Integer getVersionPatch() {
		return versionPatch;
	}

	public void setVersionPatch(Integer versionPatch) {
		this.versionPatch = versionPatch;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
