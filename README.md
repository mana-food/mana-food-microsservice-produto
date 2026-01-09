# 🍽️ ManaFood - Microserviço de Produtos

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mana-food_mana-food-microsservice-produto&metric=coverage)](https://sonarcloud.io/dashboard?id=mana-food_mana-food-microsservice-produto)

## 📋 Sobre o Projeto

O **ManaFood Product Microservice** é um microsserviço para gerenciamento de produtos, categorias e itens de um sistema de delivery de alimentos. O projeto foi construído seguindo os princípios da **Arquitetura Limpa (Clean Architecture)**, garantindo separação de responsabilidades, testabilidade e manutenibilidade.

### 🎯 Objetivos

- Gerenciar categorias de produtos (Bebidas, Lanches, Sobremesas, etc.)
- Gerenciar produtos e seus itens componentes
- Fornecer API REST para integração com outros microsserviços
- Garantir qualidade através de testes unitários e BDD (Behavior-Driven Development)

---

## 🚀 Quick Start

### Para Desenvolvedores
```bash
# 1. Clone o repositório
git clone <seu-repo>
cd mana-food-microsservice-produto

# 2. Configure ambiente local
cp .env.example .env
# Edite .env com suas credenciais

# 3. Suba o banco de dados
docker-compose up -d db-mana-food-product

# 4. Execute os testes
./mvnw verify
```
---

## 🏗️ Arquitetura Limpa

O projeto segue os princípios da **Clean Architecture**, organizado em camadas bem definidas:

```
┌─────────────────────────────────────────┐
│   Adapter Layer (Controllers/API)       │ ← Interface com o mundo externo
├─────────────────────────────────────────┤
│   Application Layer (Use Cases)         │ ← Lógica de aplicação e orquestração
├─────────────────────────────────────────┤
│   Domain Layer (Entities/Business)      │ ← Regras de negócio puras
├─────────────────────────────────────────┤
│   Infrastructure (Database/External)    │ ← Implementação técnica
└─────────────────────────────────────────┘
```

### 📦 Estrutura de Pacotes

```
br.com.manafood.manafoodproduct/
├── adapter/                    # Camada de Adaptadores
│   ├── controller/             # Controllers REST (API)
│   ├── mapper/                 # Conversores DTO ↔ Entity
│   ├── request/                # DTOs de requisição
│   └── response/               # DTOs de resposta
│
├── application/                # Camada de Aplicação
│   └── usecase/                # Casos de uso (Create, Update, Delete, Queries)
│       ├── category/
│       ├── product/
│       └── item/
│
├── domain/                     # Camada de Domínio
│   ├── entity/                 # Entidades de negócio
│   ├── port/                   # Interfaces (Ports)
│   ├── exception/              # Exceções de negócio
│   └── common/                 # Objetos de valor compartilhados
│
└── infrastructure/             # Camada de Infraestrutura
    ├── persistence/            # JPA Entities e Repositories
    └── config/                 # Configurações Spring
```

### 🔄 Fluxo de Dados

```
Cliente HTTP → Controller → Use Case → Domain → Repository → Database
                   ↓           ↓         ↓          ↓
                 Mapper    Business   Entity   JPA Entity
```

---

## 🚀 Tecnologias Utilizadas

### Core
- **Kotlin** 1.9.24 - Linguagem principal
- **Spring Boot** 3.3.5 - Framework
- **Java** 17 - Runtime

### Banco de Dados
- **MySQL** 8.0 - Banco de dados relacional
- **Flyway** - Migrações de banco de dados
- **Hibernate/JPA** - ORM

### Testes
- **JUnit 5** - Framework de testes
- **MockK** 1.13.13 - Mocking para Kotlin
- **Cucumber** 7.18.1 - Testes BDD
- **Testcontainers** 1.19.3 - Containers para testes
- **JaCoCo** - Cobertura de código

### Documentação
- **SpringDoc OpenAPI** - Documentação automática da API (Swagger)

### DevOps
- **Docker & Docker Compose** - Containerização
- **Maven** - Build e gerenciamento de dependências
- **SonarCloud** - Análise de qualidade de código
- **GitHub Actions** - CI/CD

---

## 📊 Modelo de Dados

### Entidades Principais

**Category** (Categoria)
- `id`: UUID
- `name`: String (nome da categoria)
- Exemplos: Bebidas, Lanches, Sobremesas

**Item** (Item/Ingrediente)
- `id`: UUID
- `name`: String
- `unitPrice`: BigDecimal (preço unitário)
- Exemplos: Queijo, Presunto, Coca-Cola

**Product** (Produto)
- `id`: UUID
- `name`: String
- `categoryId`: UUID (referência à categoria)
- `items`: List<Item> (itens que compõem o produto)
- Exemplo: X-Burguer (categoria: Lanches, itens: Pão, Carne, Queijo)

### Relacionamentos

```
Category 1 ──── N Product
Product N ──── N Item (tabela intermediária: product_item)
```

---

## 🔌 API REST

### Endpoints Principais

#### **Categorias** (`/categories`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/categories` | Criar nova categoria |
| GET | `/categories/{id}` | Buscar categoria por ID |
| GET | `/categories` | Listar todas (paginado) |
| PUT | `/categories/{id}` | Atualizar categoria |
| DELETE | `/categories/{id}` | Excluir categoria |

#### **Produtos** (`/products`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/products` | Criar novo produto |
| GET | `/products/{id}` | Buscar produto por ID |
| GET | `/products` | Listar todos (paginado) |
| PUT | `/products/{id}` | Atualizar produto |
| DELETE | `/products/{id}` | Excluir produto |

#### **Itens** (`/items`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/items` | Criar novo item |
| GET | `/items/{id}` | Buscar item por ID |
| GET | `/items` | Listar todos (paginado) |
| PUT | `/items/{id}` | Atualizar item |
| DELETE | `/items/{id}` | Excluir item |

### 📄 Documentação Swagger

Acesse a documentação interativa da API:
```
http://localhost:8081/swagger-ui.html
```

---

## 🚀 Como Executar

### Pré-requisitos

- **Docker** e **Docker Compose** instalados
- **Java 17** ou superior
- **Maven 3.8+** (opcional, usa wrapper incluído)

### ⚠️ PASSO 0: Configurar Variáveis de Ambiente (OBRIGATÓRIO)

```bash
# 1. Copie o arquivo de exemplo
cp .env.example .env

# 2. Edite o .env e configure suas credenciais
# Mínimo necessário:
#   SPRING_DATASOURCE_USERNAME=root
#   SPRING_DATASOURCE_PASSWORD=sua_senha
#   MYSQL_ROOT_PASSWORD=sua_senha_mysql

# 3. NUNCA commite o arquivo .env no Git!
```

### Opção 1: Docker Compose (RECOMENDADO) 🐳

A forma mais fácil - sobe banco de dados E aplicação automaticamente:

```bash
# 1. Clone o repositório
git clone <repo-url>
cd mana-food-microsservice-produto

# 2. Configure o .env (veja PASSO 0 acima)
cp .env.example .env
# Edite o .env com suas credenciais

# 3. Suba os containers
docker-compose up -d

# 4. Verifique os logs
docker-compose logs -f app-mana-food-product

# 5. Teste a aplicação
curl http://localhost:8081/actuator/health

# 6. Acesse o Swagger
# Abra: http://localhost:8081/swagger-ui.html

# 7. Parar os containers
docker-compose down
```

**Serviços criados:**
- MySQL: `localhost:3307`
- Aplicação: `localhost:8081`

### Opção 2: Banco Docker + Aplicação Local 🔧

Rode o banco no Docker e a aplicação na IDE:

```bash
# 1. Configure o .env
cp .env.example .env
# Edite com suas credenciais

# 2. Inicie apenas o MySQL
docker-compose up -d db-mana-food-product

# 3. Configure o profile local
cp src/main/resources/application-local.yml.example src/main/resources/application-local.yml
# Edite application-local.yml com suas credenciais locais

# 4. Defina a variável de ambiente
$env:SPRING_PROFILES_ACTIVE="local"  # PowerShell
# OU
set SPRING_PROFILES_ACTIVE=local     # CMD

# 5. Execute a aplicação
./mvnw spring-boot:run

# Ou pela IDE (IntelliJ/Eclipse):
# - Abra: ManaFoodProductApplication.kt
# - Run/Debug com profile "local"
```
---

## 🧪 Testes

O projeto possui **duas estratégias de testes complementares**:

### 1️⃣ Testes Unitários (MockK)

Testes rápidos e isolados de cada componente:

```bash
# Executar todos os testes unitários
mvn test -Dtest='!CucumberBddRunner'

# Executar testes de uma classe específica
mvn test -Dtest=CategoryControllerTest
```

**Localização**: `src/test/kotlin/.../adapter`, `application`, `infrastructure`

**Características**:
- ✅ Rápidos (milissegundos)
- ✅ Isolados com MockK
- ✅ Cobertura de unidades individuais

### 2️⃣ Testes BDD (Cucumber + Testcontainers)

Testes End-to-End completos com banco real:

```bash
# Executar todos os testes BDD
mvn test -Dtest=CucumberBddRunner

# Executar por tag específica
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@category"
mvn test -Dcucumber.filter.tags="@product"
```

**Localização**: `src/test/kotlin/.../bdd` e `src/test/resources/features`

**Características**:
- ✅ Testes End-to-End completos
- ✅ Banco MySQL real em container (Testcontainers)
- ✅ Todas as camadas integradas
- ✅ Gherkin em português
- ✅ Executado via Cucumber + JUnit 5

### 📊 Estrutura de Testes BDD

```
src/test/
├── kotlin/.../bdd/
│   ├── CucumberBddRunner.kt           # Runner JUnit 5
│   ├── config/
│   │   ├── AbstractBddTest.kt         # Configuração base (Testcontainers)
│   │   └── CucumberSpringConfiguration.kt
│   └── steps/
│       ├── CategorySteps.kt           # Step Definitions
│       ├── ProductSteps.kt
│       └── ItemSteps.kt
└── resources/features/
    ├── category.feature               # Cenários Gherkin
    ├── product.feature
    └── item.feature
```

### 🥒 Exemplo de Cenário Gherkin

```gherkin
# language: pt
Funcionalidade: Gerenciamento de Categorias

  @smoke @category
  Cenário: Criar uma nova categoria com sucesso
    Dado que o sistema está disponível
    E o banco de dados está limpo
    Quando eu criar uma categoria com nome "Bebidas"
    Então a categoria deve ser criada com sucesso
    E a categoria deve ter o nome "Bebidas"
```

### 📈 Cobertura de Código

```bash
# Gerar relatório de cobertura
mvn clean test jacoco:report

# Ver relatório HTML
start target/site/jacoco/index.html  # Windows
open target/site/jacoco/index.html   # Mac/Linux
```

**Meta**: 90% de cobertura de código

---

## 🥒 Testes BDD com Cucumber

### 🎯 O que é BDD?

**BDD (Behavior-Driven Development)** é uma abordagem de desenvolvimento orientada por comportamento onde:
- Testes são escritos em **linguagem natural** (Gherkin)
- Stakeholders não-técnicos podem entender os cenários
- Foco no **comportamento do sistema**, não implementação

### 🏗️ Arquitetura dos Testes BDD

```
┌─────────────────────────────────────┐
│   Testes BDD (Cucumber/Gherkin)     │  ← Testes de Aceitação E2E
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│   Adapter Layer (Controllers)       │  ← API REST
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│   Application Layer (Use Cases)     │  ← Lógica de Negócio
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│   Domain Layer (Entities)           │  ← Core do Sistema
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│   Infrastructure (Persistence)      │  ← MySQL (Testcontainers)
└─────────────────────────────────────┘
```

### 🐳 Testcontainers - Banco Real

Os testes BDD usam um **container Docker real** com MySQL:

**Vantagens:**
- ✅ Testes com banco de dados real
- ✅ Flyway executa migrações reais
- ✅ Isolamento total entre testes
- ✅ Não precisa de banco instalado localmente
- ✅ CI/CD friendly

**Requisito:** Docker instalado e rodando

### 📊 Cenários Implementados

#### Category (7 cenários)
- ✅ Criar categoria
- ✅ Buscar por ID (existente e inexistente)
- ✅ Listar todas
- ✅ Atualizar
- ✅ Excluir
- ✅ Paginação

#### Product (7 cenários)
- ✅ Criar produto
- ✅ Buscar por ID (existente e inexistente)
- ✅ Atualizar
- ✅ Excluir
- ✅ Paginação
- ✅ Validação (criar sem categoria)

#### Item (7 cenários)
- ✅ Criar item
- ✅ Buscar por ID
- ✅ Listar todos
- ✅ Atualizar
- ✅ Excluir
- ✅ Paginação

### 🏷️ Tags Disponíveis

Execute testes específicos usando tags:

```bash
# Testes críticos (smoke tests)
mvn test -Dcucumber.filter.tags="@smoke"

# Por funcionalidade
mvn test -Dcucumber.filter.tags="@category"
mvn test -Dcucumber.filter.tags="@product"
mvn test -Dcucumber.filter.tags="@item"

# Por tipo
mvn test -Dcucumber.filter.tags="@pagination"
mvn test -Dcucumber.filter.tags="@validation"
```

### 📊 Relatórios Cucumber

Após executar os testes, relatórios são gerados automaticamente:

1. **Console Pretty** - Saída colorida no terminal
2. **JSON Report** - `target/cucumber-reports/cucumber.json`
3. **HTML Report** - `target/cucumber-reports/cucumber.html`

```bash
# Visualizar relatório HTML
start target/cucumber-reports/cucumber.html  # Windows
open target/cucumber-reports/cucumber.html   # Mac/Linux
```

---

## 🗄️ Migrações de Banco (Flyway)

As migrações são executadas automaticamente na inicialização:

**Scripts:** `src/main/resources/db/migration/`
- `V1__create_category.sql` - Tabela de categorias
- `V2__create_item.sql` - Tabela de itens
- `V3__create_product.sql` - Tabela de produtos
- `V4__create_product_item.sql` - Tabela de relacionamento

**Comandos úteis:**

```bash
# Recriar banco de dados (apaga e cria novamente)
.\recreate-database.ps1  # Windows

# Verificar status das migrações
mvn flyway:info

# Limpar banco (CUIDADO!)
mvn flyway:clean
```

---

## 🔧 Configuração

### 🔐 Segurança e Variáveis de Ambiente (IMPORTANTE!)

Este projeto utiliza **variáveis de ambiente** para proteger dados sensíveis.

**⚠️ ANTES DE EXECUTAR:**

```bash
# 1. Copie o arquivo de exemplo
cp .env.example .env

# 2. Edite o .env com suas credenciais reais
# Exemplo:
# SPRING_DATASOURCE_PASSWORD=sua_senha_aqui
# MYSQL_ROOT_PASSWORD=sua_senha_mysql_aqui

# 3. Para desenvolvimento local, copie também:
cp src/main/resources/application-local.yml.example src/main/resources/application-local.yml
```


### Profiles do Spring

O projeto usa profiles para diferentes ambientes:

**`application.yml`** (default - usa variáveis de ambiente)
```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3307/db_manafood_product}
    username: ${SPRING_DATASOURCE_USERNAME}
    password: ${SPRING_DATASOURCE_PASSWORD}
```

**`application-local.yml`** (desenvolvimento local - crie a partir do .example)
```yaml
# Copie de application-local.yml.example e configure suas credenciais
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/db_manafood_product
    username: your_username
    password: your_password
```

**Como ativar o profile local:**

```bash
# Variável de ambiente (PowerShell)
$env:SPRING_PROFILES_ACTIVE="local"
mvn spring-boot:run

# Variável de ambiente (CMD)
set SPRING_PROFILES_ACTIVE=local
mvn spring-boot:run

# Argumento direto
mvn spring-boot:run -Dspring-boot.run.profiles=local

# IntelliJ IDEA
# Run/Debug Configurations → Environment Variables → SPRING_PROFILES_ACTIVE=local
```

### Variáveis de Ambiente Disponíveis

| Variável | Descrição | Exemplo |
|----------|-----------|---------|
| `SPRING_DATASOURCE_URL` | URL do banco de dados | `jdbc:mysql://localhost:3307/db_manafood_product` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco | `root` |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco | `sua_senha_aqui` |
| `MYSQL_ROOT_PASSWORD` | Senha root MySQL (Docker) | `sua_senha_mysql` |
| `SONAR_TOKEN` | Token SonarCloud | `seu_token_sonar` |
| `SERVER_PORT` | Porta da aplicação | `8081` |
| `SPRING_PROFILES_ACTIVE` | Profile ativo | `local` ou `prod` |

---

## 📦 Build e Deploy

### Build Local

```bash
# Build sem testes
mvn clean package -DskipTests

# Build com testes
mvn clean package

# JAR gerado
target/ManaFoodProduct-0.0.1-SNAPSHOT.jar
```

### Docker Build

```bash
# Build da imagem
docker build -t manafood-product:latest .

# Run container
docker run -p 8081:8081 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3307/manafoodproduct \
  -e SPRING_DATASOURCE_USERNAME=manafood \
  -e SPRING_DATASOURCE_PASSWORD=manafood123 \
  manafood-product:latest
```

---

## 📊 Qualidade de Código

### SonarCloud

O projeto está integrado ao SonarCloud para análise contínua:

```bash
# Executar análise local
mvn clean verify sonar:sonar \
  -Dsonar.token=<SEU_TOKEN>
```

**Configuração no `pom.xml`:**
- Organization: `mana-food`
- Project Key: `mana-food_mana-food-microsservice-produto`
- Coverage: JaCoCo XML Reports

---

## 🤝 Contribuindo

### Convenções do Projeto

1. **Arquitetura Limpa** - Respeite as camadas
2. **Kotlin** - Linguagem principal (sem Java)
3. **MockK** - Para mocking (Mockito banido!)
4. **Testes BDD** - Use Cucumber para E2E
5. **Cobertura** - Mínimo 90%

### Processo de Desenvolvimento

1. Crie uma branch: `git checkout -b feature/nova-funcionalidade`
2. Implemente seguindo Clean Architecture
3. Escreva testes unitários (MockK)
4. Escreva cenários BDD (Gherkin)
5. Verifique cobertura: `mvn jacoco:report`
6. Commit: `git commit -m "feat: descrição"`
7. Push: `git push origin feature/nova-funcionalidade`
8. Abra Pull Request

---


## 🐛 Troubleshooting

### Docker não está rodando
```
Error: Could not find a valid Docker environment
```
**Solução:** Inicie o Docker Desktop

### Porta já em uso
```
Error: Port 8081 is already in use
```
**Solução:** Pare outras aplicações na porta 8081 ou mude no `application.yml`

### Erro de conexão com banco
```
Communications link failure
```
**Solução:** Verifique se o MySQL está rodando (`docker-compose ps`)

### Testes BDD falhando
**Solução:** Certifique-se que o Docker está rodando para o Testcontainers

---

## 📄 Licença

Este projeto é privado e de uso educacional.

---

## 👥 Autores

**ManaFood Team** - Projeto de Pós-Graduação

---

**Versão:** 0.0.1-SNAPSHOT  
**Última atualização:** Dezembro 2025

