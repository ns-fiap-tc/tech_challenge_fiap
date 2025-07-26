# # Remote backend instance, to save tfstate
# terraform {
#   backend "s3" {
#     bucket         = "lanchonete-tfstate-bucket"
#     key            = "tech-challenge-app/terraform.tfstate"
#     region         = "us-east-1"
#     dynamodb_table = "terraform-lock"
#     encrypt        = true
#   }
# }
