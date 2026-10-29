# Calculadora CO2 API

API RESTful desenvolvida em Java 21 com Spring Boot e banco Oracle. Calcula o CO2 economizado, os pontos e o cashback de trajetos feitos em ônibus, metrô ou trem.

Projeto da 4ª Sprint de Domain Driven Design Using Java (FIAP).

## Arquitetura

- `model`: classes de domínio (CalculadoraCO2, Transporte, Onibus, Metro, Trem)
- `dao`: acesso ao banco (ConnectionFactory e CalculadoraCO2DAO)
- `bo`: validações e regras de negócio
- `resource`: endpoints REST e CorsFilter
- `exception`: tratamento de erros com respostas em JSON

## Endpoints

| Verbo  | URL              | Descrição        | Sucesso |
|--------|------------------|------------------|---------|
| GET    | /calculos        | Lista todos      | 200     |
| GET    | /calculos/{id}   | Busca por id     | 200     |
| POST   | /calculos        | Cria um cálculo  | 201     |
| PUT    | /calculos/{id}   | Altera           | 200     |
| DELETE | /calculos/{id}   | Exclui           | 204     |

Erros: 400 (validação) e 404 (não encontrado).

### Exemplo de corpo (POST/PUT)

```json
{ "nome": "usuario", "tipoTransporte": "Onibus", "distanciaKm": 10 }
```

## Como rodar

Defina as variáveis de ambiente `DB_USER` e `DB_PASSWORD` e execute:

    ./mvnw spring-boot:run

## Deploy

https://calculadoraco2.onrender.com/calculos