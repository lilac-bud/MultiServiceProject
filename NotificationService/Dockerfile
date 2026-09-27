FROM dhi.io/eclipse-temurin:25-alpine-dev
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} notif-service.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/notif-service.jar"]
