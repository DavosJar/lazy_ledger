FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia solo el pom.xml para aprovechar cache de dependencias
COPY pom.xml .

# Variables de entorno para mejorar tolerancia de red
ENV MAVEN_OPTS="-Dmaven.resolver.transport.retries=5 \
                -Dmaven.wagon.http.retryHandler.count=5 \
                -Dmaven.wagon.http.connectionTimeout=60000 \
                -Dmaven.wagon.http.readTimeout=60000"

# Intentar hasta 5 veces la descarga offline
RUN set -e; \
    max_retries=5; \
    count=0; \
    until [ $count -ge $max_retries ]; do \
        echo "=== Intento $((count+1))/$max_retries de mvn dependency:go-offline ==="; \
        mvn -B dependency:go-offline && break; \
        echo "Fallo en descarga, reintentando en 15s..."; \
        count=$((count+1)); \
        sleep 15; \
    done

# Copia el código fuente (esto invalida cache solo si cambia src)
COPY src ./src

# Comando por defecto para desarrollo
CMD ["mvn", "spring-boot:run"]
