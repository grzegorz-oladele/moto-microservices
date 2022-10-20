FROM maven:3.8.6-amazoncorretto-17 AS MAVEN_BUILDER
COPY pom.xml /pom.xml
RUN mkdir src/
RUN echo "Creating main class in builder layer" > src/GatewayApplication.java
RUN mvn compile
RUN rm -f target/*
COPY ./ ./
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-al2-jdk
COPY --from=MAVEN_BUILDER /target/gateway-0.0.1-SNAPSHOT.jar gateway-0.0.1-SNAPSHOT.jar
EXPOSE 9000
CMD ["java", "-jar", "gateway-0.0.1-SNAPSHOT.jar"]