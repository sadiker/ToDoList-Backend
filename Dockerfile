FROM openjdk:17-jdk
ADD target/springboot-mongo-docker.jar application.jar
ENTRYPOINT ["java","-jar","/application.jar"]