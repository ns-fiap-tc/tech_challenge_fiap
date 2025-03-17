#!/bin/bash

echo "📦 Deletando manifestos Kubernetes..."
kubectl delete -f k8s/app/
kubectl delete -f k8s/pagamento-mock/
kubectl delete -f k8s/postgres/
kubectl delete -f k8s/rabbitmq/
kubectl delete secret dockerhub-secret

echo "✅ Infraestrutura deletada!"
