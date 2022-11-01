FROM openjdk:15-jdk-alpine
COPY . .
RUN mv /build/libs/soprafs22.jar RaveWave-server.jar
RUN chmod 777 RaveWave-server.jar
ENTRYPOINT ["java","-jar","/RaveWave-server.jar"]
