FROM openjdk:11
COPY target/DiscoveryServer-0.0.1-SNAPSHOT.jar /tmp/DiscoveryServer.jar 
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/tmp/DiscoveryServer.jar"] 
