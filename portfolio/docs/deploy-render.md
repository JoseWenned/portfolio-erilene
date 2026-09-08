# Deploy gratuito no Render

## O que sera criado

O arquivo `render.yaml` define:

- um Web Service gratuito executando a imagem Docker;
- um PostgreSQL gratuito conectado por `DATABASE_URL`;
- health check em `/actuator/health`;
- credenciais administrativas informadas manualmente no painel do Render.

O plano gratuito do Render possui limites de uso e pode suspender o Web
Service apos um periodo sem trafego. O PostgreSQL gratuito tambem pode ter
limites de armazenamento, expiracao ou indisponibilidade conforme a politica
atual do Render. Consulte os limites exibidos no painel antes de usar dados
reais.

## Preparacao

1. Envie esta branch para o GitHub.
2. Acesse o Render e escolha **New > Blueprint**.
3. Conecte o repositorio do projeto.
4. Selecione o arquivo `render.yaml` e confirme a criacao dos recursos.
5. Preencha os valores solicitados:
   - `PORTFOLIO_SECURITY_ADMIN_EMAIL`
   - `PORTFOLIO_SECURITY_ADMIN_PASSWORD`
6. Aguarde o build e consulte os logs do Web Service.

## Variaveis de ambiente

O Render fornece `DATABASE_URL` a partir do PostgreSQL definido no Blueprint.
O entrypoint Docker converte a URL para o formato JDBC usado pelo Spring Boot.
O `PORT` e fornecido pelo Render e usado automaticamente pela aplicacao.

Nunca publique a senha administrativa em `render.yaml`, no Git ou em exemplos
de log. Use os campos protegidos do painel do Render.

## Validacao apos o deploy

Substitua o dominio pelo endereco gerado pelo Render:

```bash
curl -i https://SEU-SERVICO.onrender.com/actuator/health
curl -i https://SEU-SERVICO.onrender.com/v3/api-docs
curl -i https://SEU-SERVICO.onrender.com/api/depoimentos
```

Para criar um depoimento:

```bash
curl -i -X POST https://SEU-SERVICO.onrender.com/api/depoimentos \
  -H 'Content-Type: application/json' \
  -d '{"nome":"Maria","comentario":"Excelente profissional!","nota":5}'
```

Para criar um usuario administrador:

```bash
curl -i -u "$ADMIN_EMAIL:$ADMIN_PASSWORD" \
  -X POST https://SEU-SERVICO.onrender.com/api/usuarios \
  -H 'Content-Type: application/json' \
  -d '{"email":"outro-admin@example.com","senha":"senha-segura","role":"ADMIN"}'
```

## Observacoes para producao

- `spring.jpa.hibernate.ddl-auto=update` foi mantido para o primeiro deploy.
  Antes de uma base real, adote migracoes versionadas.
- O plano gratuito nao deve ser tratado como armazenamento permanente de
  depoimentos.
- Configure CORS quando um frontend hospedado em outro dominio for adicionado.
- Troque HTTP Basic por uma estrategia com tokens quando a aplicacao crescer.