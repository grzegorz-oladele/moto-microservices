FROM maven:3.8.6-amazoncorretto-17 AS MAVEN_BUILDER
COPY pom.xml /pom.xml
RUN mkdir src/
RUN echo "Creating main class in builder layer" > src/EurekaServiceApplication.java
RUN mvn compile
RUN rm -f target/*
COPY ./ ./
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-al2-jdk
COPY --from=MAVEN_BUILDER /target/eureka-service-0.0.1-SNAPSHOT.jar eureka-service-0.0.1-SNAPSHOT.jar
EXPOSE 8761
CMD ["java", "-jar", "eureka-service-0.0.1-SNAPSHOT.jar"]