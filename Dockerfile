FROM eclipse-temurin:21
LABEL maintainer="Vinayak Warad"
ADD target/dockTest-0.0.1-SNAPSHOT.jar dockTest.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","dockTest.jar"]

