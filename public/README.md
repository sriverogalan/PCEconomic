## PCEconomic

Aquesta es la part publica del projecte de la botiga online PCEconomic.

## Dependencies

- Maven
- Java 17
- MySQL
- Tomcat Server

## Instal·lació

- Clonar el repositori
- Importar el projecte a l'IDE
- Crear una base de dades amb el nom "pceconomic"
- Crear un fitxer anomenat `application.properties` dins el directori `src/main/resources` folder
- Afegir els següents paràmetres al fitxer `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Autors

- Miquel Angel Amengual Sastre
- Sergi Rivero Galan