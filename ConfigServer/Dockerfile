FROM dhi.io/eclipse-temurin:25-alpine-dev
RUN apk --no-cache add curl
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} config-server.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/config-server.jar"]
