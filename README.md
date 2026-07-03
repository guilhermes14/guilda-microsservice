# Sistema de Guilda — Microservices

## Serviços

| Serviço | Porta | Descrição |
|---|---|---|
| API Gateway | 8080 | Roteamento e fallback |
| guilda-app | 8081 | Sistema principal |
| ranking-service | 8082 | Microserviço de ranking |
| eureka-server | 8761 | Service Discovery |
| config-server | 8888 | Configuração centralizada |
| postgres-guilda | 5433 | Banco do professor (externo) |
| postgres-ranking | 5434 | Banco do ranking |


## Decisões Técnicas

- **ranking-service** foi escolhido como microserviço novo pois se integra naturalmente ao domínio de missões
- **Pontuação por nível de perigo**: BAIXO=10, MEDIO=25, ALTO=50, EXTREMO=100
- **Banco de dados** roda fora do compose pois já vem com dados pré-populados
- **host.docker.internal** usado para o guilda-app (container) acessar o banco externo (host)

## Pré-requisitos

- Docker Desktop instalado e rodando
- Java 21
- Maven

## Como Rodar

### 1. Sobe o banco 

```bash
docker run -d -p 5433:5432 --name postgres-guilda leogloriainfnet/postgres-tp2-spring:2.0-win
```

### 2. Define a senha do banco

```bash
docker exec -it postgres-guilda psql -U postgres -c "ALTER USER postgres WITH PASSWORD 'postgres123';"
```

### 3. Sobe toda a stack

```bash
docker compose up --build
```

### 4. Verifica os serviços

Acessa `http://localhost:8761` para ver todos os serviços registrados no Eureka.

## Config Repo

As configurações centralizadas estão em repositório separado:
https://github.com/guilhermes14/guilda-config-repo

Contém os arquivos:
- `guilda-app.yml` — configurações do guilda-app
- `guilda-app-dev.yml` — configurações de desenvolvimento
- `guilda-app-prod.yml` — configurações de produção
- `ranking-service-dev.yml` — configurações de desenvolvimento do ranking
- `ranking-service-prod.yml` — configurações de produção do ranking

O Config Server lê automaticamente desse repositório ao subir.


### Aventureiros

# Listar com paginação
curl "http://localhost:8080/guilda-app/aventureiros?page=0&size=10"
# Filtrar por classe
curl "http://localhost:8080/guilda-app/aventureiros?classe=GUERREIRO"


### Ranking

# Ranking completo com todos os aventureiros
curl http://localhost:8080/ranking-service/ranking/completo
# Ranking simples
curl http://localhost:8080/ranking-service/ranking
# Buscar aventureiro específico
curl http://localhost:8080/ranking-service/ranking/1


### Usuários e Roles

# Listar usuários com roles e permissions
curl http://localhost:8080/guilda-app/usuarios
# Listar roles com permissions
curl http://localhost:8080/guilda-app/roles


## Troubleshooting

**Erro de senha no banco**
```bash
docker exec -it postgres-guilda psql -U postgres -c "ALTER USER postgres WITH PASSWORD 'postgres123';"
```
**Serviços não aparecem no Eureka:**
Aguarde 30 segundos após subir — o Eureka precisa de tempo para registrar os serviços.

**Ranking perdendo dados após restart:**
Use `docker compose restart ranking-service` em vez de `docker compose down` para preservar os dados.

**Porta 5432 já em uso:**
Verifique se há um PostgreSQL nativo rodando no Windows e pare o serviço antes de subir o Docker.

## Graylog mostrando logs centralizados

<img width="1172" height="937" alt="image" src="https://github.com/user-attachments/assets/3fc2783c-2d17-4016-a1db-c5a4b128eeb2" />

