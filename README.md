# CreditGuard - Gerenciador de Transações (Java)
#### Motivação: Desafio Tecnico Caju 2024

### Descrição:
CreditGuard é um sistema de gerenciamento de transações que classifica e autoriza transações com base em códigos MCC (Merchant Category Code).
Este projeto utiliza Spring Boot e o Mysql como banco de dados.

### Índice
- Introdução
- Instalação
- Uso
- Estrutura do Projeto
- Contribuição
- Licença
- Contato

### Introdução
CreditGuard é um sistema projetado para gerenciar transações financeiras, classificando-as com base em códigos MCC 
e autorizando-as de acordo com os saldos disponíveis em diferentes categorias (FOOD, MEAL, CASH).

### Instalação
#### Pré-requisitos
- Java 11 ou superior
- Maven
- Docker [Docker Desktop](https://www.docker.com/products/docker-desktop/)

## Uso

### Endpoints Disponíveis

- **Processar Transação**

    ```http
    POST /v1/transaction
    ```

  **Corpo da Requisição:**

    ```json
    {
        "account": "1234",
        "totalAmount": 100.00,
        "mcc": "5411",
        "merchant": "Supermercado XYZ"
    }
    ```

  **Resposta:**

    ```json
    {
        "code": "00"
    }
    ```

### Exemplo de Requisição

```bash
curl -X POST http://localhost:8080/v1/transaction \
    -H "Content-Type: application/json" \
    -d '{
        "account": "1234",
        "totalAmount": 100.00,
        "mcc": "5411",
        "merchant": "Supermercado XYZ"
    }'
  ```  

### Diagrama da solucao:
![Modelagem Bando de dados ](../creditguard-caju/db_creditguard.png)


### Passo a Passo para Execução
##### 1. Clonar o Repositório : [creditguard](https://github.com/brunodosanjjos/creditguard)

>git clone https://github.com/brunodosanjjos/creditguard-caju

##### 2. Construir a Imagem Docker e executar o Container :

> docker-compose up

Use esse comando na pasta do projeto para criar uma imagem Docker e iniciar a container

##### 3. Verificar a Aplicação
Abra o seu navegador e acesse http://localhost:8080/actuator/health para verificar se a aplicação está rodando corretamente.


### Documentacao da api

Para acessar a documentacao da api, acesse:

#### Open API:  http://localhost:8080/api-docs

#### Swagger: http://localhost:8080/swagger-ui/index.html#/


### Collection

Para importar a collection em uma API Client, baixe o arquivo **creditguard-collection.json** desse repositorio


### L4 -  Questão aberta

Em caso de transaçoes simultaneas, para garantir que apenas uma transação seja processada por vez, dado que estamos processando as
transações de forma sincrona, foi implementado o bloqueio pessimsta, para garantir que nenhuma outra transação leia o registro na tabela,
enquanto já existe uma transacao em andamento, evitando leituras sujas, para nao correr o risco de novas transaçoes lerem ou modificarem o dado
durante o processamento da transação.

