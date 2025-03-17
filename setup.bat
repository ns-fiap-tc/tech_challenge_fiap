@echo off
echo  Iniciando setup da infraestrutura Kubernetes...

REM  Carregar variáveis do arquivo .env
if exist .\.env (
    for /f "tokens=1,2 delims==" %%a in (.\.env) do (
        set %%a=%%b
    )
) else (
    echo ⚠️ Arquivo .env não encontrado! Crie um com base no .env.example.
    exit /b 1
)

echo  Iniciando Minikube...
minikube start

echo  Criando Secrets no Kubernetes...
kubectl create secret generic app-secret ^
    --from-literal=DB_NAME="%DB_NAME%" ^
    --from-literal=DB_USER="%DB_USER%" ^
    --from-literal=DB_PASS="%DB_PASS%" ^
    --from-literal=RABBITMQ_USER="%RABBITMQ_USER%" ^
    --from-literal=RABBITMQ_PASSWORD="%RABBITMQ_PASSWORD%"

kubectl create secret generic postgres-secret ^
    --from-literal=DB_USER="%DB_USER%" ^
    --from-literal=DB_PASS="%DB_PASS%"

kubectl create secret generic rabbitmq-secret ^
    --from-literal=RABBITMQ_USER="%RABBITMQ_USER%" ^
    --from-literal=RABBITMQ_PASSWORD="%RABBITMQ_PASSWORD%"

kubectl create secret generic mock-pagamento-secret ^
    --from-literal=MOCK_PAGAMENTO_URL="%MOCK_PAGAMENTO_URL%"

kubectl create secret docker-registry dockerhub-secret ^
    --docker-server=https://index.docker.io/v1/ ^
    --docker-username="%DOCKERHUB_USERNAME%" ^
    --docker-password="%DOCKERHUB_ACCESS_TOKEN%" ^
    --docker-email="%DOCKERHUB_EMAIL%"

echo  Aplicando manifestos Kubernetes...
kubectl apply -f k8s/app/
kubectl apply -f k8s/pagamento-mock/
kubectl apply -f k8s/postgres/
kubectl apply -f k8s/rabbitmq/

echo ⏳ Aguardando os pods subirem...
kubectl wait --for=condition=ready pod --all --timeout=180s

echo  Configurando portas para acesso aos serviços...
start "" kubectl port-forward svc/app-service 8080:80
start "" kubectl port-forward svc/mock-pagamento-service 8081:8081

echo ✅ Infraestrutura pronta! Acesse os serviços:
echo     - App Lanchonete: http://localhost:8080
echo     - Mock Pagamento (Auxiliar do Lanchonete): http://localhost:8081