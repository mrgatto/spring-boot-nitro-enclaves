FROM amazoncorretto:8u302

WORKDIR /app

COPY target/nitro-enclaves-simple-echo-enclave-1.0.0-SNAPSHOT.jar ./app.jar

COPY libs/libvsockj-native-1.0-SNAPSHOT.so /usr/lib/
COPY libs/nsm-cli ./nsm-cli

CMD ["java", "-jar", "/app/app.jar"]
