data "aws_db_instance" "lanchonete_db" {
  db_instance_identifier = var.db_lanchonete_identifier
}

data "aws_eks_cluster" "lanchonete_cluster" {
  name = "lanchonete_cluster"
}

data "aws_eks_cluster_auth" "lanchonete_cluster_auth" {
  name = data.aws_eks_cluster.lanchonete_cluster.name
}

data "aws_secretsmanager_secret" "jwt-secret-key" {
  name = "jwt-secret-key" 
}

data "aws_secretsmanager_secret_version" "jwt-secret-version" {
  secret_id = data.aws_secretsmanager_secret.jwt-secret-key.id
}

data "kubernetes_service" "service-ms-categoria" {
  metadata {
    name      = "service-ms-categoria"
    namespace = "default"
  }
}

data "kubernetes_service" "service-ms-pagamento" {
  metadata {
    name      = "service-ms-pagamento"
    namespace = "default"
  }
}

data "kubernetes_service" "service-ms-produto" {
  metadata {
    name      = "service-ms-produto"
    namespace = "default"
  }
}