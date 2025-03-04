FROM eclipse-temurin:17.0.13_11-jre-noble

WORKDIR /service

COPY target/*.jar ./app.jar

ARG PROFILE=prod

ENV SPRING_PROFILES_ACTIVE=$PROFILE

CMD ["java", "-jar", "./app.jar"]