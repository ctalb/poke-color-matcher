FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
EXPOSE 8080
COPY --from=build /app/target/*.jar poke-floss-matcher.jar
ENTRYPOINT ["java", "-jar", "poke-floss-matcher.jar"]
