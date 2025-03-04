FROM eclipse-temurin:17.0.13_11-jre-noble

WORKDIR /service

COPY target/*.jar ./app.jar

ARG PROFILE=prod

ENV SPRING_PROFILES_ACTIVE=$PROFILE
ENV DB_NAME=lanchdb
ENV DB_USER=lanchuser
ENV DB_PASSWORD=lanch@1234
ENV DB_HOST=dbserver
ENV DB_PORT=5432

CMD ["java", "-jar", "./app.jar"]