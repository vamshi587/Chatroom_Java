FROM openjdk:17
EXPOSE 8080:80
ARG JAR_FILE=build/libs/ChatRoom-0.0.1.jar
COPY ${JAR_FILE} ChatRoom-0.0.1.jar
ENTRYPOINT ["java","-jar","/ChatRoom-0.0.1.jar"]