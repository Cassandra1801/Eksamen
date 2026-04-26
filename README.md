# Bilabonnement.dk - Internt System

## Deployment
- URL: (indsæt Azure URL når deployed)
- Brugernavn: (indsæt)
- Password: (indsæt)

## GitHub Repository
- URL: (indsæt GitHub repo URL)

## Forudsætninger
Følgende skal være installeret for at køre applikationen:
- Java 17+
- Maven
- MySQL

## Installation og opsætning

### Database
1. Opret en MySQL database
2. Kør DDL-scriptet: `src/main/resources/sql/schema.sql`
3. Kør DML-scriptet: `src/main/resources/sql/data.sql`

### Miljøvariabler
Opret en `application-local.properties` fil i `src/main/resources/` med følgende:

spring.datasource.url=jdbc:mysql://localhost:3306/bilabonnement
spring.datasource.username=DIT_BRUGERNAVN
spring.datasource.password=DIT_PASSWORD

### Kør applikationen

mvn spring-boot:run

Applikationen kører på http://localhost:8080
