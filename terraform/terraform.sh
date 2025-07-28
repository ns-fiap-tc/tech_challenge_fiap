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
-var "db_lanchonete_username=$DB_LANCHONETE_USERNAME" \
-var "db_lanchonete_password=$DB_LANCHONETE_PASSWORD" \
-var "db_lanchonete_identifier=$DB_LANCHONETE_IDENTIFIER" \
-var "db_lanchonete_name=$DB_LANCHONETE_NAME" \
-var "db_lanchonete_port=$DB_LANCHONETE_PORT" \
-var "dockerhub_username=$DOCKERHUB_USERNAME" \
-var "dockerhub_token=$DOCKERHUB_ACCESS_TOKEN" 