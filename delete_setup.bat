@echo off

echo "📦 Deletando manifestos Kubernetes..."

kubectl delete secret app-secret --ignore-not-found
kubectl delete secret postgres-secret --ignore-not-found
kubectl delete secret rabbitmq-secret --ignore-not-found
kubectl delete secret mock-pagamento-secret --ignore-not-found
kubectl delete secret dockerhub-secret --ignore-not-found

kubectl delete -f k8s/app/
kubectl delete -f k8s/pagamento-mock/
kubectl delete -f k8s/postgres/
kubectl delete -f k8s/rabbitmq/

echo "✅ Infraestrutura deletada!"
