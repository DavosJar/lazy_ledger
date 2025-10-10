# Lazy Ledger Backend

Este proyecto es una aplicación Spring Boot containerizada con OpenJDK 21 y PostgreSQL 17.3.

## Requisitos Previos

- Docker Desktop instalado y ejecutándose en Windows.
- Puerto 8090 disponible para la aplicación.
- Puerto 5432 disponible para PostgreSQL.

## Construcción y Ejecución

### Paso 1: Construir las Imágenes

Abre la consola

```bash
cd c:/Users/adjar/OneDrive/Escritorio/plataformas/lazy_ledger/backend
```

Construye las imágenes de Docker:

```bash
docker-compose build
```

### Paso 2: Iniciar los Contenedores

Ejecuta los contenedores:

```bash
docker-compose up
```

Esto iniciará:
- La aplicación Spring Boot en http://localhost:8090
- PostgreSQL en el puerto 5432 con la base de datos `lazyledger_db`

### Paso 3: Verificar el Funcionamiento

- La aplicación debería estar disponible en http://localhost:8090
- Los logs de la aplicación se mostrarán en la consola
- El contenedor de la base de datos se llamará `lazyledger-db`
- El contenedor de la aplicación se llamará `lazyledger-backend`

## Configuración

### Base de Datos
- **Host**: db (dentro del contenedor)
- **Puerto**: 5432
- **Base de datos**: lazyledger_db
- **Usuario**: admin
- **Contraseña**: secret

### Aplicación
- **Puerto**: 8090
- **Perfil**: default
- **JPA DDL Auto**: update
- **Dialecto**: PostgreSQL

## Detener los Contenedores

Para detener los contenedores, presiona `Ctrl+C` en la consola o ejecuta:

```bash
docker-compose down
```

Esto también eliminará los volúmenes de datos de PostgreSQL.

## Desarrollo Local

Para desarrollo local sin contenedores, actualiza `application.properties` para usar:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/lazyledger_db
spring.datasource.username=admin
spring.datasource.password=secret
server.port=8090
```

Y ejecuta PostgreSQL localmente con la base de datos `lazyledger_db`.