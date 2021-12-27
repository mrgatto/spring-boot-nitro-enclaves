package com.github.mrgatto.enclave.nsm;

import com.github.mrgatto.enclave.nsm.output.DescribeNsm;
import com.github.mrgatto.enclave.nsm.output.DescribePcr;

public interface NsmClient {

	DescribeNsm describeNsm();

	DescribePcr describePcr(int index);

}
