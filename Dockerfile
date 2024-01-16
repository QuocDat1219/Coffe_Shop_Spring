FROM openjdk:17
EXPOSE 8000
ADD target/springboot-coffe-shop.jar springboot-coffe-shop.jar
ENTRYPOINT ["java","-jar","/springboot-coffe-shop.jar"]