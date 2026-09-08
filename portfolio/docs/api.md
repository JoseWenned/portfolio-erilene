# API REST

## Convencoes

- Base URL local: `http://localhost:8080`
- Formato de entrada e saida: `application/json`
- IDs: UUID
- Datas: `LocalDateTime`, serializado pelo Spring Boot
- Autenticacao administrativa: HTTP Basic

## Depoimentos

### Criar depoimento

`POST /api/depoimentos`

Publico. O registro nasce com status `PENDENTE`.

```json
{
  "nome": "Maria",
  "comentario": "Excelente profissional!",
  "nota": 5,
  "fotoUrl": "https://example.com/maria.jpg"
}
```

Resposta: `201 Created`.

### Listar depoimentos

`GET /api/depoimentos`

Publico. Retorna somente depoimentos com status `APROVADO`.

Resposta: `200 OK`.

### Aprovar depoimento

`PATCH /api/depoimentos/{id}/aprovar`

Requer usuario com role `ADMIN`.

Resposta: `200 OK`.

### Rejeitar depoimento

`PATCH /api/depoimentos/{id}/rejeitar`

Requer usuario com role `ADMIN`.

Resposta: `200 OK`.

## Usuarios

### Criar usuario

`POST /api/usuarios`

Requer usuario com role `ADMIN`. A senha e armazenada com BCrypt e nunca e
retornada na resposta.

```json
{
  "email": "novo-admin@portfolio.test",
  "senha": "senha-segura",
  "role": "ADMIN"
}
```

Resposta: `201 Created`.

## Autenticacao

Exemplo com `curl`:

```bash
curl -u admin@portfolio.test:admin-password \
  -H 'Content-Type: application/json' \
  -d '{"email":"novo-admin@portfolio.test","senha":"senha-segura","role":"ADMIN"}' \
  http://localhost:8080/api/usuarios
```

## Erros

As respostas de erro seguem este formato:

```json
{
  "status": 400,
  "error": "Erro de validação",
  "message": "Existem dados inválidos na requisição.",
  "timestamp": "2026-09-08T10:00:00",
  "details": [
    "nota: A nota deve ser no máximo 5."
  ]
}
```

Status usados:

- `400 Bad Request`: JSON ou regra de validacao invalida
- `401 Unauthorized`: autenticacao ausente ou invalida
- `403 Forbidden`: usuario autenticado sem permissao de administrador
- `404 Not Found`: depoimento inexistente
- `409 Conflict`: email de usuario ja cadastrado