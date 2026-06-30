# 📝 API de Tarefas com Autenticação JWT

API RESTful desenvolvida em Java com Spring Boot, contendo sistema de autenticação via JWT (JSON Web Token) e CRUD de tarefas vinculado a cada usuário.

---

## 🚀 Tecnologias

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT (jjwt)
- Lombok
- Maven

---

## 🔐 Funcionalidades

- Registro de usuário com senha criptografada (BCrypt)
- Login com geração de token JWT
- Rotas protegidas por autenticação via token
- CRUD de tarefas (criar, listar, atualizar, deletar)
- Cada usuário só acessa as próprias tarefas
- Tratamento global de exceções

---

## 📂 Estrutura do projeto

```
src/main/java/com/example/demo/
├── model/         → Entidades (Usuario, Tarefa)
├── repository/     → Interfaces JPA
├── dto/            → Objetos de entrada/saída (records)
├── security/       → JWT (geração, filtro, configuração)
├── service/        → Regras de negócio
├── controller/     → Endpoints REST
└── exception/      → Tratamento de erros
```

---

## ⚙️ Como executar

1. Clone o repositório
```bash
git clone https://github.com/gabriellpachecok-lgtm/api-tarefas-jwt.git
```

2. Crie o banco de dados no PostgreSQL
```sql
CREATE DATABASE tarefas_db;
```

3. Configure o `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tarefas_db
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA
```

4. Rode a aplicação pela classe `DemoApplication`

A API sobe em `http://localhost:8080`

---

## 📮 Endpoints

### Autenticação (públicos)

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/auth/registro` | Cria um novo usuário |
| POST | `/auth/login` | Retorna o token JWT |

**Exemplo de registro/login:**
```json
{
  "email": "teste@teste.com",
  "senha": "123456"
}
```

### Tarefas (protegidas — exige token)

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/tarefas` | Lista as tarefas do usuário logado |
| POST | `/tarefas` | Cria uma nova tarefa |
| PUT | `/tarefas/{id}` | Atualiza uma tarefa |
| DELETE | `/tarefas/{id}` | Remove uma tarefa |

**Header obrigatório nas rotas de tarefas:**
```
Authorization: Bearer SEU_TOKEN_AQUI
```

**Exemplo de criação de tarefa:**
```json
{
  "titulo": "Estudar Spring Security",
  "descricao": "Terminar o projeto JWT",
  "concluida": false
}
```

---

## 🛡️ Segurança

- Senhas armazenadas com hash BCrypt, nunca em texto puro
- Tokens JWT com expiração de 2 horas
- Filtro de autenticação intercepta todas as requisições protegidas
- Cada usuário só pode editar/deletar suas próprias tarefas
