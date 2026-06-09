# Voto Distribuido

Sistema de votacao distribuida desenvolvido para fins avaliativos e de testes na disciplina de Sistemas Distribuidos da Universidade Federal de Vicosa (UFV).

O projeto simula uma eleicao com separacao entre o no coletor, responsavel por receber usuarios e votos, e o no agregador, responsavel por consolidar resultados. A comunicacao entre os servicos usa RabbitMQ, os dados sao persistidos em PostgreSQL e a interface web foi criada em Angular.

## Arquitetura

- `frontend`: aplicacao Angular publicada por Nginx na porta `4200`; tambem faz proxy para o coletor, core e WebSocket.
- `backend`: servico coletor Spring Boot, escalavel em multiplas replicas.
- `core`: servico agregador Spring Boot, responsavel por candidatos, totalizacao e WebSocket.
- `nginx-proxy`: balanceador que recebe `coletor.local:8080` e distribui chamadas para as replicas do coletor.
- `rabbitmq`: broker usado para troca assincrona de mensagens.
- `postgres_core`: banco do agregador.
- `postgres_eleicao`: banco do coletor.

Mais detalhes estao em [docs/ARQUITETURA.md](docs/ARQUITETURA.md).

## Pre-requisitos

- Git
- Docker Desktop ou Docker Engine com Docker Compose
- Opcional para testes locais sem Docker: Java 21+ e Node.js 22+

## Executando com Docker

Clone o repositorio:

```bash
git clone https://github.com/kevennlaranjeira/VotoDistribu-do.git
cd VotoDistribu-do
```

Crie o arquivo de ambiente:

```bash
cp .env.example .env
```

Suba toda a stack com tres replicas do coletor:

```bash
docker compose up -d --build --scale coletor=3
```

Verifique os containers:

```bash
docker compose ps
```

Acesse:

- Frontend: http://localhost:4200
- Coletor balanceado: http://localhost:28080/user/healthCheck
- Agregador: http://localhost:28081/eleicao-gp2/listarCandidatosDesc
- RabbitMQ Management: http://localhost:15672 com usuario `yan` e senha `yan`

As portas publicadas podem ser alteradas no `.env` (`FRONTEND_PORT`, `COLETOR_PORT`, `CORE_PORT` e `RABBITMQ_MANAGEMENT_PORT`) caso alguma delas ja esteja em uso.

Para parar:

```bash
docker compose down
```

Para parar e remover os volumes dos bancos:

```bash
docker compose down -v
```

## Testes e validacao

Backend:

```bash
cd backend
./mvnw test
```

Core:

```bash
cd core
./mvnw test
```

Frontend:

```bash
cd frontend
npm ci
npm run build
npm run test:ci
```

No Windows, use `mvnw.cmd` no lugar de `./mvnw`. Se o Java padrao da maquina for inferior ao 21, aponte `JAVA_HOME` para um JDK 21+ antes de rodar os testes.

O processo completo de verificacao esta em [docs/TESTES.md](docs/TESTES.md).

## Fluxo de demonstracao

1. Suba a stack com `docker compose up -d --build --scale coletor=3`.
2. Abra `http://localhost:4200`.
3. Crie uma conta ou faca login.
4. Liste os candidatos carregados pelo agregador.
5. Vote em um candidato.
6. Observe a atualizacao dos resultados via WebSocket.
7. Acompanhe filas e mensagens no RabbitMQ Management.

## Observacoes

Este projeto usa credenciais simples e uma chave JWT fixa para facilitar a avaliacao academica. Para uso real, substitua os valores do `.env`, proteja segredos e revise as politicas de CORS, autenticacao e persistencia.

O frontend usa proxy interno para falar com os servicos pela propria origem (`/api-coletor`, `/api-core` e `/ws`). Isso evita depender de edicao de `hosts` ou de CORS no navegador. O proxy do coletor tambem aceita o host `coletor.local` para compatibilidade com a configuracao original.
