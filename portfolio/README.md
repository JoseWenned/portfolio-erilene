# Portfolio Erline

Backend REST do portfolio da Erline, construído com Java 21, Spring Boot 4,
PostgreSQL e uma separação inspirada em Clean Architecture.

## Objetivo

A aplicação recebe depoimentos de clientes, mantém esses registros pendentes
para moderacao e publica somente os depoimentos aprovados. Tambem oferece a
administracao de usuarios e a documentacao interativa da API.

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Spring Security com HTTP Basic
- PostgreSQL
- Springdoc OpenAPI / Swagger UI
- JUnit, Testcontainers e MockMvc

## Pre-requisitos

- JDK 21
- Maven 3.9+ ou o Maven Wrapper (`./mvnw`)
- PostgreSQL 16 ou compativel
- Docker, para os testes de integracao com Testcontainers

## Configuracao local

Por padrao, a aplicacao utiliza:

```text
URL:      jdbc:postgresql://localhost:5432/portfolio_erline
Usuario:  postgres
Senha:    postgres
Porta:    8080
```

As credenciais do administrador devem ser fornecidas por configuracao externa.
Nao coloque uma senha real no repositorio.

## Executar

Na pasta `portfolio`, com PostgreSQL em execucao:

```bash
mvn spring-boot:run \
  -Dspring-boot.run.arguments="--portfolio.security.admin.email=admin@portfolio.test --portfolio.security.admin.password=admin-password"
```

Para usar outra porta:

```bash
mvn spring-boot:run \
  -Dspring-boot.run.arguments="--server.port=8081 --portfolio.security.admin.email=admin@portfolio.test --portfolio.security.admin.password=admin-password"
```

## Testes

```bash
mvn clean test
```

Os testes de API usam PostgreSQL em Testcontainers. Portanto, o Docker precisa
estar acessivel durante a execucao.

## Documentacao e requests

Com a aplicacao em execucao:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Requests REST Client: [requests/portfolio-api.http](requests/portfolio-api.http)

Se a aplicacao estiver na porta 8081, use essa porta nas URLs acima. O arquivo
`.http` ja esta configurado para 8081 quando essa porta e necessaria.

## Documentacao do projeto

- [Arquitetura](docs/architecture/overview.md)
- [API REST](docs/api.md)
- [Operacao e desenvolvimento](docs/development.md)
- [Uso de LLMs e sessoes](docs/llm/sessions.md)