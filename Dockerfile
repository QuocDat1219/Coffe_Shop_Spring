FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/com.inn.cafe-0.0.1-SNAPSHOT.jar springboot-coffe-shop.jar

ENTRYPOINT ["java","-jar","/springboot-coffe-shop.jar"]
