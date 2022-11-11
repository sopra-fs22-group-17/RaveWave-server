FROM azul/zulu-openjdk:15.0.9-15.44.13

RUN --mount=type=secret,id=clientSecret \
  cat /run/secrets/clientSecret
RUN --mount=type=secret,id=redirectURL \
  cat /run/secrets/redirectURL
RUN --mount=type=secret,id=SPRING_DATASOURCE_PASSWORD \
  cat /run/secrets/SPRING_DATASOURCE_PASSWORD
RUN --mount=type=secret,id=SPRING_DATASOURCE_URL \
  cat /run/secrets/SPRING_DATASOURCE_URL
RUN --mount=type=secret,id=SPRING_DATASOURCE_USERNAME \
  cat /run/secrets/SPRING_DATASOURCE_USERNAME

COPY . .
RUN mv /build/libs/soprafs22.jar RaveWave-server.jar
RUN chmod 777 RaveWave-server.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/RaveWave-server.jar"]
