# Build da aplicação (Somente para Produção)
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY . /build/

# Se modo for Produção, builda aplicação com mvn, senão pula etapa
ARG MODE=prod
RUN if [ "$MODE" = "prod" ]; then \
    mvn -N -DskipTests install && \
    mvn -DskipTests -DskipITs=true install && \
    mvn -DskipTests clean package -pl app -am; \
fi

# Runtime
FROM eclipse-temurin:17.0.13_11-jre-noble
WORKDIR /service

ARG MODE=prod
ARG PROFILE=prod

ENV MODE=$MODE
ENV SPRING_PROFILES_ACTIVE=$PROFILE

# Modo produção: copia o jar buildado no stage anterior
COPY --from=builder /build/app/target/*.jar ./app.jar

# Modo desenvolvimento: tenta copiar do target local se existir
# Isso só funciona se você já tiver buildado a aplicação com `mvn package`
COPY target/*.jar ./app.jar

# Entrypoint universal (prod ou dev)
CMD if [ -f "./app.jar" ]; then \
      java -jar ./app.jar ; \
    else \
      echo "❌ Nenhum app.jar encontrado. Certifique-se de que a aplicação foi buildada!" && exit 1 ; \
    fi