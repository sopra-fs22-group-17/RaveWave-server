FROM azul/zulu-openjdk:15.0.9-15.44.13
COPY . .
RUN mv /build/libs/soprafs22.jar RaveWave-server.jar
RUN chmod 777 RaveWave-server.jar
ENTRYPOINT ["java","-jar","/RaveWave-server.jar"]
