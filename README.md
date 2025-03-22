# FIAP Tech Challenge

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Maven](https://img.shields.io/badge/maven-%230db7ed.svg?style=for-the-badge&logo=maven&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)

## Sobre o projeto 

Tech Challenge do curso Software Architecture da FIAP. 

### Fase 1
> Aplicação desenvolvida utilizando arquitetura hexagonal que contempla a gestão dos pedidos de uma lanchonete.
>
> O código fonte inalterado desta fase ainda pode ser encontrado na branch [`release/v1.0.0`](https://github.com/ra1nmak3r1/tech_challenge_fiap/tree/release/v1.0.0)

### Fase 2
> Migração da aplicação da arquitetura hexagonal para clean architecture.

## Resumo sobre a Fase 2

1. Por conta do refactoring para clean architecture, uma situação que enfrentamos foi a ausência do contexto transacional do Spring na utilização das classes de negócios quando executavam o módulo de persistência (JPA), uma vez que as classes de negócios (*UseCasesImpl) não estavam mais sendo gerenciadas pelo ApplicationContext do Spring. Como solução para este cenário, utilizamos AOP (Programação Orientada a Aspectos) para interceptar as chamadas aos métodos dos Controllers (que estão sendo gerenciados pelo Spring) para incluirmos cada execução em uma transação isolada.

2. Alteramos a estrutura do projeto em sub-módulos, sendo eles:
	- business: contém as classes de negócios, que fazem parte do core da aplicação e que podem ser executados com diferentes recursos externos, sendo utilizados os frameworks: lombok e mapstruct - ambos utilizados na geração de código em tempo de compilação.
	- app: contém as classes relacionadas aos frameworks e recursos utilizados para o correto funcionamento da aplicação.
	- pagamento-mock: aplicação apartada que simula a execução do Mercado Pago para efetivação do pagamento do pedido. 

3. No módulo business, foram utilizados apenas os frameworks lombok e mapstruct.

    - O lombok é utilizado para a geração de métodos getters, setters, hashCode, equals e construtores.

    - Enquanto o mapstruct é utilizado para a criação de métodos que fazem o mapeamento dos atributos entre entidades para realização da cópia dos valores dos atributos de beans de classes diferentes.

4. Utilizamos os presenters apenas como sendo a transformação dos beans de domínio pra os DTOs a serem enviados para fora dos Controllers.  Nesta implementação os DTOs são os mesmos utilizados no recebimento dos métodos externos e como informação a ser retornada, mas em caso de alteração da informação retornada, basta alterar o tipo de retorno dos métodos dos Controllers e os presenters. 


## Estrutura utilizada nos pacotes


```
raíz
├── app (módulo)
│   ├── Dockerfile
│   ├── pom.xml
│   └── src
│       └── application
│           ├──	device
│           │   ├── queue (produtores / consumidores)
│           │   ├── rest (interfaces)
│           │   │   ├── exception
│           │   │   │   └── handler
│           │   │   └── impl (implementações das interfaces)mapper
│           │   └── persistence
│           │       ├── entity
│           │       ├── mapper
│           │       └── repository
│           └── infrastructure (local onde serão utilizadas as dependências de cada cloud ou de recursos externos)
│               ├── aspect (pacote contendo as classes da AOP)
│               ├── config (inclusão das configurações da aplicação, como por exemplo @Configuration do Spring, criando os @Bean)
│               ├── utils (classes utilitárias)
│               └── aws (pacotes específicos para cada cloud, por exemplo)
├── business (módulo)
│   ├── pom.xml
│   └── src
│       └── business (classes / interfaces referentes às regras de negócios da aplicação. criar as classes / interfaces sem usar frameworks - código o mais simples possível)
│           ├── adapter
│           │   ├── controller
│           │   ├── gateway
│           │   └── presenter
│           ├── common
│           │   ├── dto
│           │   ├── mapper
│           │   ├── queue (produtor / consumidor)
│           │   └── persistence
│           └── core
│               ├── domain (POJOs)
│               ├── exception
│               └── usecase (interfaces contendo os métodos a serem implementados)
│                   └── impl (implementações das interfaces)
└── pagamento-mock (módulo)
    ├── Dockerfile
    ├── pom.xml
    └── src
        └── pagamentomock
            ├── adapter
            │   ├── input
            │   │    ├── controller
            │	│    ├── dto
            │	│    └── mapper
            │	└── output
            └── infrastructure
                ├── config
                └── utils

```

## Tecnologias utilizadas na Aplicação

* Maven 3.9.5
* Spring Boot 3.3.4
* Java 17

## 📦 Arquitetura da Infraestrutura e CI/CD

### 🚀 Tecnologias Utilizadas na Infraestrutura

- **Minikube** — Cluster Kubernetes local para simular produção
- **Kubernetes (K8s)** — Orquestração dos recursos
- **Docker** — Empacotamento das aplicações em containers
- **Docker Compose** — Suporte ao ambiente de desenvolvimento local
- **GitHub Actions** — Pipeline de CI para build e deploy das imagens
- **Docker Hub** — Repositório para armazenar imagens da aplicação
- **Secrets e ConfigMaps** — Gestão segura de variáveis sensíveis no cluster
- **RabbitMQ(4.0.5) & PostgreSQL(16)** — Infraestrutura de mensageria e banco de dados

---

### ✅ Pré-requisitos para Execução

- Docker e Docker Compose instalados
- Minikube instalado e configurado localmente (Testes e validações realizados com a v1.35.0)
- Acesso ao `.env` com as variáveis necessárias

---

### 🛠️ Integração Contínua (CI)

O fluxo de CI é automatizado via **GitHub Actions** e é engatilhado a cada `push` na branch `main`.

1. Faz o checkout do repositório
2. Gera as imagens Docker de cada aplicação (`app` e `mock-pagamento`)
3. Faz o login no Docker Hub usando um **Access Token seguro**
4. Publica as imagens no Docker Hub (`app` em repositório privado e `mock-pagamento` em repositório público)

---

### 🌐 Deploy e Infraestrutura (CD)

A subida do ambiente é feita localmente via script `setup.sh` ou `setup.bat`, que:

1. Lê e carrega o arquivo `.env` com credenciais e configurações
2. Cria dinamicamente as Secrets no Kubernetes
3. Aplica todos os manifestos do cluster (PostgreSQL, RabbitMQ, app e mock)
4. Expõe os serviços via `port-forward` para acesso local (`localhost:8080`, `:8081`)

---

### 🧭 Fluxo da Arquitetura (CI/CD)

```mermaid
flowchart TD
    dev[Desenvolvedor] --> pushGit[Push para GitHub]

    subgraph GitHub_Actions_CI
        pushGit --> buildApp[Build imagem da aplicação]
        buildApp --> buildMock[Build imagem do mock-pagamento]
        buildMock --> dockerLogin[Login no Docker Hub]
        dockerLogin --> pushImages[Push das imagens privadas]
    end

    pushImages --> dockerHub[Docker Hub]

    dev --> runSetup[Executa setup.sh ou setup.bat]

    subgraph Execucao_Local_CD
        runSetup --> loadEnv[Carrega variáveis do .env]
        loadEnv --> createSecrets[Cria Secrets no Kubernetes]
        createSecrets --> applyManifests[Aplica manifestos do cluster]
        applyManifests --> portForward[Port-forward dos serviços]
    end

    dockerHub --> applyManifests
    portForward --> appAccess[Acesso: http://localhost:8080]
    portForward --> mockAccess[Acesso: http://localhost:8081]
```

## Como executar o projeto

Este projeto é uma aplicação Java que utiliza **Docker** e **Docker Compose** para facilitar a execução e a configuração.

### Pré-requisitos

Ambiente para execução da aplicação:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas em sua máquina para compilação da aplicação:

- [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3.9+](https://maven.apache.org/)
- [Spring Boot 3.3.4](https://spring.io/projects/spring-boot)
 
Siga os passos abaixo para executar o projeto:

### 1. Introdução

Para esta nova etapa é necessário remover os containers que foram utilizados para a Fase 1 do projeto.

### 2. Instalar o pom parent no repositório
Primeiro passo, é instalar o pacote parent da aplicação, através do comando:

```bash
mvn -DskipTests -DskipITs=true -N clean install 
```

### 3. Compilar o projeto
Em seguida, compile o projeto e gere o arquivo JAR. Para isso, execute:

```bash
mvn -DskipTests -DskipITs=true -N clean install
```

### 4. Execução da aplicação

A aplicação será executada em containers. Este ambiente pode ser apartado em relação ao código fonte, por isso trataremos da execução desta forma.

Desta forma utilizaremos uma pasta raíz, e as subpastas de acordo com os módulos que serão executados como micro-serviços, chamada deploy para exemplificação:

```
deploy
├── .env
└── docker-compose.yml
    └── app
		├── Dockerfile
	    └── target 
	        └── app-0.1.0-SNAPSHOT.jar
    └── pagamento-mock
        ├──	Dockerfile
        └── target 
            └── pagamento-mock-0.1.0-SNAPSHOT.jar
```

[A estrutura apresentada acima pode ser obtida a partir deste link.](https://drive.google.com/file/d/1ph1Kpj9o3_74XkMHHpIow1AC16tN_M9I/view?usp=sharing)

Uma vez que a estrutura acima tenha sido replicada ou obtida pelo link acima, executar o seguinte comando na raíz:

### 5. Subir a aplicação com Docker Compose
```bash
docker compose up --build
```

### 6. Acessar a aplicação
A aplicação estará disponível no endereço: http://localhost:8080.

Certifique-se de verificar as configurações de porta no arquivo `docker-compose.yml`, caso haja personalizações.

## Acesso às APIs

Aplicação:
* http://servername:8080/api-docs (endpoints)
* http://servername:8080/swagger-ui/index.html (swagger-ui)

Pagamento Mock:
* http://servername:8081/api-docs (endpoints)
* http://servername:8081/swagger-ui/index.html (swagger-ui)


## Fluxo de Execução:

### 1. Criação de um novo pedido:

POST -> /pedido-service/v1/save

### 2. Atualização dos itens do pedido:

PUT -> /pedido-service/v1/save/:id

Esta atualização contempla os itens, o pagamento e também o status do pedido.

O ID do objeto pagamento deve ser preenchido. 

Caso o status seja alterado para RECEBIDO, significa que o pedido foi finalizado pelo usuário e agora será feito o processamento do pagamento, que ocorrerá de forma assíncrona, utilizando o projeto Pagamento Mock. 

### 3. Confirmação do Pagamento

Para confirmar que o pagamento foi realizado, é necessário executar o endpoint abaixo do Pagamento Mock, que por sua vez, executará o webhook da aplicação.

Pagamento Mock endpoint:

POST -> /pagamento-mock-service/v1/callPagamentoWebHook/:pedidoId/:aprovarPagamento

Webhook (endpoint) da aplicação:

POST -> /pagamento-service/v1/updateStatus/:pedidoId/:statusCode

Observações:
- ambos os métodos foram definidos como POST por não serem idempotentes.
- a execução do webhook, caso receba o statusCode = 100, significa que o pagamento foi realizado com sucesso e fará com que o pedido seja confirmado e as Ordens de Serviço sejam criadas para a cozinha. 

## Contribuidores
* Fabio Tetsuo Chuman - RM 360172
* Guilherme Fausto - RM 359909
* Nicolas Silva - RM 360621
* Rodrigo Medda Pereira - RM 360575


## Licença
[![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](./LICENSE)