FROM openjdk:17-jdk-alpine
MAINTAINER qixiaoling
COPY target/NightFoxReview-0.0.1-SNAPSHOT.jar NightFoxReview-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","NightFoxReview-0.0.1-SNAPSHOT.jar"]