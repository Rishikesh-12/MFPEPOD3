FROM openjdk:11
COPY target/Authentication-Microservice-0.0.1-SNAPSHOT.jar /tmp/Authentication-Microservice.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tmp/Authentication-Microservice.jar"] 
