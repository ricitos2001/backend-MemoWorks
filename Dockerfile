# Stage de build
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /usr/src/app
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

# Stage de runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /usr/src/app/target/MemoWorks-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
