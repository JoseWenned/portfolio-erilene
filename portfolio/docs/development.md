# Desenvolvimento e operacao

## Fluxo recomendado

1. Atualize a branch principal antes de iniciar uma tarefa.
2. Crie uma branch pequena e orientada a uma funcionalidade.
3. Escreva ou atualize testes junto com a alteracao.
4. Execute `mvn clean test` antes do commit.
5. Revise `git diff --check` e o estado do repositorio.
6. Use uma mensagem de commit objetiva e envie a branch para revisao.

## Banco de dados

O desenvolvimento local usa PostgreSQL. O Hibernate esta configurado com
`ddl-auto=update` para o ambiente atual; uma evolucao futura deve adotar
migracoes versionadas, como Flyway ou Liquibase, antes de ambientes de
producao.

## Seguranca

- Credenciais administrativas devem vir de variaveis de ambiente ou argumentos
  externos.
- Senhas devem ser armazenadas somente como hash BCrypt.
- Endpoints de moderacao e administracao exigem role `ADMIN`.
- O Swagger e publico apenas para consulta da especificacao.
- Nao registrar senhas, hashes ou cabecalhos `Authorization` nos logs.

## Testes

- Testes de dominio validam regras das entidades.
- Testes de caso de uso validam orquestracao e contratos.
- Testes de repositorio validam persistencia com PostgreSQL.
- Testes REST validam JSON, status HTTP, seguranca e integracao das camadas.

## Diagnostico rapido

### Porta 8080 ocupada

Inicie em outra porta:

```bash
mvn spring-boot:run \
  -Dspring-boot.run.arguments="--server.port=8081"
```

Depois ajuste `@baseUrl` em `requests/portfolio-api.http`.

### Banco indisponivel

Confira se o PostgreSQL esta ativo e se o banco `portfolio_erline` existe. Os
testes de integracao usam um container separado e nao dependem desse banco
local.