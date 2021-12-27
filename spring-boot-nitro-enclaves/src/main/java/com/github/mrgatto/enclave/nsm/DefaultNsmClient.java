package com.github.mrgatto.enclave.nsm;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.github.mrgatto.enclave.nsm.output.DescribeNsm;
import com.github.mrgatto.utils.JsonMapper;

/**
 * Default NSM (Nitro Security Module) client implementation that use provided
 * nsm-cli binary.
 * <p>
 * More info at https://github.com/mrgatto/nsm-cli
 *
 */
public class DefaultNsmClient implements NsmClient {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultNsmClient.class);

	@Value("${nitro.enclave.nsm-cli:/app/nsm-cli}")
	private String nsmCli;

	private final JsonMapper jsonMapper;

	public DefaultNsmClient(JsonMapper jsonMapper) {
		this.jsonMapper = jsonMapper;

	}

	@Override
	public DescribeNsm describeNsm() {
		try {
			LOG.info("Executing nsm-cli ({})...", this.nsmCli);

			String[] cmd = new String[] { this.nsmCli, "describe-nsm" };

			ProcessBuilder builder = new ProcessBuilder(Arrays.asList(cmd));
			builder.inheritIO().redirectOutput(ProcessBuilder.Redirect.PIPE);
			Process process = builder.start();

			String procOutput = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
			process.waitFor();

			LOG.debug("nsm-cli output: {}", procOutput);

			return this.jsonMapper.readValue(procOutput, DescribeNsm.class);

		} catch (Exception e) {
			LOG.error("Cannot execute " + this.nsmCli, e);
			throw new RuntimeException(e);
		}
	}

}
