# -------- Stage 1: Build the application --------
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy Maven config first (for dependency caching)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the jar
RUN mvn clean package -DskipTests


# -------- Stage 2: Run the application --------
FROM eclipse-temurin:17-jdk AS runner

WORKDIR /app

# Copy jar from builder stage
COPY --from=builder /app/target/billing-service-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot port
EXPOSE 4001
EXPOSE 9001

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]