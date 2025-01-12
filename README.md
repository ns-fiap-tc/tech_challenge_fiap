# FIAP Tech Challenge

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)


## About 

Tech Challenge do curso Software Architecture da FIAP. 

Aplicação desenvolvida utilizando arquitetura hexagonal que contempla a gestão dos pedidos de uma lanchonete.  

## Estrutura utilizada nos pacotes

- adapter
    - input
        - controller
        - dto
        - mapper
    - output
        - persistence
            - entity
            - mapper
            - repository

- application
    - service

- domain (classes / interfaces referentes às regras de negócios da aplicação. criar as classes / interfaces sem usar frameworks - código o mais simples possível)
    - model (POJOs)
    - exception
    - port (interfaces referentes ao que será recebido / enviado)
        - input
            - rest
        - output
            - persistence
    - usecase (interfaces contendo os métodos a serem implementados)

- infrastructure (local onde serão utilizadas as dependências de cada cloud ou de recursos externos)
    - config (inclusão das configurações da aplicação, como por exemplo @Configuration do Spring, criando os @Bean)
    - aws (pacotes específicos para cada cloud, por exemplo)

## Tecnologias utilizadas

* Spring Boot 3.3.4
* Java 17
* PostgreSQL 16
* Docker

## Acesso às APIs

* http://servername:8080/api-docs (endpoints)
* http://servername:8080/swagger-ui/index.html (swagger-ui)

## Contribuidores
* Alexandre - RM 359968
* Fabio Tetsuo Chuman - RM
* Guilherme Fausto - RM 359909
* Nicolas Silva - RM 360621
* Rodrigo M Pereira - RM360575


## Licença
[![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](./LICENSE)
