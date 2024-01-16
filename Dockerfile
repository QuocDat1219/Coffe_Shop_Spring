FROM openjdk:17
EXPOSE 8000
COPY target/com.inn.cafe-0.0.1-SNAPSHOT.jar springboot-coffe-shop.jar
ENTRYPOINT ["java","-jar","/springboot-coffe-shop.jar"]