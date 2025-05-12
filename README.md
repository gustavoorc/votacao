# Sistema de Gerenciamento de Pautas e Votações - API REST em Java/Spring Boot

## Descrição

Este projeto implementa uma API REST em Java com Spring Boot para gerenciar sessões de votação em assembleias de cooperativa. A plataforma permite cadastrar pautas, abrir sessões de votação, registrar votos de associados e contabilizar os resultados. Todo o sistema é persistente, garantindo a integridade dos dados mesmo após reinicializações da aplicação.

---

## Funcionalidades

- **Cadastrar uma nova pauta**  
  Cria uma pauta com título, descrição e outros atributos necessários.

- **Abrir uma sessão de votação**  
  Inicia uma sessão de votação em uma pauta existente. A sessão fica aberta por um período determinado via requisição ou 1 minuto padrão.

- **Receber votos dos associados**  
  Cada associado, identificado por um ID único, pode votar apenas uma vez por pauta, registrando votos 'Sim' ou 'Não'.

- **Contabilizar os votos e obter resultados**  
  Ao final da sessão, a API fornece o resultado da votação (quantidade de votos 'Sim' e 'Não', e o resultado final).

---

## Como Funciona

A comunicação entre cliente (app móvel) e backend é realizada por mensagens no formato JSON, que representam comandos ou respostas, possibilitando a montagem de telas de votação, resultados, etc. As definições específicas do formato estão detalhadas no anexo 1 do projeto.

---

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **JPA/Hibernate** (para persistência)
- **Banco de Dados**:  Postgres 
- **Build Tool**: Maven 

---

## Persistência de Dados

- As pautas e votos são armazenados de forma persistente, garantindo resiliência até mesmo após reinicializações da aplicação.

## Como Executar

1. Clone este repositório:
```bash
git clone <https://github.com/gustavoorc/votacao>
```

2. Importe o projeto na sua IDE (Eclipse, IntelliJ, etc).
3. Configure o banco de dados na application.properties

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/votacao
spring.datasource.username=seu user
spring.datasource.password=sua senha
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

4. Rode a aplicação (classe com @SpringBootApplication).

5. A API estará disponível na URL padrão: http://localhost:8080/