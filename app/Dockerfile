# Construção da aplicação (Somente para Produção)
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY . /build/

ARG MODE=prod

# Se modo for Produção, builda aplicação com mvn, senão pula etapa
RUN if [ "$MODE" = "prod" ]; then \
    mvn -N -DskipTests install && \
    mvn -DskipTests -DskipITs=true install && \
    mvn -DskipTests clean package -pl app -am; \
fi

# Criando a imagem final
FROM eclipse-temurin:17.0.13_11-jre-noble
WORKDIR /service

# Define o modo de execução (Esse argumento MODE está no docker-compose raiz do projeto)
ARG MODE=prod
ENV MODE=$MODE

# No modo produção, copia o JAR gerado no estágio anterior
COPY --from=builder /build/app/target/*.jar /service/app.jar

# No modo desenvolvimento, caso o JAR não tenha sido gerado no build anterior, copia manualmente
COPY target/*.jar /service/app.jar

# Define o profile ativo
ARG PROFILE=prod
ENV SPRING_PROFILES_ACTIVE=$PROFILE

# Variáveis de ambiente do banco de dados
ENV DB_NAME=${DB_NAME}
ENV DB_USER=${DB_USER}
ENV DB_PASSWORD=${DB_PASS}
ENV DB_HOST=dbserver
ENV DB_PORT=5432

# O MODE dev mantém o container ativo para hot-reload
CMD ["java", "-jar", "./app.jar"]