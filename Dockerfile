FROM eclipse-temurin:17-jdk-focal as build

WORKDIR /build

COPY .mvn/ ./.mvn
COPY mvnw pom.xml  ./
RUN ./mvnw dependency:go-offline

COPY . .
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /com.inn.cafe/target/com.inn.cafe-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app/run.jar"]
