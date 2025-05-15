terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.46"
    }
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.32.0"
    }
  }

  required_version = ">= 1.2.0"
}

provider "aws" {
  region = var.aws_region
}

provider "kubernetes" {
  host                   = data.aws_eks_cluster.lanchonete_cluster.endpoint
  cluster_ca_certificate = base64decode(data.aws_eks_cluster.lanchonete_cluster.certificate_authority[0].data)
  token                  = data.aws_eks_cluster_auth.lanchonete_cluster_auth.token
}