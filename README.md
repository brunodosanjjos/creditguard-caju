# CreditGuard - Gerenciador de Transações (Java)
#### Desafio Tecnico Caju 2024

### Descrição:
CreditGuard é um sistema de gerenciamento de transações que classifica e autoriza transações com base em códigos MCC (Merchant Category Code).
Este projeto utiliza Spring Boot e o Mysql como banco de dados.

### Índice
- Introdução
- Instalação
- Uso
- Exemplo de Requisição
- Diagrama da solucao
- Passo a Passo para Execução
- Documentacao da api
- Collection
- Questão aberta

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

### Modelagem das Tabelas:
![Modelagem Bando de dados ](../creditguard-caju/db_creditguard.png)


### Passo a Passo para Execução
##### 1. Clonar o Repositório : [creditguard](https://github.com/brunodosanjjos/creditguard)

>git clone https://github.com/brunodosanjjos/creditguard-caju

##### 2. Construir a Imagem Docker e executar o Container :

> docker-compose up

Use esse comando na pasta do projeto para criar uma imagem Docker e iniciar a container

##### 3. Verificar a Aplicação
Abra o seu navegador ou gestor de chamadas e acesse http://localhost:8080/actuator/health para verificar a aplicação.


### Documentacao da api

Essas são as documentações da API:

#### Open API:  http://localhost:8080/api-docs

#### Swagger: http://localhost:8080/swagger-ui/index.html#/


### Collection

Para facilitar o uso das funcionalidades da API  baixe o arquivo **creditguard-collection.json** desse repositorio e adicione no seu gerenciador.


### Questão aberta

Quando se pensa em gerenciamento de transações, a primeira ideia que nos vem à mente é um sistema de filas. 
No entanto, dependendo do escopo que será executado, isso pode acabar ultrapassando a margem especificada. 
Isso nos leva aos conceitos de mecanismos de bloqueio. Usar um bloqueio otimista assume que as transações raramente entram em conflito. 
Ele verifica se os dados não foram alterados por outra transação antes de confirmar a operação. Porém também poderíamos ultrapassar a margem. 
Já o bloqueio pessimista, por outro lado, assume que os conflitos são comuns e bloqueia os dados assim que uma transação começa a processá-los. 
Em Java, você pode usar mecanismos de sincronização como o synchronized ou bibliotecas de concorrência como locks para garantir que apenas uma transação por vez possa acessar e modificar o saldo da conta. 
Outra opção é usar ReentrantLock, que oferece mais controle sobre o bloqueio quando se trata de restringir até que ponto do método ou processo o bloqueio ficará ativo.

Conclusão
Escolher uma abordagem sobre gerenciamento de transações tem suas vantagens e desvantagens. 
A escolha da melhor solução depende do contexto específico do sistema, qual a frequência de transações simultâneas e os 
requisitos de desempenho. Usar bloqueio otimista ou pessimista é mais direto, enquanto o uso de um sistema de filas 
como Kafka pode oferecer uma solução mais escalável e robusta para processamento de transações em alta escala. 
Entretanto, algo que sempre deve ser priorizado é a qualidade e velocidade dos métodos implementados no gerenciamento 
das transações via aplicação. Sem isso, todo o processo de fila ou bloqueio seria penalizado.

