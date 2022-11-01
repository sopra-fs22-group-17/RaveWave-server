FROM openjdk:15-jdk-alpine
COPY /build/libs/soprafs22.jar RaveWave.jar
RUN chmod 777 RaveWave.jar
ENTRYPOINT ["java","-jar","/RaveWave.jar"]
