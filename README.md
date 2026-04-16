# 🛒 Sistema de Gerenciamento de Pedidos - E-commerce

Projeto desenvolvido em **Java com Spring Boot** com o objetivo de controlar pedidos de um e-commerce, aplicando padrões de projeto, regras de negócio e arquitetura em camadas.

---

# 📌 📖 Descrição do Problema

O sistema controla pedidos considerando as seguintes regras:

* Um pedido inicia como **AGUARDANDO_PAGAMENTO**
* Pode ser **pago** ou **cancelado**
* Após pago:

  * Pode ser **enviado**
  * Pode ser **cancelado**
  * Não pode ser pago novamente
* Após enviado:

  * Não pode ser cancelado
* Após cancelado:

  * Não pode mais mudar de status

Além disso, o sistema calcula o **frete automaticamente na criação**:

* Caminhão → 5% do valor do pedido
* Avião → 10% do valor do pedido

O sistema foi pensado para permitir adicionar novos tipos de frete facilmente.

---

# 🎯 Objetivo da Solução

* Controlar pedidos com regras de status bem definidas
* Aplicar padrões de projeto na prática
* Persistir dados em banco H2
* Disponibilizar API REST para testes (Postman)

---

# 🏗️ 🧱 Arquitetura do Projeto

O sistema segue arquitetura em camadas:

```text
Controller → Service → Repository → Model
```

* **Controller**: recebe as requisições HTTP
* **Service**: contém as regras de negócio
* **Repository**: acesso ao banco de dados
* **Model**: entidades e padrões de projeto

---

# 📦 📊 Estrutura do Projeto

```text
br.com.ecommerce
 ├── controller
 │    └── PedidoController
 │
 ├── service
 │    └── PedidoService
 │
 ├── repository
 │    └── PedidoRepository
 │
 ├── model
 │    ├── entity
 │    │    └── Pedido
 │    │
 │    ├── observer
 │    │    ├── Observador
 │    │    └── ClienteObserver
 │    │
 │    ├── state
 │    │    ├── PedidoState
 │    │    ├── AguardandoPagamentoState
 │    │    ├── PagoState
 │    │    ├── EnviadoState
 │    │    └── CanceladoState
 │    │
 │    └── strategy
 │         ├── CalculoFrete
 │         ├── FreteCaminhao
 │         └── FreteAviao
 │
 └── EcommercePedidosApplication

resources
 └── application.properties
```

---

# 🧾 📌 Classe Principal (Pedido)

A classe `Pedido` representa o pedido no sistema e possui os seguintes atributos:

* `id`
* `cliente`
* `valorTotal`
* `valorFrete`
* `status`

---

# 🧠 🎯 Padrões de Projeto Utilizados

## 🟢 Strategy – Cálculo de Frete

Permite escolher o tipo de frete em tempo de execução.

**Implementação:**

* `CalculoFrete`
* `FreteCaminhao` (5%)
* `FreteAviao` (10%)

**Vantagem:**
Facilita adicionar novos tipos de frete sem alterar o código principal.

---

## 🔵 Observer – Notificação

Responsável por notificar o cliente quando o pedido é criado.

**Implementação:**

* `Observador`
* `ClienteObserver`

**Vantagem:**
Separa a lógica de notificação da regra principal do sistema.

---

## 🟡 State – Controle de Status

O controle de estados foi implementado com apoio das classes de estado e validações na camada de serviço.

**Status:**

* AGUARDANDO_PAGAMENTO
* PAGO
* ENVIADO
* CANCELADO

**Regras:**

* Não permite transições inválidas
* Garante consistência no fluxo do pedido

---

# 🚚 💰 Cálculo de Frete

| Tipo     | Cálculo                |
| -------- | ---------------------- |
| Caminhão | 5% do valor do pedido  |
| Avião    | 10% do valor do pedido |

---

# 🌐 🚀 Endpoints da API

**Base URL:**
`{{BaseUrl}} = http://localhost:8080`

---

## Criar Pedido (Caminhão)

```http
POST {{BaseUrl}}/pedidos?frete=CAMINHAO
```

Body:

```json
{
  "cliente": "Rian",
  "valorTotal": 500
}
```

---

## Criar Pedido (Avião)

```http
POST {{BaseUrl}}/pedidos?frete=AVIAO
```

Body:

```json
{
  "cliente": "Paulo",
  "valorTotal": 1000
}
```

---

## Listar Todos

```http
GET {{BaseUrl}}/pedidos
```

---

## Buscar por ID

```http
GET {{BaseUrl}}/pedidos/{id}
```

---

## Pagar Pedido

```http
PUT {{BaseUrl}}/pedidos/{id}/pagar
```

---

## Enviar Pedido

```http
PUT {{BaseUrl}}/pedidos/{id}/enviar
```

---

## Cancelar Pedido

```http
PUT {{BaseUrl}}/pedidos/{id}/cancelar
```

---

# 🧪 🛠️ Como Testar

### 1. Executar o projeto

Rodar a classe `EcommercePedidosApplication`

---

### 2. Acessar o banco H2

```
http://localhost:8080/h2-console
```

Configuração:

```
JDBC URL: jdbc:h2:mem:TRABALHOAV2
```

---

### 3. Testar no Postman

Fluxo recomendado:

1. Criar pedido
2. Pagar pedido
3. Enviar pedido

---

# 🗄️ 💾 Persistência de Dados

Os dados são armazenados em banco H2 em memória.

Tabela gerada:

```text
PEDIDO
- id
- cliente
- status  
- valor_frete
- valor_total  
```

---

# 📊 📐 Diagrama de Classes

O diagrama de classes representa:

* Estrutura do sistema
* Relação entre as classes
* Aplicação dos padrões Strategy, Observer e State

---

# 🎯 Conclusão

A aplicação atende ao problema proposto, implementando o controle de pedidos com regras claras de estado e cálculo automático de frete.

A utilização dos padrões de projeto contribui para uma estrutura mais organizada, facilitando manutenção e evolução do sistema.
