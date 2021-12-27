# simple-echo example

## Build Jars

Ensure framework library jars is in your local Maven repo (from base project dir):

```shell script
mvn install
```

Package this sample application:

```shell script
# from examples/simple-echo/simple-echo-enclave
mvn package
```

## Build Enclave image

```shell script
# from examples/simple-echo/simple-echo-enclave
docker build . -t simple-echo-enclave
nitro-cli build-enclave --docker-uri simple-echo-enclave:latest --output-file simple-echo-enclave.eif
```

## Run Enclave image

```shell script
nitro-cli terminate-enclave --all
nitro-cli run-enclave --eif-path simple-echo-enclave.eif --memory 2048 --cpu-count 2 --debug-mode
```

### debug-mode Console

```shell script
nitro-cli console --enclave-id $(nitro-cli describe-enclaves | jq -r '.[0].EnclaveID')
```

## Run Host App

```shell script
# from examples/simple-echo/simple-echo-host
ECID=$(nitro-cli describe-enclaves | jq -r '.[0].EnclaveCID')
CID=$ECID java -jar target/nitro-enclaves-simple-echo-host-1.0.0-SNAPSHOT.jar
```


