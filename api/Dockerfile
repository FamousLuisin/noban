FROM eclipse-temurin:21-jre-alpine
COPY target/*.jar api.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "api.jar" ]