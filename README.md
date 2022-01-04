# Spring Boot Nitro Enclave
[![Java CI with Maven](https://github.com/mrgatto/spring-boot-nitro-enclaves/actions/workflows/maven.yml/badge.svg)](https://github.com/mrgatto/spring-boot-nitro-enclaves/actions/workflows/maven.yml) [![Code Grade](https://api.codiga.io/project/29257/score/svg)](https://www.codiga.io)



This is a working (and usable) _proof of concept_ Spring Boot library for easy creation of [AWS Nitro Enclaves](https://docs.aws.amazon.com/enclaves/latest/user/nitro-enclave.html) applications.

**Objectives:**

- Easy development of Nitro Enclaves applications.
- Abstraction of the _vsock_ host-enclave communication with a mock mode, TCP implementaion for easy local test/development (even on Windows :sunglasses:). 
- Schemaless with automatic serialization/deserialization (JSON, further improvement should be BSON)
- Ready-to-use integration with the Nitro Security Module (NSM).
- Ready-to-use integration with the AWS KMS.

<h5>* Some ideas came from <a href="https://developer.r3.com/conclave/">R3 Conclave</a></h5>

# Introduction

An _enclave_ is a protected memory region that provides confidentiality for data and code execution. It is an instance of a Trusted Execution Environment (TEE)
which is usually secured by hardware.

An enclave application partitions itself into two components:

1. An untrusted component (called the **host**) and
2. A trusted component (called the **enclave**).

# Overview

<p align="center">
  <img src="docs/draw.io.drawio.png" />
</p>

## Usage

See project [examples](#examples) below.

### Enclave Application

```java
@SpringBootApplication
@ComponentScan({ "my.app.package" })
@EnableNitroEnclavesEnclaveSide
public class NitroEnclaveApplication {

  public static void main(String[] args) {
    ApplicationContext ctx = SpringApplication.run(NitroEnclaveApplication.class, args);

    NitroEnclaveServer server = ctx.getBean(NitroEnclaveServer.class);
    server.run();
  }
}

// -----
// Your business logic handlers
// -----

@Component
public class MyActionHandler extends AbstractActionHandler<MyPojoData, MyPojoDataResult> {

  @Override
    public boolean canHandle(String action) {
      return "action_to_execute".equalsIgnoreCase(action);
    }

    @Override
    public MyPojoDataResult handle(MyPojoData data) {
      // my logic
    }
}
```

### Host Application

```java
@SpringBootApplication
@ComponentScan({ "my.app.package" })
@EnableNitroEnclavesHostSide
public class NitroEnclaveHostApplication {

  public static void main(String[] args) {
   ApplicationContext ctx = SpringApplication.run(NitroEnclaveHostApplication.class, args);
   NitroEnclaveClient client = ctx.getBean(NitroEnclaveClient.class);

   // -----
   // Your Enclave interaction
   // -----
   EnclaveRequest<MyPojoData> request = new EnclaveRequest<>();
   request.setAction("action_to_execute");
   request.setData(myPojoData);

   EnclaveResponse<MyPojoDataResult> response = client.send(request);	

   if (response.getIsError()) {
	System.out.println(String.format("Something went wrong: %s", response.getError()));
	System.out.println(response.getErrorStacktrace());
   } else {
	System.out.println(response.getData().getValue());
   }

   System.out.println(String.format("Enclave execution time %sms", response.getDuration()));   
  }
}
```

# Parameters

```yaml
nitro:
  enclave:
    port: 5000
    network-mode: vsock # default is 'tcp'
    nsm-cli: /app/nsm-cli # default value, for NSM integration
    kmstool-enclave-cli: /app/kmstool_enclave_cli # default value, for KMS integration
```

# Tools / Libs

* [aws-nitro-enclaves-cli](https://docs.aws.amazon.com/enclaves/latest/user/nitro-enclave-cli-install.html)

* kmstool-enclave-cli & libnsm.so

 Build instructions at [kmstool-enclave-cli](https://github.com/aws/aws-nitro-enclaves-sdk-c/tree/main/bin/kmstool-enclave-cli).

* vsockj

This project uses the _vsockj_ library for vsock communication.
Build instructions at [vsockj](https://github.com/Cloud-Architects/vsockj).

* [nsm-cli](https://github.com/mrgatto/nsm-cli)

For interaction with the Nitro Security Module.


# Examples <a name="examples"></a>

* [Hello World Echo](examples/simple-echo)
* [KMS Decrypt](examples/kms-decrypt)
