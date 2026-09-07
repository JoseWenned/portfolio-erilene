# Arquitetura do Backend

## 1. Objetivo

O backend do Portfólio da Erline foi estruturado utilizando princípios de Clean Architecture, buscando separar regras de negócio, casos de uso, interfaces externas e detalhes de infraestrutura.

O objetivo é manter o domínio independente de frameworks e tecnologias externas, facilitando testes, manutenção e evolução da aplicação.

## 2. Camadas

### Domain

Responsável pelas regras e conceitos centrais do negócio.

Esta camada contém:

* Entidades;
* Enumerações;
* Exceções de domínio;
* Contratos relacionados ao domínio.

O domínio não deve depender diretamente de Spring Framework, JPA, PostgreSQL ou HTTP.

### Application

Responsável pela execução dos casos de uso da aplicação.

Exemplos:

* Criar depoimento;
* Listar depoimentos aprovados;
* Moderar depoimento.

Esta camada coordena as operações necessárias para executar cada caso de uso.

### Infrastructure

Responsável pelos detalhes técnicos necessários para executar a aplicação.

Exemplos:

* Persistência com JPA;
* Implementação dos repositories;
* Configurações técnicas;
* Integrações com recursos externos.

### Interfaces

Responsável pela comunicação da aplicação com o mundo externo.

Inicialmente, será utilizada uma API REST para comunicação com o frontend.

Esta camada contém:

* Controllers;
* DTOs de entrada e saída;
* Tratamento de exceções HTTP.

## 3. Regra de dependência

As dependências devem apontar para dentro da arquitetura.

O domínio não deve conhecer detalhes de infraestrutura ou transporte HTTP.

A infraestrutura pode depender do domínio para implementar mecanismos necessários à aplicação.

## 4. Fluxo de uma requisição

O fluxo esperado para uma operação REST é:

```text
Cliente
   ↓
Controller
   ↓
Use Case
   ↓
Domain
   ↓
Repository
   ↓
PostgreSQL
```

O retorno segue o caminho inverso:

```text
PostgreSQL
   ↓
Repository
   ↓
Use Case
   ↓
Controller
   ↓
Cliente
```

## 5. Decisão arquitetural

A separação das responsabilidades foi adotada para reduzir o acoplamento entre regra de negócio e detalhes técnicos.

Dessa forma, alterações em tecnologias de persistência, transporte ou infraestrutura devem causar o menor impacto possível nas regras centrais do sistema.

## 6. Escopo inicial

O backend possui escopo reduzido e será desenvolvido inicialmente para atender o módulo de depoimentos do portfólio.

As principais funcionalidades serão:

* Receber depoimentos;
* Armazenar depoimentos;
* Listar depoimentos aprovados;
* Permitir moderação;
* Manter depoimentos pendentes, aprovados ou rejeitados.

Novas funcionalidades poderão ser adicionadas posteriormente sem comprometer a separação arquitetural definida.
