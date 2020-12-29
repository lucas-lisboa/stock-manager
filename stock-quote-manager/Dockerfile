FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} stock-quote-manager.jar
ENTRYPOINT ["java","-jar","/stock-quote-manager.jar"]