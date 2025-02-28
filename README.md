# FIAP Tech Challenge

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)

## About 

Tech Challenge do curso Software Architecture da FIAP. 

Fase 1: Aplicação desenvolvida utilizando arquitetura hexagonal que contempla a gestão dos pedidos de uma lanchonete.

Fase 2: Migração da aplicação da arquitetura hexagonal para clean architecture.

Observações:
a) Por conta do refactoring praa clean architecture, uma situação que enfrentamos foi a ausência do contexto transacional do Spring na utilização das classes de negócios quando executavam o módulo de persistência (JPA), uma vez que as classes de negócios (*UseCasesImpl) não estavam mais sendo gerenciadas pelo ApplicationContext do Spring. Como solução para este cenário, utilizamos AOP (Programação Orientada a Aspectos) para interceptar as chamadas aos métodos dos Controllers (que estão sendo gerenciados pelo Spring) para incluirmos cada execução em uma transação isolada.

b) Re-estruturamos os pacotes da aplicação, separando em 2 principais:
	- application: contém as classes relacionadas aos frameworks e recursos utilizados para o correto funcionamento da aplicação.
	- business: contém as classes de negócios, que fazem parte do core da aplicação e que podem ser executados com diferentes recursos externos, sendo utilizados os frameworks: lombok e mapstruct - ambos utilizados na geração de código em tempo de compilação.

O lombok é utilizado para a geração de métodos getters, setters, hashCode, equals e construtores.

Enquanto o mapstruct é utilizado para a criação de métodos que fazem o mapeamento dos atributos entre entidades para realização da cópia dos valores dos atributos de beans de classes diferentes.

c) Utilizamos os presenters apenas como sendo a transformação dos beans de domínio pra os DTOs a serem enviados para fora dos Controllers.  Nesta implementação os DTOs são os mesmos utilizados no recebimento dos métodos externos e como informação a ser retornada, mas em caso de alteração da informação retornada, basta alterar o tipo de retorno dos métodos dos Controllers e os presenters. 


## Estrutura utilizada nos pacotes


```
src
├── application
│   └── device
│       ├── queue (produtores / consumidores)
│       ├── rest (interfaces)
│       │   └── impl (implementações das interfaces)
│       └── persistence
│           ├── entity
│           ├── mapper
│           └── repository
├── business (classes / interfaces referentes às regras de negócios da aplicação. criar as classes / interfaces sem usar frameworks - código o mais simples possível)
│   ├── adapter
│   │   ├── controller
│   │   ├── gateway
│   │   └── presenter
│   ├── common
│   │   ├── dto
│   │   ├── mapper
│   │   ├── queue (produtor / consumidor)
│   │   └── persistence
│   └── core
│       ├── domain (POJOs)
│       ├── exception
│       │   └── handler
│       └── usecase (interfaces contendo os métodos a serem implementados)
│           └── impl (implementação das interfaces)
└── infrastructure (local onde serão utilizadas as dependências de cada cloud ou de recursos externos)
    ├── aspect (pacote contendo as classes da AOP)
    ├── config (inclusão das configurações da aplicação, como por exemplo @Configuration do Spring, criando os @Bean)
    ├── utils (classes utilitárias)
    └── aws (pacotes específicos para cada cloud, por exemplo)
```

## Tecnologias utilizadas

* Spring Boot 3.3.4
* Java 17
* PostgreSQL 16
* RabbitMQ 4.0.5
* Docker
* Docker-Compose

## Como executar o projeto

Este projeto é uma aplicação Java que utiliza **Docker** e **Docker Compose** para facilitar a execução e a configuração.

### Pré-requisitos

Ambiente para execução da aplicação:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas em sua máquina para compilação da aplicação:

- [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3.8+](https://maven.apache.org/)
- [Spring Boot 3.3.4](https://spring.io/projects/spring-boot)
 
Siga os passos abaixo para executar o projeto:

### 1. Compilar o projeto
Primeiro, compile o projeto e gere o arquivo JAR. Para isso, execute:

```bash
mvn package -DskipTests
```

### 2. Execução da aplicação

A aplicação será executada em containers.  Este ambiente pode ser apartado em relação ao código fonte, por isso trataremos da execução desta forma.

Desta forma utilizaremos uma pasta raíz chamada deploy para exemplificação:

```
deploy
├── .env
├── Dockerfile
└── docker-compose.yml
    └── target 
        └── lanchonete-0.1.0-SNAPSHOT.jar
```

[A estrutura apresentada acima pode ser obtida a partir deste link.](https://drive.google.com/file/d/1ph1Kpj9o3_74XkMHHpIow1AC16tN_M9I/view?usp=sharing)

Uma vez que a estrutura acima tenha sido replicada ou obtida pelo link acima, executar o seguinte comando:

c:\lanchonete\docker compose up --build

### 3. Subir a aplicação com Docker Compose
```bash
docker compose up --build
```

### 4. Acessar a aplicação
A aplicação estará disponível no endereço: http://localhost:8080.

Certifique-se de verificar as configurações de porta no arquivo `docker-compose.yml`, caso haja personalizações.

## Acesso às APIs

* http://servername:8080/api-docs (endpoints)
* http://servername:8080/swagger-ui/index.html (swagger-ui)

## Contribuidores
* Estevão Oliveira - RM 360184
* Fabio Tetsuo Chuman - RM 360172
* Guilherme Fausto - RM 359909
* Nicolas Silva - RM 360621
* Rodrigo Medda Pereira - RM 360575


## Licença
[![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](./LICENSE)