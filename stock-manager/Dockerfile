FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} stock-manager.jar
ENTRYPOINT ["java","-jar","/stock-manager.jar"]