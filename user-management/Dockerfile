FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY user-management/pom.xml user-management/pom.xml
COPY user-management/.mvn user-management/.mvn
COPY user-management/mvnw user-management/mvnw

WORKDIR /app/user-management
RUN ./mvnw dependency:go-offline -B

WORKDIR /app
COPY user-management/src ./user-management/src

WORKDIR /app/user-management
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/user-management/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]