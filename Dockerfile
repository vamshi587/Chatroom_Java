FROM openjdk:17
CMD ["./gradlew", "clean", "bootJar"]
COPY ./libs/ChatRoom-0.0.1.jar ChatRoom-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/ChatRoom-0.0.1.jar"]