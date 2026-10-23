# Noban

O **Noban** é uma API para gerenciamento de projetos baseada no conceito de
Kanban, com recursos de autenticação, autorização, persistência de dados e
gerenciamento de projetos, colaboradores, etapas e tarefas.

## Tecnologias

- Java 21 e Spring Boot
- Spring Security e JWT
- Spring Data JPA / Hibernate
- PostgreSQL e Flyway
- Maven
- Docker e Docker Compose (opcional, para executar o banco e/ou a API)

## Requisitos

- Java 21
- OpenSSL, para gerar as chaves JWT (ou chaves próprias configuradas por
  variáveis de ambiente)
- Docker com o plugin Docker Compose, se for usar o banco ou a API em containers

O Maven Wrapper (`api/mvnw`) já está incluído; não é necessário instalar Maven.

## Configuração inicial

Os comandos abaixo partem da raiz do repositório.

1. Crie o arquivo de configuração local:

   ```bash
   cp .env.example .env
   ```

2. Preencha no `.env` os valores usados pelo Docker Compose e pela aplicação.
   Para um ambiente local de desenvolvimento, por exemplo:

   ```dosini
   POSTGRES_USER=
   POSTGRES_PASSWORD=
   POSTGRES_DB=
   NOBAN_VERSION=
   DATABASE_URL=
   DATABASE_USERNAME=
   DATABASE_PASSWORD=
   JWT_PRIVATE_KEY=
   JWT_PUBLIC_KEY=
   JWT_ALGORITHM=
   JWT_ISSUER=
   JWT_ACCESS_EXPIRATION_TIME=
   JWT_REFRESH_EXPIRATION_TIME=
   ALLOWED_ORIGIN=
   ALLOWED_METHOD=
   ALLOWED_HEADER=
   ```

   Esses valores são apenas para desenvolvimento local; use credenciais seguras
   em outros ambientes. O arquivo `.env` não é carregado automaticamente pelo
   Spring Boot: para executar localmente, exporte suas variáveis para o
   ambiente, conforme mostrado na seção abaixo. O Docker Compose lê `.env`
   automaticamente para interpolar a configuração do `compose.yaml`.

3. Gere um par de chaves EC para JWT. A aplicação usa os caminhos
   `classpath:private.pem` e `classpath:public.pem` por padrão, então gere os
   arquivos dentro dos recursos da API:

   ```bash
   cd api/src/main/resources
   openssl ecparam -name prime256v1 -genkey -noout -out private.ec.pem
   openssl pkcs8 -topk8 -in private.ec.pem -nocrypt -out private.pem
   openssl ec -in private.pem -pubout -out public.pem
   cd ../../..
   ```

   As chaves são ignoradas pelo Git. Não compartilhe nem versione a chave
   privada. Se usar chaves em outro local, configure `JWT_PRIVATE_KEY` e
   `JWT_PUBLIC_KEY` com caminhos acessíveis pelo processo da API; para Docker,
   esses arquivos também precisam estar disponíveis dentro do container.

## Executar a API localmente

Esta opção roda a API com Java e pode usar PostgreSQL local ou o banco iniciado
por Docker Compose.

1. Configure `DATABASE_URL` no `.env` com o endereço acessível a partir da sua
   máquina. Com o banco deste Compose, use
   `jdbc:postgresql://localhost:5432/noban`.
2. Inicie o banco na raiz do repositório:

   ```bash
   docker compose up -d database
   ```

   Aguarde o PostgreSQL ficar pronto antes de iniciar a API; consulte
   `docker compose logs -f database` e, ao ver a mensagem de que está aceitando
   conexões, encerre apenas o acompanhamento com `Ctrl+C`.

   Se usar outro PostgreSQL, pule esta etapa e ajuste as credenciais e o endereço
   no `.env`.
3. Carregue as variáveis do `.env` no terminal e inicie a API:

   ```bash
   cd api
   set -a
   . ../.env
   set +a
   ./mvnw spring-boot:run
   ```

   A API ficará disponível em `http://localhost:8080`. O processo permanece
   ativo nesse terminal; use `Ctrl+C` para encerrá-lo.

## Executar API e banco com Docker Compose

O Compose sobe a API e o PostgreSQL. Antes, gere as chaves como descrito em
[Configuração inicial](#configuração-inicial) e empacote a API: o Dockerfile
usa o JAR em `api/target` e não compila o projeto durante a criação da imagem.

1. No `.env`, configure `DATABASE_URL` com o nome do serviço do banco dentro da
   rede Docker:

   ```dotenv
   DATABASE_URL=jdbc:postgresql://db:5432/noban
   ```

   Garanta também que `DATABASE_USERNAME`/`DATABASE_PASSWORD` correspondam a
   `POSTGRES_USER`/`POSTGRES_PASSWORD`.
2. Empacote a API a partir da raiz:

   ```bash
   cd api
   ./mvnw clean package
   cd ..
   ```

3. Inicie o banco e, em seguida, a API:

   ```bash
   docker compose up -d database
   docker compose up -d --build api
   ```

   Se a API iniciar antes de o PostgreSQL aceitar conexões, aguarde o banco
   ficar pronto pelos logs (`docker compose logs -f db`) e inicie a API
   novamente com `docker compose up -d api`.

   O Compose passa para a API as variáveis de banco e a versão. As configurações
   JWT e CORS restantes usam os valores padrão da aplicação; por isso, os
   arquivos de chave gerados precisam estar incluídos no JAR antes do build da
   imagem.
4. Consulte o estado e os logs:

   ```bash
   docker compose ps
   docker compose logs -f api
   ```

   Para acompanhar o banco, use `docker compose logs -f db`. Encerre o
   acompanhamento com `Ctrl+C`; isso não encerra os containers.
5. Para parar os serviços:

   ```bash
   docker compose down
   ```

   Os dados do PostgreSQL são mantidos no volume `noban-data`. Para removê-los
   também, execute `docker compose down -v` (isso apaga os dados persistidos).

## Verificar a API

Com a aplicação em execução, consulte o endpoint de versão:

```bash
curl http://localhost:8080/api/version
```

Uma resposta JSON com o nome e a versão da API confirma que ela está
respondendo.

## Executar os testes

Os testes usam Testcontainers para iniciar um PostgreSQL. É necessário ter o
Docker em execução:

```bash
cd api
./mvnw test
```
