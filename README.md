# Fatura-RHS

Este é um projeto que lê planilhas normais e CRLV e fornece dois endpoints para fazer upload desses arquivos.

## Endpoints

- `POST /extrato/upload`: Endpoint para fazer upload de planilhas normais.
- `POST /extrato/upload/crlv`: Endpoint para fazer upload de CRLVs.

## Documentação OpenAPI

Você pode acessar a documentação OpenAPI em [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/).

## Tecnologias Utilizadas

- Spring Boot 3.1.1
- Java 17
- PostgreSQL
- Docker 

## Instruções de Execução

Certifique-se de ter o Docker instalados na sua máquina.

Clone o repositório do projeto.
```bash
git clone <URL_DO_REPOSITORIO>
cd <PASTA_DO_PROJETO>
docker-compose up
```
O projeto será iniciado na rota http://localhost:8080

## Envio de arquivos

Upload de Planilhas Normais: Envie uma planilha normal no formato .xlsx para o endpoint localhost:8080/extrato/upload. Certifique-se de incluir o arquivo no corpo da solicitação com a chave 'arquivo'.
Exemplo usando cURL:
```
curl -X POST -F "arquivo=@caminho/para/sua/planilha.xlsx" http://localhost:8080/extrato/upload
```
Upload de CRLV: Envie um arquivo CRLV no formato .xlsx para o endpoint localhost:8080/extrato/upload/crlv. Certifique-se de incluir o arquivo no corpo da solicitação com a chave 'arquivo'.
Exemplo usando cURL:
```
curl -X POST -F "arquivo=@caminho/para/sua/planilha.xlsx" localhost:8080/extrato/upload/crlv
```
