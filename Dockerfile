FROM openjdk:8-jdk-alpine
ADD /target/cake-manager-1.0.0.jar cake-manager.jar
EXPOSE 8282
ENTRYPOINT ["java","-jar","cake-manager.jar"]