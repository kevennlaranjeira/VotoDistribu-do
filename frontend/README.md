# Frontend

Interface Angular do sistema Voto Distribuido.

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

O frontend consome:

- `http://localhost:8081` para o core/agregador.
- `http://coletor.local:8080` para o backend/coletor.
