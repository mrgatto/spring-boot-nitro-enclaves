FROM amazoncorretto:8u302

WORKDIR /app

COPY target/nitro-enclaves-simple-echo-enclave-1.0.0-SNAPSHOT.jar ./app.jar
COPY run.sh ./run.sh

COPY lib/libvsockj-native-1.0-SNAPSHOT.so /usr/lib/

RUN chmod +x /app/run.sh

CMD ["java", "-jar", "/app/app.jar"]
