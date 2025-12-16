# Multi-stage Dockerfile for MemoWorks
# Build stage
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package -DskipITs

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copy built jar (assumes single jar in target)
COPY --from=build /workspace/target/*.jar app.jar

# JVM options tuned for container
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=80.0 -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]

