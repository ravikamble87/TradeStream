# Build context is the repo root, so this Dockerfile can see the parent POM
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:21-jre
WORKDIR /app

RUN addgroup --system tradestream && adduser --system --ingroup tradestream tradestream
COPY --from=build /app/target/*.jar app.jar
RUN chown tradestream:tradestream app.jar
USER tradestream

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]