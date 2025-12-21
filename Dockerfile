# Stage de build
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /usr/src/app
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

# Stage de runtime
FROM eclipse-temurin:21-jre-alpine
ARG JAR_FILE=target/MemoWorks-0.0.1-SNAPSHOT.jar
WORKDIR /app
COPY --from=builder /usr/src/app/${JAR_FILE} app.jar
ENV JAVA_TOOL_OPTIONS="-Xms256m -Xmx512m -XX:+UseG1GC"
ENV PORT 8080
EXPOSE 8080
CMD ["sh", "-c", "java $JAVA_TOOL_OPTIONS -jar /app/app.jar --server.port=$PORT"]
ENTRYPOINT ["java","-jar","app.jar"]






