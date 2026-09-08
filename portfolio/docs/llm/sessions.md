# Uso de LLMs e sessoes de desenvolvimento

Este documento define como usar modelos de linguagem (LLMs) como apoio ao
desenvolvimento deste projeto. A LLM auxilia a analise e a escrita, mas nao
substitui testes, revisao humana ou a responsabilidade sobre o codigo.

## O que e uma LLM

Uma LLM e um modelo treinado para reconhecer e gerar texto com base em
contexto. Em engenharia de software, ela pode ajudar a explorar um repositorio,
explicar codigo, propor testes, documentar decisoes e implementar alteracoes
pequenas e verificaveis.

Ela pode produzir respostas plausiveis e incorretas. Por isso, toda resposta
que altera o projeto precisa ser confrontada com o codigo, os testes e os
requisitos reais.

## O que e uma sessao de LLM

Uma sessao e uma unidade de trabalho com contexto acumulado: objetivo, arquivos
consultados, hipoteses, alteracoes, validacoes e decisoes. A sessao deve ter um
escopo claro e terminar com um estado verificavel.

Uma sessao nao deve ser tratada como memoria permanente. Decisoes importantes
devem ser registradas em documentacao ou no historico do Git.

## Ciclo recomendado

### 1. Definir o objetivo

Descreva o comportamento esperado, o modulo envolvido e o criterio de aceite.
Exemplo: "Adicionar um endpoint administrativo para criar usuarios e retornar
201 sem expor a senha".

### 2. Reunir contexto local

Forneca ou solicite a leitura dos arquivos diretamente relacionados: controller,
caso de uso, entidade, configuracao e testes vizinhos. Evite mapear o projeto
inteiro sem necessidade.

### 3. Formular uma hipotese verificavel

A LLM deve declarar onde acredita que o comportamento e controlado e qual teste
pode confirmar ou refutar essa ideia.

### 4. Fazer a menor alteracao

Prefira uma mudanca pequena, mantendo os padroes existentes e sem misturar
refatoracoes nao relacionadas.

### 5. Validar imediatamente

Depois da alteracao, execute primeiro o teste ou compilacao mais proximo do
comportamento mudado. Em seguida, execute a suite completa quando necessario.

### 6. Registrar o resultado

A sessao deve terminar com arquivos alterados, comando de validacao, resultado,
pendencias e, quando apropriado, um commit.

## Checklist de qualidade

- O pedido esta claro e possui criterio de aceite?
- A LLM identificou o ponto real de controle do comportamento?
- A alteracao respeita a arquitetura existente?
- Foram adicionados ou atualizados testes?
- Os testes realmente passaram, e nao apenas foram sugeridos?
- Segredos, dados pessoais e tokens foram removidos do contexto?
- O diff foi revisado antes do commit?
- A documentacao foi atualizada quando o contrato mudou?

## Seguranca e privacidade

Nunca envie para uma LLM:

- senhas, tokens, chaves privadas ou credenciais de banco reais;
- dados pessoais de clientes que nao sejam necessarios;
- arquivos de configuracao com segredos;
- dumps de producao sem anonimizacao.

Use valores ficticios, como `admin@portfolio.test`, e substitua segredos por
placeholders.

## Registro de uma sessao

Para tarefas relevantes, registre:

```text
Objetivo:
Contexto consultado:
Hipotese:
Alteracoes:
Validacao executada:
Resultado:
Decisoes e pendencias:
Commit:
```

## Limites de confianca

Codigo gerado por LLM exige revisao especialmente cuidadosa em autenticacao,
autorizacao, persistencia, concorrencia, migracoes de banco e tratamento de
dados pessoais. Nesses pontos, a criterio final deve ser o contrato do sistema,
as bibliotecas oficiais e os testes executados no ambiente do projeto.