FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .

# Instalar dependencias (esto solo se ejecuta si pom.xml cambia)
RUN mvn dependency:go-offline

# Copiar el resto del código fuente
COPY src ./src

# El comando que se ejecutará para iniciar la app.
# spring-boot:run detectará los cambios en los archivos que montemos.
CMD ["mvn", "spring-boot:run"]