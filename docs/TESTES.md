# Testes e validacao

## Validacao rapida

Na raiz do repositorio:

```bash
docker compose config
```

Esse comando valida a sintaxe do Compose sem subir containers.

## Testes Java

Os testes dos servicos Spring Boot usam H2 em memoria para carregar o contexto sem depender de PostgreSQL ou RabbitMQ externos.

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

No Windows:

```powershell
cd backend
.\mvnw.cmd test

cd ..\core
.\mvnw.cmd test
```

Se houver erro `release version 21 not supported`, o Java ativo e inferior ao necessario. Configure um JDK 21+:

```powershell
$env:JAVA_HOME='C:\caminho\para\jdk-21'
$env:Path="$env:JAVA_HOME\bin;$env:Path"
```

## Testes e build do frontend

```bash
cd frontend
npm ci
npm run build
npm run test:ci
```

`npm run test:ci` executa o Karma em `ChromeHeadless`.

## Validacao manual da stack

Suba tudo:

```bash
docker compose up -d --build --scale coletor=3
```

Confira:

```bash
docker compose ps
docker compose logs -f agregador
docker compose logs -f coletor
```

Teste endpoints:

```bash
curl http://localhost:28080/user/healthCheck
curl http://localhost:28081/eleicao-gp2/listarCandidatosDesc
```

Se alguma porta publicada estiver ocupada, ajuste os valores no `.env` antes de subir a stack.

Resultado esperado:

- O health check do coletor deve retornar `Vivo`.
- A listagem de candidatos deve retornar JSON.
- O frontend deve abrir em `http://localhost:4200`.
- O RabbitMQ Management deve abrir em `http://localhost:15672`.
- O login do RabbitMQ Management usa as credenciais demonstrativas do `.env`: `voto_dev` / `voto_dev_password`.

## Varredura basica para publicacao

Antes de publicar alteracoes, rode uma busca simples por valores sensiveis:

```bash
git grep -n -I -E "(password|senha|secret|token|api[ _-]?key|private[ _-]?key|BEGIN (RSA|OPENSSH|PRIVATE))" -- . ':!frontend/package-lock.json'
```

Revise manualmente os resultados. Variaveis de ambiente, exemplos publicos e codigo que manipula tokens podem aparecer nessa busca sem serem segredos reais.

## Limpeza

Parar containers preservando dados:

```bash
docker compose down
```

Parar containers removendo bancos e filas persistidas:

```bash
docker compose down -v
```
