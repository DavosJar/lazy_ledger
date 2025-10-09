# Documentación de la API - Ledgers

Este documento describe el endpoint principal implementado en la rama `develop` para la gestión de ledgers.

## Endpoints

### GET /ledgers

Devuelve un ledger de ejemplo en el formato estándar de la API.

**Respuesta de ejemplo:**
```json
{
  "data": {
    "data": {
      "id": "<uuid>",
      "name": "My Ledger",
      "description": "This is a sample ledger"
    },
    "self": "/ledgers/<uuid>"
  }
}
```

- `data`: Contiene el recurso principal (`Ledger`) y la URL de referencia (`self`).
- El UUID es generado automáticamente.

## Estructuras de datos

### Ledger
- `id`: UUID del ledger
- `name`: Nombre del ledger
- `description`: Descripción del ledger

### ResourceFormat<T>
- `data`: Objeto de tipo T (en este caso, `Ledger`)
- `self`: URL del recurso

### ApiResponse<T>
- `data`: Objeto de tipo T

---

Para más detalles sobre otros endpoints, consulta la documentación completa o el código fuente en la rama `develop`.

