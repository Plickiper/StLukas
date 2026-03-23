# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only pom files first to cache dependencies as a separate layer
COPY pom.xml ./pom.xml
COPY appodata/pom.xml appodata/pom.xml
COPY appoms/pom.xml appoms/pom.xml
COPY sbappoms/pom.xml sbappoms/pom.xml

# Download all dependencies — this layer is cached until pom.xml changes
RUN mvn dependency:go-offline -q

# Now copy source code and build
COPY appodata/ appodata/
COPY appoms/ appoms/
COPY sbappoms/ sbappoms/
RUN mvn install -DskipTests -q

# Stage 2: Runtime — slim JRE image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/sbappoms/target/sbappoms-1.0-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
