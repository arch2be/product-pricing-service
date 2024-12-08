FROM openjdk:21-jdk-slim AS build
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN ./gradlew build -x test

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]