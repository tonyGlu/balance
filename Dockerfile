FROM openjdk:8 
ADD target/balance-docker.jar balance-docker.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "balance-docker.jar"]