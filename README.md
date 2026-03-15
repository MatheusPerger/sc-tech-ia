# SC Business API

API REST para gerenciamento de empreendimentos cadastrados em Santa Catarina.
Desenvolvida com Spring Boot como parte do desafio prático de seleção **SCTEC - IA para DEVs**.

---

## 📋 Descrição

A SC Business API é um serviço RESTful que permite o gerenciamento completo de registros de empreendimentos do estado de Santa Catarina. Suporta criação, listagem, atualização e exclusão de empreendimentos, com filtragem opcional por status e segmento. A aplicação utiliza banco de dados H2 em memória, dispensando qualquer configuração externa de banco de dados para execução.

---

## 🛠️ Tecnologias

| Tecnologia              | Versão | Finalidade                        |
|-------------------------|--------|-----------------------------------|
| Java                    | 21     | Linguagem de programação          |
| Spring Boot             | 3.2.3  | Framework da aplicação            |
| H2 Database             | -      | Banco de dados em memória         |
| Springdoc OpenAPI       | -      | Swagger UI / Documentação da API  |
| Lombok                  | -      | Redução de código boilerplate     |
| JUnit 5                 | 5.10   | Testes unitários                  |
| Mockito                 | 5      | Framework de mocks para testes    |
| Maven                   | 3.9    | Ferramenta de build               |

---

## 📁 Estrutura do Projeto
```
src/
└── main/
│   ├── java/br/com/scbusiness/
│   │   ├── ScBusinessApiApplication.java   # Ponto de entrada da aplicação
│   │   ├── config/
│   │   │   └── OpenApiConfig.java          # Configuração do Swagger/OpenAPI
│   │   ├── controller/
│   │   │   └── BusinessController.java     # Endpoints REST
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   │   └── BusinessRequestDTO.java # Payload de entrada com validações
│   │   │   └── response/
│   │   │       └── BusinessResponseDTO.java # Payload de saída
│   │   ├── enums/
│   │   │   ├── BusinessStatus.java         # ACTIVE / INACTIVE
│   │   │   └── Segment.java                # Segmento de atuação
│   │   ├── exception/
│   │   │   ├── ErrorResponse.java          # Corpo padrão de resposta de erro
│   │   │   ├── GlobalExceptionHandler.java # Tratamento centralizado de exceções
│   │   │   └── ResourceNotFoundException.java
│   │   ├── model/
│   │   │   └── Business.java               # Entidade JPA
│   │   ├── repository/
│   │   │   └── BusinessRepository.java     # Camada de acesso a dados
│   │   └── service/
│   │       ├── BusinessService.java        # Interface do serviço
│   │       └── impl/
│   │           └── BusinessServiceImpl.java # Implementação da lógica de negócio
│   └── resources/
│       ├── application.properties          # Configurações da aplicação
│       └── data.sql                        # Dados iniciais (seed)
└── test/
    └── java/br/com/scbusiness/
        ├── controller/
        │   └── BusinessControllerTest.java # Testes da camada controller
        └── service/
            └── BusinessServiceImplTest.java # Testes da camada service
```

---

## ▶️ Como Executar

### Pré-requisitos
- Java 21 instalado ([Download](https://www.oracle.com/br/java/technologies/downloads/#java21))
- Maven 3.9+ instalado ([Download](https://maven.apache.org))

> ⚠️ **Atenção:** O Maven deve estar configurado para usar o **Java 21**.
> Caso tenha múltiplas versões do Java instaladas, verifique com `mvn -version`
> se a versão exibida é a 21.

### Passo a passo
```bash
# 1. Clone o repositório
git clone https://github.com/MatheusPerger/sc-tech-ia.git

# 2. Acesse a pasta do projeto
cd sc-business-api

# 3. Compile o projeto
mvn clean install

# 4. Execute a aplicação
mvn spring-boot:run
```

A API estará disponível em:
```
http://localhost:8080
```

---

## 🔗 URLs Disponíveis

| URL                                       | Descrição                  |
|-------------------------------------------|----------------------------|
| `http://localhost:8080/api/v1/businesses` | Endpoint principal da API  |
| `http://localhost:8080/swagger-ui.html`   | Swagger UI                 |
| `http://localhost:8080/api-docs`          | Especificação OpenAPI JSON |
| `http://localhost:8080/h2-console`        | Console do Banco H2        |

> **Configurações do Console H2:**
> - JDBC URL: `jdbc:h2:mem:scbusinessdb`
> - Usuário: `sa`
> - Senha: *(deixar em branco)*

---

## 📡 Endpoints da API

| Método   | Endpoint                    | Descrição                              | Status Code |
|----------|-----------------------------|----------------------------------------|-------------|
| `POST`   | `/api/v1/businesses`        | Cadastrar um novo empreendimento       | 201         |
| `GET`    | `/api/v1/businesses`        | Listar todos os empreendimentos        | 200         |
| `GET`    | `/api/v1/businesses/{id}`   | Buscar empreendimento por ID           | 200         |
| `PUT`    | `/api/v1/businesses/{id}`   | Atualizar empreendimento por ID        | 200         |
| `DELETE` | `/api/v1/businesses/{id}`   | Remover empreendimento por ID          | 204         |

### Parâmetros de consulta para GET `/api/v1/businesses`

| Parâmetro | Tipo            | Obrigatório | Descrição                          |
|-----------|-----------------|-------------|------------------------------------|
| `status`  | `BusinessStatus`| Não         | Filtrar por `ACTIVE` ou `INACTIVE` |
| `segment` | `Segment`       | Não         | Filtrar por categoria de segmento  |

---

## 📋 Exemplos de Requisição e Resposta

### POST `/api/v1/businesses`

**Corpo da Requisição:**
```json
{
  "name": "TechNova",
  "ownerName": "Matheus Perger",
  "city": "Florianópolis",
  "segment": "TECHNOLOGY",
  "contact": "matheus@technova.com.br",
  "status": "ACTIVE",
  "description": "Empresa focada em soluções com IA"
}
```

**Resposta — 201 Created:**
```json
{
  "id": 1,
  "name": "TechNova",
  "ownerName": "Matheus Perger",
  "city": "Florianópolis",
  "segment": "TECHNOLOGY",
  "contact": "matheus@technova.com.br",
  "status": "ACTIVE",
  "description": "Empresa focada em soluções com IA",
  "createdAt": "2026-01-30T10:00:00",
  "updatedAt": "2026-01-30T10:00:00"
}
```

### GET `/api/v1/businesses?status=ACTIVE&segment=TECHNOLOGY`

**Resposta — 200 OK:**
```json
[
  {
    "id": 1,
    "name": "TechNova",
    "ownerName": "Matheus Perger",
    "city": "Florianópolis",
    "segment": "TECHNOLOGY",
    "contact": "matheus@technova.com.br",
    "status": "ACTIVE",
    "description": "Empresa focada em soluções com IA",
    "createdAt": "2026-01-30T10:00:00",
    "updatedAt": "2026-01-30T10:00:00"
  }
]
```

### Resposta de Erro — 404 Not Found:
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Business not found with id: 99",
  "timestamp": "2026-01-30T10:00:00"
}
```

### Resposta de Erro — 400 Bad Request:
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Business name is required, Segment is required",
  "timestamp": "2026-01-30T10:00:00"
}
```

---

## 🗂️ Opções de Segmento

| Valor          | Descrição   |
|----------------|-------------|
| `TECHNOLOGY`   | Tecnologia  |
| `COMMERCE`     | Comércio    |
| `INDUSTRY`     | Indústria   |
| `SERVICES`     | Serviços    |
| `AGRIBUSINESS` | Agronegócio |

---

## 🧪 Executando os Testes
```bash
mvn test
```

Os testes cobrem:
- Camada de serviço (testes unitários com Mockito)
- Camada de controller (testes de integração com MockMvc)
- Cenários de sucesso e de erro para todas as operações CRUD

---

## 🎥 Vídeo Pitch

[Link do vídeo aqui]