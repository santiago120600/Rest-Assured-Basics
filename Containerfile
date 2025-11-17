FROM maven:4.0.0-rc-5-eclipse-temurin-25-alpine

WORKDIR /workspace

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

ENTRYPOINT ["mvn", "test"]