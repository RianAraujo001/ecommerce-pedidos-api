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

O sistema precisa calcular o frete com base no tipo de envio escolhido pelo cliente.

Para isso, foi aplicado o padrão **Strategy**, permitindo escolher o algoritmo de cálculo em tempo de execução.

**Implementação no projeto:**

* `CalculoFrete` → interface com o método `calcular(valor)`
* `FreteCaminhao` → calcula 5% do valor do pedido
* `FreteAviao` → calcula 10% do valor do pedido

No momento da criação do pedido, a estratégia é selecionada dinamicamente com base no parâmetro `frete` recebido na API.

**Vantagem:**

Evita uso excessivo de condicionais (`if/else`) e permite adicionar novos tipos de frete sem alterar o código existente.

---

## 🔵 Observer – Notificação

O sistema precisa notificar o cliente quando um pedido é criado.

Para isso, foi aplicado o padrão **Observer**, permitindo que objetos sejam notificados quando um evento ocorre.

**Implementação no projeto:**

* `Observador` → interface com o método `atualizar(msg)`
* `ClienteObserver` → responsável por receber a notificação

Durante a criação do pedido, o observer é acionado para enviar a mensagem de confirmação.

**Vantagem:**

Desacopla a lógica de notificação da regra principal do sistema.

---

## 🟡 State – Controle de Status

O sistema possui regras bem definidas para mudança de status do pedido.

Para organizar esse comportamento, foi aplicado o conceito do padrão **State**, utilizando classes para representar cada estado.

**Implementação no projeto:**

* `PedidoState`
* `AguardandoPagamentoState`
* `PagoState`
* `EnviadoState`
* `CanceladoState`

Cada estado representa uma fase do pedido e define quais ações são permitidas.

A camada de serviço reforça essas regras garantindo:

* Não é possível pagar duas vezes
* Não é possível cancelar após envio
* Não é possível alterar um pedido cancelado

**Vantagem:**

Garante consistência no fluxo do pedido e evita estados inválidos.

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

O diagrama de classes representa a estrutura do sistema, evidenciando a organização em camadas e a aplicação dos padrões de projeto utilizados.

Nele é possível identificar:

- A separação entre Controller, Service, Repository e Model
- A entidade principal `Pedido`
- A aplicação do padrão Strategy no cálculo de frete
- O uso do padrão Observer para notificação
- O controle de estados do pedido utilizando o padrão State

 <img width="1644" height="777" alt="Diagrama de classe" src="https://github.com/user-attachments/assets/82b588be-8f64-4321-b65b-74919151e244" />

---

# 🎯 Conclusão

A solução desenvolvida atende aos requisitos propostos, implementando o controle de pedidos com regras bem definidas de estado e cálculo automático de frete.

Durante o desenvolvimento, foram aplicados padrões de projeto como Strategy, Observer e State, permitindo uma separação clara de responsabilidades e deixando o sistema mais organizado e flexível.

Essas decisões facilitam a manutenção do código e possibilitam a evolução da aplicação, como a adição de novos tipos de frete ou mudanças nas regras de negócio, sem impactar diretamente a estrutura existente.

Com isso, o sistema se torna mais consistente, reutilizável e preparado para futuras melhorias.
