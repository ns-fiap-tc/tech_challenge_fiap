@echo off
echo 🚀 Iniciando setup da infraestrutura Kubernetes...

REM Verifica se o arquivo .env existe
if not exist .env (
    echo ❌ Arquivo .env não encontrado! Crie um arquivo .env com as variáveis necessárias.
    exit /b 1
)

REM Carrega as variáveis do .env
echo 🔍 Carregando variáveis do .env...
for /f "tokens=1* delims==" %%a in (.env) do (
    set %%a=%%b
)

echo 🚀 Iniciando Minikube...
minikube start

echo 📦 Aplicando os manifestos das Secrets...
kubectl apply -f k8s/secrets/

echo 🔑 Criando Secrets com valores do .env...
kubectl delete secret app-secret --ignore-not-found
kubectl delete secret postgres-secret --ignore-not-found
kubectl delete secret rabbitmq-secret --ignore-not-found
kubectl delete secret mock-pagamento-secret --ignore-not-found
kubectl delete secret dockerhub-secret --ignore-not-found

kubectl create secret generic app-secret ^
    --from-literal=DB_NAME="%DB_NAME%" ^
    --from-literal=DB_USER="%DB_USER%" ^
    --from-literal=DB_PASSWORD="%DB_PASSWORD%"

kubectl create secret generic postgres-secret ^
    --from-literal=DB_USER="%DB_USER%" ^
    --from-literal=DB_PASSWORD="%DB_PASSWORD%"

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

echo 📦 Aplicando manifestos do Banco e do RabbitMQ...
kubectl apply -f k8s/postgres/
kubectl apply -f k8s/rabbitmq/
timeout /t 2 /nobreak >nul

echo ⏳ Aguardando Banco e RabbitMQ ficarem prontos...
kubectl wait --for=condition=ready pod -l app=postgres --timeout=180s
kubectl wait --for=condition=ready pod -l app=messagequeue --timeout=180s

echo 📦 Iniciando os Apps...
kubectl apply -f k8s/app/
kubectl apply -f k8s/pagamento-mock/
timeout /t 2 /nobreak >nul

echo ⏳ Aguardando App ficar pronto...
kubectl wait --for=condition=ready pod -l app=lanchonete-app --timeout=180s
kubectl wait --for=condition=ready pod -l app=mock-pagamento --timeout=180s

echo 🌐 Configurando portas para acesso aos serviços...
start /b kubectl port-forward svc/app-service 8080:80
start /b kubectl port-forward svc/mock-pagamento-service 8081:8081

echo ✅ Infraestrutura pronta! Acesse os serviços:
echo     - App Lanchonete: http://localhost:8080
echo     - Mock Pagamento (Auxiliar do Lanchonete): http://localhost:8081