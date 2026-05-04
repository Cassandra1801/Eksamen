# Bilabonnement.dk - Internt System

## Deployment
- URL: https://eksamen-production.up.railway.app
- Brugernavn: root
- Password: XRLvUrFECrJVWxRMcfitGVIi0yHsmcui

## GitHub Repository
- URL: https://github.com/Cassandra1801/Eksamen

## Forudsætninger
Følgende skal være installeret for at køre applikationen:
- Java 17+
- Maven
- MySQL

## Installation og opsætning

### Database
1. Opret en MySQL database
2. Kør DDL-scriptet: `src/main/resources/schema.sql`
3. Kør DML-scriptet: `src/main/resources/data.sql`

### Miljøvariabler
Opret environment variables med følgende:

SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/bilabonnement
SPRING_DATASOURCE_USERNAME=DIT_BRUGERNAVN
SPRING_DATASOURCE_PASSWORD=DIT_PASSWORD

### Kør applikationen
mvn spring-boot:run

Applikationen kører på http://localhost:8080
