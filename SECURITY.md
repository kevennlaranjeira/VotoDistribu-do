# Seguranca

Este projeto foi desenvolvido para fins avaliativos na disciplina de Sistemas Distribuidos da Universidade Federal de Vicosa (UFV).

## Dados sensiveis

Nao publique segredos reais neste repositorio. Arquivos `.env`, chaves privadas, certificados e dumps de banco devem permanecer locais.

Os valores presentes em `.env.example` e nos arquivos `docker-compose.yml` sao credenciais demonstrativas para execucao local. Eles sao publicos por design e devem ser substituidos em qualquer ambiente que nao seja de teste academico.

## Antes de expor em outro ambiente

- Gere uma nova chave `JWT_SECRET`.
- Altere senhas de PostgreSQL e RabbitMQ.
- Restrinja CORS.
- Revise autenticacao e autorizacao.
- Proteja interfaces administrativas, como o RabbitMQ Management.
- Evite publicar portas de banco de dados diretamente.

## Relato de problemas

Por se tratar de um projeto academico, problemas podem ser relatados diretamente por issues no GitHub ou durante a avaliacao da disciplina.
