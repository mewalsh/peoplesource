FROM openjdk:8

EXPOSE 8080

RUN mkdir /app
WORKDIR /app

ARG JAR_FILE

COPY ${JAR_FILE} app.jar

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]