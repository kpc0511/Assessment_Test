FROM openjdk:19-jdk-alpine
MAINTAINER maybank.com.my
COPY target/maybank-rest-api.jar maybank-rest-api.jar
ENTRYPOINT ["java","-Xms128m","-Xmx256m","-Duser.timezone=GMT+8:00","-jar","/maybank-rest-api.jar"]