# Stage 1: Build the application
FROM openjdk:17-slim as build
WORKDIR /app

COPY . .

# Give execute permission to gradlew
RUN chmod +x ./gradlew

RUN ./gradlew clean bootJar

# Stage 2: Create the final image
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/build/libs/ChatRoom-0.0.1.jar ChatRoom-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/ChatRoom-0.0.1.jar"]
