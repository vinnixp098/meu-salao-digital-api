# ✨ Lumien API - Plataforma de Gestão para Salões

O **Lumien** é uma plataforma SaaS desenvolvida para modernizar a gestão de salões de beleza, substituindo processos manuais por uma solução digital completa, organizada e escalável.

A API REST do projeto é responsável pelo gerenciamento de:

- 👥 Clientes
- 📅 Atendimentos
- ✂️ Serviços
- 💇 Profissionais
- 📊 Relatórios
- 🏢 Empresas
- 🔐 Autenticação e controle de acesso

O objetivo do Lumie é ajudar salões a terem mais controle operacional, produtividade e organização no dia a dia.

---

# 🚀 Tecnologias Utilizadas

- ☕ **Java 24**
- 🌱 **Spring Boot 3.5**
- 📦 **Maven**
- 🗃️ **Spring Data JPA**
- 🐘 **PostgreSQL**
- 🔐 **Spring Security + JWT**
- 🔧 **Spring Web**
- ✍️ **Lombok**
- 📬 **Java Mail Sender**
- 🧪 **Bean Validation**

---

# 🧱 Arquitetura do Projeto

O projeto segue uma arquitetura organizada em camadas para facilitar manutenção e escalabilidade.

## 📂 Estrutura de pacotes

- `controller`
  Responsável por expor os endpoints da API REST.

- `service`
  Contém as regras de negócio da aplicação.

- `repository`
  Interfaces JPA responsáveis pela comunicação com o banco de dados.

- `model`
  Entidades do sistema.

- `dto`
  Objetos de transferência de dados.

- `config`
  Configurações gerais da aplicação.

- `security`
  Configurações de autenticação e autorização JWT.

- `exception`
  Tratamento global de exceções.

---

# ✨ Principais Funcionalidades

## 👥 Gestão de Clientes
- Cadastro de clientes
- Histórico de atendimentos
- Controle de informações

## 📅 Gestão de Atendimentos
- Criação de atendimentos
- Agendamento de horários
- Controle de status
- Registro de serviços realizados

## ✂️ Gestão de Serviços
- Cadastro de serviços
- Controle de valores
- Associação de serviços aos atendimentos

## 💇 Gestão de Profissionais
- Controle de usuários
- Permissões de acesso
- Organização da equipe

## 📊 Relatórios
- Relatórios financeiros
- Histórico de atendimentos
- Controle operacional

## 🔐 Autenticação
- Login com JWT
- Recuperação por código via e-mail
- Controle de sessões

---

# 🏢 Modelo SaaS

O Lumie foi desenvolvido utilizando arquitetura multiempresa (multi-tenant), permitindo que múltiplos salões utilizem a plataforma de forma isolada e segura.

Cada salão possui:
- seus próprios clientes;
- seus atendimentos;
- seus profissionais;
- seus relatórios;
- suas configurações.

---

# 💻 Configuração do Banco de Dados

A aplicação utiliza PostgreSQL.

## Exemplo de configuração:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/lumie
spring.datasource.username=postgres
spring.datasource.password=123456

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
