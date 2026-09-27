FROM dhi.io/eclipse-temurin:25-alpine-dev
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} project-gateway.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/project-gateway.jar"]
