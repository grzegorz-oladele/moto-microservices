FROM maven:3.8.6-eclipse-temurin-17-focal AS MAVEN_BUILDER
COPY pom.xml /pom.xml
RUN mkdir src/
RUN echo "Creating main class in builder layer" > src/MotorcycleServiceApplication.java
RUN mvn compile
RUN rm -f target/*
COPY ./ ./
RUN mvn clean package -DskipTests

FROM openjdk:17.0.2-jdk-slim
COPY --from=MAVEN_BUILDER /target/motorcycle-service-0.0.1-SNAPSHOT.jar motorcycle-service-0.0.1-SNAPSHOT.jar
EXPOSE 8000
CMD ["java", "-jar", "motorcycle-service-0.0.1-SNAPSHOT.jar"]