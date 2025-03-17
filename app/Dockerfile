# Construção da aplicação (Somente para Produção)
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
ARG MODE=prod
# Se modo for Produção, builda aplicação com mvn, senão pula etapa
RUN if [ "$MODE" = "prod" ]; then \
    mvn -DskipTests -DskipITs=true -N clean install && \
    mvn -DskipTests clean package; \
fi

# Criando a imagem final
FROM eclipse-temurin:17.0.13_11-jre-noble
WORKDIR /service

# Define o modo de execução (Esse argumento MODE está no docker-compose raiz do projeto)
ARG MODE=prod
ENV MODE=$MODE

# No modo produção, copia o .JAR gerado na etapa anterior
# No modo desenvolvimento, assume que o .JAR já está no diretório target/
COPY --from=builder /app/target/*.jar ./app.jar || COPY target/*.jar ./app.jar

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
CMD if [ "$MODE" = "dev" ]; then tail -f /dev/null; else java -jar ./app.jar; fi