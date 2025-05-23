#!/bin/bash

# Carrega as variáveis do arquivo .env
if [ -f .env ]; then
    export $(grep -v '^#' .env | xargs)
else
    echo "[terraform] Erro: Arquivo .env não encontrado."
    exit 1
fi

# Verifica se o método foi passado como argumento
if [ -z "$1" ]; then
    echo "[terraform] Erro: Nenhum método especificado (plan, apply, etc.)."
    exit 1
fi

METHOD=$1
shift

PARAMS="$@"

terraform $METHOD $PARAMS \
-var "aws_region=$AWS_REGION" \
-var "db_username=$DB_USERNAME" \
-var "db_password=$DB_PASSWORD" \
-var "db_identifier=$DB_IDENTIFIER" \
-var "db_name=$DB_NAME" \
-var "db_port=$DB_PORT" \
-var "dockerhub_username=$DOCKERHUB_USERNAME" \
-var "dockerhub_token=$DOCKERHUB_ACCESS_TOKEN" 