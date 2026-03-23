# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only pom files first to cache dependencies as a separate layer
COPY pom.xml ./pom.xml
COPY doctdata/pom.xml doctdata/pom.xml
COPY doctms/pom.xml doctms/pom.xml
COPY sbdoctms/pom.xml sbdoctms/pom.xml

# Download all dependencies — this layer is cached until pom.xml changes
RUN mvn dependency:go-offline -q

# Now copy source code and build
COPY doctdata/ doctdata/
COPY doctms/ doctms/
COPY sbdoctms/ sbdoctms/
RUN mvn install -DskipTests -q

# Stage 2: Runtime — slim JRE image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/sbdoctms/target/sbdoctms-1.0-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
