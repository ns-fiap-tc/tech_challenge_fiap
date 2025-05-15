# AWS provider configuration
variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "us-east-1"
}

# Database configuration
variable "db_username" {
  description = "The username for the RDS instance"
  type        = string
  sensitive   = true
  default     = "lanchuser"
}

variable "db_password" {
  description = "The password for the RDS instance"
  type        = string
  sensitive   = true
  default     = ""
}

variable "db_name" {
  description = "Database name"
  type        = string
  default     = "lanchdb"
}

variable "db_identifier" {
  description = "The identifier for the RDS instance"
  type        = string
  default     = "lanchonete-db"
}

variable "db_port" {
  description = "The identifier for the database port"
  type        = string
  default     = "5432"
}

#Variaveis DockerHUB

variable "dockerhub_username" {
  description = "The username of the dockerhub image to deploy"
  type        = string
  default     = ""
}

variable "dockerhub_token" {
  description = "The access token of the dockerhub image to deploy"
  type        = string
  default     = ""
}