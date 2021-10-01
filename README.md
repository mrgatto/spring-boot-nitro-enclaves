# Spring Boot Nitro Enclave

This is a _proof of concept_ of a Spring Boot library for easy creation of [AWS Nitro Enclaves](https://docs.aws.amazon.com/enclaves/latest/user/nitro-enclave.html) applications.


# Introduction

An enclave application partitions itself into two components:

1. An untrusted component (called the host) and
2. A trusted component (called the enclave).

An _enclave_ is a protected memory region that provides confidentiality for data
and code execution. It is an instance of a Trusted Execution Environment (TEE)
which is usually secured by hardware.

# Overview


# Tools / Libs

* [aws-nitro-enclaves-cli](https://docs.aws.amazon.com/enclaves/latest/user/nitro-enclave-cli-install.html)

* kmstool-enclave-cli & libnsm.so

 Build instructions at [kmstool-enclave-cli](https://github.com/aws/aws-nitro-enclaves-sdk-c/tree/main/bin/kmstool-enclave-cli).

* vsockj

This project uses the _vsockj_ library for vsock communication.
Build instructions at [vsockj](https://github.com/Cloud-Architects/vsockj).


# Examples

* [Hello World Echo](examples/simple-echo)
* [KMS Encrypt/Decrypt](examples/kms-encrypt)
