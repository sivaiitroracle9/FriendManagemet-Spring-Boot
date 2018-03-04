FROM openjdk:8

RUN mkdir -p /spgroup/friend-management
COPY ./target/application-0.0.1-SNAPSHOT.jar /spgroup/friend-management/application-0.0.1-SNAPSHOT.jar
RUN chmod 777 /spgroup/friend-management/application-0.0.1-SNAPSHOT.jar
ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom -jar /spgroup/friend-management/application-0.0.1-SNAPSHOT.jar