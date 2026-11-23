# ---------- Build stage ----------
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy Maven wrapper + pom first for better layer caching
COPY pom.xml .
COPY src ./src

# Install Maven, build the jar, skip tests (they'll run in CI later)
RUN apk add --no-cache maven && mvn clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy only the built jar from the previous stage
COPY --from=build /app/target/cybersecure-platform-0.0.1-SNAPSHOT.jar app.jar

# Document the port (doesn't actually publish it — that's -p on run)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]