# Mini-Dossier Técnico API RESTful - LazyLedger

**Proyecto:** LazyLedger MVP
**Autores:** César López, Alexis Java, César Ramos
**Asignatura:** Desarrollo Basado en Plataformas
**Profesor:** Ing. Edison Coronel
**Fecha:** 17/10/2025

## 1. Introducción

**Propósito:** Especificación técnica de la API RESTful de LazyLedger para integración y mantenimiento.

**Objetivo:** Desarrollar un MVP backend que permita gestionar y analizar operaciones financieras colaborativamente mediante interfaces conversacionales (chat) con respuestas contextualizadas.

**Alcance Actual:**
- CRUD completo de Clientes
- Gestión de membresías en Ledgers colaborativos (invitaciones, roles, expulsiones)
- Arquitectura limpia con separación de capas
- Principios RESTful, paginación y validaciones de negocio

**Tecnologías:** Spring Boot, Spring JPA, SpringDoc/Swagger, Docker, Clean Architecture.

## 2. Configuración General

**URL Base:** `http://localhost:8090/`
**Formato:** JSON exclusivamente
**Autenticación:** Pendiente de implementación (headers personalizados planeados)
**Paginación:** `page` (0-based), `size` (máx. 100)

### 2.1. Principios RESTful Implementados

| Principio | Implementación en LazyLedger |
|-----------|------------------------------|
| **Recursos por URIs** | `/clientes`, `/miembros-ledger` - recursos claramente identificados |
| **Métodos HTTP** | GET (lectura), POST (crear), PUT (actualizar), DELETE (eliminar) |
| **Stateless** | No mantiene estado de sesión; cada request es independiente |
| **Representaciones** | JSON consistente para requests y responses |
| **Códigos HTTP** | 200 (OK), 201 (Created), 204 (No Content), 400 (Bad Request), 403 (Forbidden), 404 (Not Found), 409 (Conflict) |
| **HATEOAS** | Implementación parcial con enlaces de navegación en responses |


## 3. Modelos de Datos

### 3.1. Cliente

**ClienteSaveRequest** (creación):
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@example.com",
  "tipo": "PERSONA",
  "telefono": "+593987654321"
}
```

**ClienteDTO** (respuesta):
```json
{
  "id": "01HXXXXXXXXXXXXXXXXXXXXX",
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@example.com",
  "tipo": "PERSONA",
  "telefono": "+593987654321"
}
```

### 3.2. MiembroLedger

| InvitarMiembroRequest | CambiarRolRequest | ExpulsarMiembroRequest |
|------------------------|-------------------|-------------------------|
| `{"clienteId": "...", "ledgerId": "..."}` | `{"clienteId": "...", "ledgerId": "...", "nuevoRol": "MIEMBRO"}` | `{"clienteId": "...", "ledgerId": "..."}` |

| EliminarLedgerRequest | MiembroLedgerDTO |
|-----------------------|------------------|
| `{"ledgerId": "..."}` | `{"clienteId": "...", "ledgerId": "...", "rol": "MIEMBRO", "activo": true}` |
## 4. Endpoints Principales

### 4.1. Clientes

| Método | Endpoint | Descripción | Estado |
|--------|----------|-------------|--------|
| GET | `/clientes` | Lista paginada de clientes | ✅ |
| GET | `/clientes/{id}` | Cliente por ID | ✅ |
| POST | `/clientes` | Crear cliente | ✅ |
| PUT | `/clientes/{id}` | Actualizar cliente | ✅ |
| DELETE | `/clientes/{id}` | Eliminar cliente | ✅ |

**Ejemplo POST /clientes:**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@example.com",
  "tipo": "PERSONA",
  "telefono": "+593987654321"
}
```

### 4.2. Miembros de Ledger

| Método | Endpoint | Descripción | Headers |
|--------|----------|-------------|---------|
| GET | `/miembros-ledger/usuario/{clienteId}/ledgers` | Ledgers del usuario | - |
| POST | `/miembros-ledger/invitar` | Invitar miembro | `X-Solicitante-Id` (planeado) |
| PUT | `/miembros-ledger/cambiar-rol` | Cambiar rol | `X-Solicitante-Id` (planeado) |
| DELETE | `/miembros-ledger/expulsar` | Expulsar miembro | `X-Solicitante-Id` (planeado) |
| GET | `/miembros-ledger/ledger/{ledgerId}/miembros` | Miembros del ledger | - |
| GET | `/miembros-ledger/{clienteId}/{ledgerId}` | Membresía específica | - |
| DELETE | `/miembros-ledger/eliminar-ledger` | Eliminar ledger | `X-Solicitante-Id` (planeado) |

**Ejemplo POST /miembros-ledger/invitar:**
```json
{
  "clienteId": "01HXXXXXXXXXXXXXXXXXXXXX",
  "ledgerId": "01HXXXXXXXXXXXXXXXXXXXXX"
}
```
*Header planeado: `X-Solicitante-Id: [ID-propietario]`*

## 5. Manejo de Errores

**Respuestas de Error Estandarizadas:**
```json
{
  "timestamp": "2025-10-15T19:30:09.2826473",
  "message": "Datos inválidos en el DTO del cliente: El formato del email es inválido",
  "type": "VALIDATION_ERROR"
}
```

**Códigos HTTP Comunes:**
- `400 Bad Request`: Datos inválidos o faltantes
- `403 Forbidden`: Sin permisos para la operación
- `404 Not Found`: Recurso no encontrado
- `409 Conflict`: Conflicto (ej: email duplicado, ya es miembro)

## 6. Arquitectura e Implementación

**Clean Architecture:**
- **Presentación**: Controllers REST, DTOs, mapeo de requests/responses
- **Aplicación**: Use Cases, Facades, coordinación de operaciones
- **Dominio**: Entities, Services, reglas de negocio, excepciones
- **Infraestructura**: Repositories JPA, configuración Spring

**Características Técnicas:**
- UUID v7 para IDs únicos y ordenados temporalmente
- Validaciones de negocio en dominio (independientes del framework)
- Manejo centralizado de excepciones con GlobalExceptionHandler
- Paginación automática con metadatos
- Documentación OpenAPI automática
- Headers de autenticación preparados (no implementados en MVP)

## 7. Testing y Despliegue

**Ejecutar la Aplicación:**
```bash
mvn spring-boot:run
# Acceder en: http://localhost:8090
```

**Docker:**
```bash
docker-compose up -d
```

**Testing Básico (sin autenticación implementada):**
```bash
# Crear cliente
curl -X POST http://localhost:8090/clientes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Test","apellido":"User","email":"test@example.com","tipo":"PERSONA","telefono":"123456789"}'

# Listar clientes
curl http://localhost:8090/clientes
```

## 8. Uso de la API

**Documentación Interactiva:** `http://localhost:8090/swagger-ui.html`

## 9. Conclusión

**Resumen:** API RESTful completa con Clean Architecture, CRUD de clientes, gestión de membresías en ledgers colaborativos, validaciones de negocio y documentación automática.

**Trabajo Futuro:**
- Interfaz conversacional con IA para consultas financieras
- Módulos de transacciones y análisis financiero
- Dashboards y reportes interactivos
- Autenticación JWT y permisos avanzados
- Integración con servicios externos
- Notificaciones en tiempo real
- Tests automatizados y monitoreo