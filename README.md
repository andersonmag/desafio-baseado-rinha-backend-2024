# Desafio baseado na Rinha Backend 2024
## Tecnologias utilizadas
- Spring(Java 17)
- React 18
- PostgreSQL
- Docker
- Nginx com Load Balancer

## Como rodar o projeto
- Clonar o repositório
- Subir os Containers (tenha o Docker instalado)
    - Na raiz do projeto, execute o seguinte comando para construir e subir os containers:
      `docker-compose up --build`

## Urls de acesso
- Frontend a partir de `http:localhost:3000`
    - Rotas para acessos: `/transacoes` e `/extrato`
- Backend a partir de `http:localhost:9999` (load balancer)
    - Rotas para requisicoes: *POST* `/transacoes` e *GET*  `/extrato`
