# Javagas 🚗

Javagas é uma api REST de gerenciamento de estacionamento, a nível de estudo, construído utilizando os conceitos de DDD (Domain-Driven Design), focado em desacoplar a regra de negócio da infraestrutura.

## 🚀 Tecnologias

- Spring Boot como framework
- Spring Data JPA, desacoplado da camada de domínio, como persistência de dados
- PostgreSQL
- Testcontainers como ambiente de testes
- Maven como gerenciador de dependências

## 🏗️ Arquitetura

A estrutura do projeto foi pensada para facilitar a testabilidade e a manutenção:

- **Domain:** Contém as entidades (`Vaga`, `Ticket`), Value Objects (`Placa`, `Tarifa`, `CodigoTicket`) e as regras de negócio. Pensado para ser independente de frameworks/libs.
- **Application:** Lida com o fluxo de dados e executa os casos de uso, como `CriarVaga`, `GerarTicket`, etc.
- **Infra:**
  - **Entrada:** Controllers REST.
  - **Saída:** Persistência utilizando o Spring Data JPA e mapeamento de entidades JPA separadas das entidades de domínio.

## 🛠️ Instalação e Execução

### Pré-requisitos

- JDK 17+.
- Maven 3.x.
- Docker e Docker Compose instalados.

1. **Clonar o repositório:**

```Bash
  git clone [Javagas] (https://github.com/matheus-vicente/ddd_with_spring.git)
  cd javagas
```

2. **Configurar o Banco de Dados:**

O projeto utiliza PostgreSQL. Você pode subir uma instância rapidamente via docker compose:

```Bash
  docker compose up -d
```

3. **Executar a aplicação:**

```bash
  mvn spring-boot:run
```

## 🧪 Testes

Para garantir a confiabilidade, o projeto utiliza **Testcontainers** para subir um banco real durante os testes de integração.

```bash
  mvn test
```

## 📍 Endpoints

### Vagas

> POST /v1/vagas - Cadastra uma nova vaga.

> GET /v1/vagas - Lista todas as vagas paginadas.

> PUT /v1/vagas/{id} - Altera as informações de `código` e `tipo`.

> DELETE /v1/vagas/{id} - Deleta uma vaga

### Tickets

> POST /v1/tickets/gerar-ticket/{vagaId} - Gera um ticket de entrada para um veículo.

> PUT /v1/tickets/cancelar-ticket/{id} - Cancela um ticket.

> PUT /v1/tickets/deletar-ticket/{id} - Deleta um ticket.
