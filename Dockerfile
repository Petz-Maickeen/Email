FROM adoptopenjdk/openjdk11:latest
MAINTAINER baeldung.com

ENV DB_USER=maickeen \
    DB_PASSWORD=petz2021

COPY target/email-0.0.1-SNAPSHOT.jar email-1.0.0.jar
ENTRYPOINT ["java","-jar","/email-1.0.0.jar"]