# simple-echo example

## Build Jars

Ensure framework library jars is in your local Maven repo (from base project dir):

```bash
mvn install
```

Package this sample application:

```bash
# from examples/simple-echo
mvn package
```

## Build Enclave image

```bash
docker build ./simple-echo-enclave -t simple-echo-enclave
nitro-cli build-enclave --docker-uri simple-echo-enclave:latest --output-file simple-echo-enclave.eif
```

## Run Enclave image

```bash
nitro-cli terminate-enclave --all
nitro-cli run-enclave --eif-path simple-echo-enclave.eif --memory 2048 --cpu-count 2 --enclave-cid 5 --debug-mode
```

### debug-mode Console

```bash
nitro-cli console --enclave-id $(nitro-cli describe-enclaves | jq -r '.[0].EnclaveID')
```

## Run Host App

```bash
CID=5 java -jar simple-echo-host/target/nitro-enclaves-simple-echo-host-1.0.0-SNAPSHOT.jar
```

You should see the following output, where **i-09eb...** is the Nitro Module ID:

```bash
Echo from Enclave i-09eb1f8c065b7f2e8-enc017dfd281b8c1930: Hello World!
```

