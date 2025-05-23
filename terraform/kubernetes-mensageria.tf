# Deployment do RabbitMQ
resource "kubernetes_deployment" "messagequeue_deployment" {
  metadata {
    name = "messagequeue"
  }
  spec {
    replicas = 1
    selector {
      match_labels = {
        app = "messagequeue"
      }
    }
    template {
      metadata {
        labels = {
          app = "messagequeue"
        }
      }
      spec {
        container {
          name  = "messagequeue"
          image = "rabbitmq:4.0.5-management-alpine"
          port {
            container_port = 5672
          }
          port {
            container_port = 15672
          }
        }
      }
    }
  }
}

# Serviço do RabbitMQ
resource "kubernetes_service" "messagequeue_service" {
  metadata {
    name = "messagequeue"
  }
  spec {
    selector = {
      app = "messagequeue"
    }
    port {
      name        = "service"
      protocol    = "TCP"
      port        = 5672
      target_port = 5672
    }
    port {
      name        = "web"
      protocol    = "TCP"
      port        = 15672
      target_port = 15672
    }
  }
}