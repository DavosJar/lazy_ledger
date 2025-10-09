# Lazy Ledger Backend

Este proyecto implementa la API principal para la gestión de ledgers.

## Documentación de la API

Consulta la documentación de los endpoints de ledgers en [API_LEDGER.md](./API_LEDGER.md).

## Instrucciones de ejecución

1. Asegúrate de tener Java 21+ y Maven instalados en tu sistema.
2. Abre una terminal en la carpeta `core`.
3. Ejecuta el siguiente comando para iniciar la aplicación:

```
./mvnw spring-boot:run
```

O si tienes Maven instalado globalmente:

```
mvn spring-boot:run
```

La aplicación se iniciará en el puerto configurado (por defecto 8090).

## Configuración
Puedes modificar la configuración en el archivo `src/main/resources/application.properties`.
