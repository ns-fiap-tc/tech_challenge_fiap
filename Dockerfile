# Build da aplicação (Somente para Produção)
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY . /build/

# Se modo for Produção, builda aplicação com mvn, senão pula etapa
ARG MODE=prod
RUN if [ "$MODE" = "prod" ]; then \
    mvn clean install -DskipTests ; \
fi

# Runtime
FROM eclipse-temurin:17.0.13_11-jre-noble
WORKDIR /service

ARG MODE=prod
ARG PROFILE=prod

ENV MODE=$MODE
ENV SPRING_PROFILES_ACTIVE=$PROFILE

# Produção: copia o jar buildado no stage anterior
COPY --from=builder /build/app/target/*.jar ./app.jar

# Entrada condicional: roda se o .jar existe (prod ou dev)
CMD if [ -f "./app.jar" ]; then \
      java -jar ./app.jar ; \
    elif [ -f "./target/app.jar" ]; then \
      java -jar ./target/app.jar ; \
    else \
      echo "❌ Nenhum app.jar encontrado. Primeiro faça o Build da aplicação." && exit 1 ; \
    fi