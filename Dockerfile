FROM openjdk:15-jdk-alpine
COPY . /operator
WORKDIR /operator
ENTRYPOINT ["java","-jar","/operator/build/libs/soprafs22.jar"]
