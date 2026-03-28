# 🛠 Build Stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# 🚀 Runtime Stage
FROM eclipse-temurin:21-jdk AS runtime
WORKDIR /app
COPY --from=builder /app/target/ecomsoft-0.0.1-SNAPSHOT.jar .

EXPOSE 9091
ENTRYPOINT ["java", "-jar", "/app/ecomsoft-0.0.1-SNAPSHOT.jar"]


