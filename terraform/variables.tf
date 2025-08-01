# AWS provider configuration
variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "us-east-1"
}

# Database configuration
variable "db_lanchonete_username" {
  description = "The username for the RDS instance"
  type        = string
  sensitive   = true
}

variable "db_lanchonete_password" {
  description = "The password for the RDS instance"
  type        = string
  sensitive   = true
}

variable "db_lanchonete_name" {
  description = "Database lanchonete name"
  type        = string
  default     = "lanchdb"
}

variable "db_lanchonete_identifier" {
  description = "The identifier for the RDS instance"
  type        = string
  default     = "lanchonete-db"
}

variable "db_lanchonete_port" {
  description = "The port for the RDS instance"
  type        = string
  default     = "5432"
}

#Variaveis DockerHUB

variable "dockerhub_username" {
  description = "The username of the dockerhub image to deploy"
  type        = string
}

variable "dockerhub_token" {
  description = "The access token of the dockerhub image to deploy"
  type        = string
}