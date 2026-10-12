FROM dhi.io/eclipse-temurin:25-alpine-dev
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} user-service.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/user-service.jar"]
