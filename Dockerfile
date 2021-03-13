FROM openjdk:11-jdk
COPY ./target/ProjectManagement-0.0.1-SNAPSHOT.jar  /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch ProjectManagement-0.0.1-SNAPSHOT.jar'
EXPOSE 8080
ENTRYPOINT ["java","-jar","ProjectManagement-0.0.1-SNAPSHOT.jar"]