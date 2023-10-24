# Etapa 1: Construir o aplicativo Java
FROM ubuntu:latest AS build

# Atualizar o sistema e instalar o OpenJDK 17 e o Maven
RUN apt-get update && apt-get install -y openjdk-17-jdk maven
RUN apt install postgresql postgresql-contrib

# Copiar o código-fonte do aplicativo para o contêiner
COPY . /app

# Define o diretório de trabalho para a compilação
WORKDIR /app

# Compilar o aplicativo usando o Maven
RUN mvn clean install

# Etapa 2: Preparar o contêiner final
FROM openjdk:17-jdk-slim

# Copiar o arquivo JAR construído na etapa anterior
COPY --from=build /app/target/todolist-1.0.0.jar /app.jar

# Etapa 3: Configurar o PostgreSQL
FROM postgres:latest

# Definir as variáveis de ambiente para o PostgreSQL
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_DB mydb

RUN postgres createdb sammy

# Expor as portas do PostgreSQL e do aplicativo (8080)
EXPOSE 5432
EXPOSE 8080

# Ponto de entrada para o aplicativo Java
ENTRYPOINT ["java", "-jar", "/app.jar"]
