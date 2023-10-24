## 🚀 Tecnologias

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [Insomnia](https://insomnia.rest/)
- [H2 Database](https://www.h2database.com/html/main.html)
- [Spring](https://spring.io/)
- [Postgres](https://www.postgresql.org/)

## 📑 Sobre o Projeto

Este projeto consistem em uma API REST como parte de um desafio tecnico da Elotech. A API foi criada para colocar em pratica os conhecimentose e habilidades.



<br>

### Como instalar

```bash
$ apt-get install openjdk-17-jdk -y
$ apt-get install maven -y
$ mvn clean install
```

## ❓ Como utilizar

### Como inicializar

```bash
$ java -jar target/todolist-1.0.0.jar
```

### Descrição

Api para o gerenciamento de uma Pessoa que tem contatos.

Porta utilizada: 8080

Possue as rotas:
POST - Create new Person: /persons/
PUT - Update Person: /person/{id}
GET - Get One Person: /person/{id}
GET - Get all persons: /person/
DELETE - Delete one person /person/{id}

Na rota get all person é posivel pasar os parametros de filtro ?size=10&page=0 , onde size é a quantidade de pessoas e page é a pagina.
