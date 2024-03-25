# Cadastro Backend API

## Descrição
Este repositório contém uma API em Java desenvolvida com Spring Boot para o gerenciamento de cadastro de pessoas e seus contatos.

A API oferece endpoints para realizar operações como adicionar, atualizar, listar e excluir pessoas e seus respectivos contatos. Utiliza o banco de dados PostgreSQL para persistência dos dados e fornece uma interface RESTful para integração com outras aplicações.

A lógica de implementação consiste em que uma Pessoa possui N Contatos.

## Pré-requisitos
Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:
- Java JDK
- Maven
- PostgreSQL

## Dependências
A API utiliza as seguintes dependências do Spring Boot:
- `spring-boot-starter-web`: Para criação de aplicativos web com Spring MVC.
- `spring-boot-devtools`: Para reinicialização automática do aplicativo em ambiente de desenvolvimento.
- `lombok`: Para simplificar a criação de classes Java com anotações.
- `spring-boot-starter-test`: Para testes unitários.
- `spring-boot-starter-data-jpa`: Para persistência de dados utilizando JPA.
- `spring-boot-starter-validation`: Para validação de dados.
- `h2`: Banco de dados em memória para ambiente de teste.

## Endpoints
### Pessoa Controller
- `GET /pessoa`: Retorna todas as pessoas cadastradas.
- `GET /pessoa/{id}`: Retorna uma pessoa específica pelo ID.
- `GET /pessoa/paginado`: Retorna as pessoas de forma paginada.
- `GET /pessoa/contatos/{cd_pessoa}`: Retorna todos os contatos de uma pessoa específica.
- `POST /pessoa`: Adiciona uma nova pessoa.
- `PUT /pessoa/{id}`: Atualiza os dados de uma pessoa pelo ID.
- `DELETE /pessoa/{id}`: Exclui uma pessoa e seus contatos pelo ID.
- `DELETE /pessoa/contatos/{id}`: Exclui todos os contatos de uma pessoa pelo ID.

### Contato Controller
- `GET /contato/paginado`: Retorna os contatos de forma paginada.
- `POST /contato/add/{cd_pessoa}`: Adiciona um novo contato para uma pessoa específica.
- `PUT /contato/{cd_pessoa}/{cd_contato}`: Atualiza um contato existente.
- `DELETE /contato/{cd_pessoa}/{cd_contato}`: Exclui um contato específico.

## Como Executar
1. Clone este repositório para sua máquina local.
2. Configure o arquivo `application.properties` com as credenciais do seu banco de dados PostgreSQL.
3. Execute o comando `mvn spring-boot:run` para iniciar a aplicação.
4. Acesse os endpoints utilizando uma ferramenta de teste de API como Postman ou através de requisições HTTP.
