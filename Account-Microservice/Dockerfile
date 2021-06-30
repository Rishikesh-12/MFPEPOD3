FROM openjdk:11
COPY target/Account-Microservice-0.0.1-SNAPSHOT.jar /tmp/Account-Microservice.jar 
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tmp/Account-Microservice.jar"] 
