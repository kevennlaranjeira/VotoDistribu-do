# Frontend

Interface Angular do sistema Voto Distribuido.

Este frontend faz parte de um projeto avaliativo da disciplina de Sistemas Distribuidos da UFV.

## Desenvolvimento local

Instale as dependencias:

```bash
npm ci
```

Execute em modo desenvolvimento:

```bash
npm start
```

A aplicacao abre em `http://localhost:4200`.

## Build

```bash
npm run build
```

## Testes

```bash
npm run test:ci
```

Em producao Docker, o Nginx do frontend encaminha:

- `/api-core` para o core/agregador.
- `/api-coletor` para o backend/coletor.
- `/ws` para o WebSocket do core.
