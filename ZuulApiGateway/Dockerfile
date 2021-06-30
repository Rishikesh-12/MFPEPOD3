FROM openjdk:11
COPY target/ZullApiGateway-0.0.1-SNAPSHOT.jar /tmp/ZullApiGateway.jar 
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tmp/ZullApiGateway.jar"] 
