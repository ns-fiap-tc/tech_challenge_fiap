resource "kubernetes_secret" "secrets-lanchonete" {
  metadata {
    name = "secrets-lanchonete"
  }

  type = "Opaque"

  data = {
    DB_HOST             = element(split(":",data.aws_db_instance.lanchonete_db.endpoint),0)
    DB_PORT             = var.db_port
    DB_NAME             = var.db_name
    DB_USER             = var.db_username
    DB_PASSWORD         = var.db_password
    PAGAMENTO_MOCK_HOST = kubernetes_service.service-pagamento-mock.metadata[0].name
    JWT_KEY_VALUE       = jsondecode(data.aws_secretsmanager_secret_version.jwt-secret-version.secret_string).jwt-key
  }

  lifecycle {
    prevent_destroy = false
  }
}

resource "kubernetes_secret" "secrets-dockerhub" {
  metadata {
    name = "secrets-dockerhub"
    #namespace = "default"
  }
  type = "kubernetes.io/dockerconfigjson"
  data = {
      ".dockerconfigjson" = jsonencode({
        auths = {
          "https://index.docker.io/v1/" = {
            auth = base64encode("${var.dockerhub_username}:${var.dockerhub_token}")
          }
        }
      })
  }
}

# LANCHONETE APP
resource "kubernetes_deployment" "deployment-lanchonete-app" {
  metadata {
    name      = "deployment-lanchonete-app"
    namespace = "default"
  }

  spec {
    selector {
      match_labels = {
        app = "deployment-lanchonete-app"
      }
    }

    template {
      metadata {
        labels = {
          app = "deployment-lanchonete-app"
        }
      }

      spec {
        // Prevent error:
        // 0/2 nodes are available: 2 node(s) were unschedulable. 
        // preemption: 0/2 nodes are available: 2 
        // Preemption is not helpful for scheduling.
        toleration {
          key      = "key"
          operator = "Equal"
          value    = "value"
          effect   = "NoSchedule"
        }

        image_pull_secrets {
          name = kubernetes_secret.secrets-dockerhub.metadata.0.name
        }

        container {
          name  = "deployment-lanchonete-app-container"
          image = "${var.dockerhub_username}/fiap-tech-challenge-lanchonete:latest"

          resources {
            requests = {
              memory : "512Mi"
              cpu : "500m"
            }
            limits = {
              memory = "1Gi"
              cpu    = "1"
            }
          }

          env_from {
            secret_ref {
              name = kubernetes_secret.secrets-lanchonete.metadata[0].name
            }
          }

          port {
            container_port = "8080"
          }
        }
      }
    }
  }

  depends_on = [kubernetes_deployment.messagequeue_deployment]
}

resource "kubernetes_service" "service-lanchonete-app" {
  metadata {
    name      = "service-lanchonete-app"
    namespace = "default"
    annotations = {
      "service.beta.kubernetes.io/aws-load-balancer-type" : "nlb",
      "service.beta.kubernetes.io/aws-load-balancer-scheme" : "internal",
      "service.beta.kubernetes.io/aws-load-balancer-cross-zone-load-balancing-enabled" : "true"
    }
  }
  spec {
    selector = {
      app = "deployment-lanchonete-app"
    }
    port {
      port = "80"
      target_port = "8080"
      node_port = "30001"
    }
    type = "LoadBalancer"
  }
}
