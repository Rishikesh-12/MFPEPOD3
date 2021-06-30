FROM openjdk:11
COPY target/Transaction-Microservice-0.0.1-SNAPSHOT.jar /tmp/Transaction-Microservice.jar 
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tmp/Transaction-Microservice.jar"] 
