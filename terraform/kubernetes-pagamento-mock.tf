# resource "kubernetes_config_map" "configmap-pagamento-mock" {
#   metadata {
#     name = "configmap-pagamento-mock"
#   }

#   data = {
#     LANCHONETE_APP_HOST =  kubernetes_service.service-lanchonete-app.metadata[0].name
#   }

#   lifecycle {
#     prevent_destroy = false
#   }
# }

# # PAGAMENTO MOCK
# resource "kubernetes_deployment" "deployment-pagamento-mock" {
#   metadata {
#     name      = "deployment-pagamento-mock"
#     namespace = "default"
#   }

#   spec {
#     selector {
#       match_labels = {
#         app = "deployment-pagamento-mock"
#       }
#     }

#     template {
#       metadata {
#         labels = {
#           app = "deployment-pagamento-mock"
#         }
#       }

#       spec {
#         toleration {
#           key      = "key"
#           operator = "Equal"
#           value    = "value"
#           effect   = "NoSchedule"
#         }

#         container {
#           name  = "deployment-pagamento-mock-container"
#           image = "${var.dockerhub_username}/fiap-tech-challenge-lanchonete-mock-pagamento:latest"

#           resources {
#             requests = {
#               memory : "512Mi"
#               cpu : "500m"
#             }
#             limits = {
#               memory = "1Gi"
#               cpu    = "1"
#             }
#           }

#           env_from {
#             config_map_ref {
#               name = kubernetes_config_map.configmap-pagamento-mock.metadata[0].name
#             }
#           }

#           port {
#             container_port = "8080"
#           }
#         }
#       }
#     }
#   }
# }

# resource "kubernetes_service" "service-pagamento-mock" {
#   metadata {
#     name      = "service-pagamento-mock"
#     namespace = "default"
#     annotations = {
#       "service.beta.kubernetes.io/aws-load-balancer-type" : "nlb",
#       "service.beta.kubernetes.io/aws-load-balancer-scheme" : "internal",
#       "service.beta.kubernetes.io/aws-load-balancer-cross-zone-load-balancing-enabled" : "true"
#     }
#   }
#   spec {
#     selector = {
#       app = "deployment-pagamento-mock"
#     }
#     port {
#       port = "8081"
#       target_port = "8080"
#       node_port = "30002"
#     }
#     type = "LoadBalancer"
#   }
# }
