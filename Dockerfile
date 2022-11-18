
FROM openjdk:11
EXPOSE 8080

RUN apt-get update && \
 apt-get install -y netcat;

COPY /target/mini-autorizador-0.0.1-SNAPSHOT.jar /app/mini_autorizador.jar
COPY /wait-for-mysql.sh /app/wait-for-mysql.sh
WORKDIR /app