FROM openjdk:11
COPY target/Rules-Microservice-0.0.1-SNAPSHOT.jar /tmp/Rules-Microservice.jar 
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tmp/Rules-Microservice.jar"] 
