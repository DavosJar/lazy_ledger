# Endpoints Pendientes - Lazy Ledger

## CLIENTE Module - Endpoints Faltantes

### GET /clientes/me
**Caso de Uso:** Ver Mi Perfil
- **Método:** GET
- **Autenticación:** JWT requerida
- **Descripción:** Usuario autenticado obtiene su propio perfil
- **Respuesta:** ClienteDTO con información completa del usuario

### PUT /clientes/me
**Caso de Uso:** Actualizar Mi Perfil
- **Método:** PUT
- **Autenticación:** JWT requerida
- **Descripción:** Usuario actualiza su información personal
- **Cuerpo:** ClienteDTO con campos actualizables
- **Respuesta:** ClienteDTO actualizado

### GET /clientes/search
**Caso de Uso:** Buscar Usuario para Invitación
- **Método:** GET
- **Parámetros:** email (query parameter)
- **Autenticación:** JWT requerida
- **Descripción:** Buscar usuarios existentes por email para invitar a ledgers
- **Respuesta:** Lista simplificada de usuarios encontrados

## LEDGER Module - Endpoints Faltantes

### GET /ledgers/{id}
**Caso de Uso:** Ver Detalles del Ledger
- **Método:** GET
- **Parámetros:** id (path)
- **Autenticación:** JWT requerida
- **Autorización:** Usuario debe ser miembro del ledger
- **Descripción:** Obtener información completa del ledger (nombre, descripción, miembros, configuración)
- **Respuesta:** LedgerDTO con detalles completos

### PUT /ledgers/{id}
**Caso de Uso:** Gestionar Configuración del Ledger
- **Método:** PUT
- **Parámetros:** id (path)
- **Autenticación:** JWT requerida
- **Autorización:** Solo propietario puede modificar
- **Descripción:** Actualizar nombre, descripción y configuración del ledger
- **Cuerpo:** LedgerUpdateRequest
- **Respuesta:** LedgerDTO actualizado

### DELETE /ledgers/{id}
**Caso de Uso:** Eliminar Ledger
- **Método:** DELETE
- **Parámetros:** id (path)
- **Autenticación:** JWT requerida
- **Autorización:** Solo propietario puede eliminar
- **Descripción:** Eliminar permanentemente un ledger y todas sus membresías
- **Respuesta:** 204 No Content

### GET /ledgers/{id}/dashboard
**Caso de Uso:** Dashboard del Ledger
- **Método:** GET
- **Parámetros:** id (path)
- **Autenticación:** JWT requerida
- **Autorización:** Usuario debe ser miembro
- **Descripción:** Resumen financiero, actividad reciente, estado de miembros
- **Respuesta:** LedgerDashboardDTO

### GET /ledgers/{id}/members
**Caso de Uso:** Gestionar Miembros
- **Método:** GET
- **Parámetros:** id (path)
- **Autenticación:** JWT requerida
- **Autorización:** Usuario debe ser miembro
- **Descripción:** Lista de miembros con roles y estado
- **Respuesta:** Lista de MemberDTO

### GET /ledgers/{id}/activity
**Caso de Uso:** Ver Actividad del Ledger
- **Método:** GET
- **Parámetros:** id (path)
- **Autenticación:** JWT requerida
- **Autorización:** Usuario debe ser miembro
- **Descripción:** Actividad reciente (transacciones, cambios de miembros)
- **Respuesta:** Lista de ActivityDTO

## TRANSACTION Module - Endpoints Faltantes

### GET /transacciones/ledger/{ledgerId}
**Caso de Uso:** Ver Transacciones del Ledger
- **Método:** GET
- **Parámetros:** ledgerId (path), filtros opcionales (fecha, categoría, tipo, monto)
- **Autenticación:** JWT requerida
- **Autorización:** Usuario debe ser miembro del ledger
- **Descripción:** Lista paginada de transacciones del ledger con filtros
- **Respuesta:** PagedResponse<TransaccionDTO>

### GET /transacciones/ledger/{ledgerId}/summary
**Caso de Uso:** Resumen Financiero
- **Método:** GET
- **Parámetros:** ledgerId (path), período opcional
- **Autenticación:** JWT requerida
- **Autorización:** Usuario debe ser miembro
- **Descripción:** Totales por categoría, balance mensual, estadísticas
- **Respuesta:** FinancialSummaryDTO

### GET /transacciones/ledger/{ledgerId}/search
**Caso de Uso:** Buscar Transacciones
- **Método:** GET
- **Parámetros:** ledgerId (path), criterios de búsqueda avanzada
- **Autenticación:** JWT requerida
- **Autorización:** Usuario debe ser miembro
- **Descripción:** Búsqueda avanzada con rangos de fecha, montos, categorías
- **Respuesta:** PagedResponse<TransaccionDTO>

### GET /transacciones/ledger/{ledgerId}/categories/stats
**Caso de Uso:** Análisis de Gastos
- **Método:** GET
- **Parámetros:** ledgerId (path), período opcional
- **Autenticación:** JWT requerida
- **Autorización:** Usuario debe ser miembro
- **Descripción:** Estadísticas por categoría de gastos/ingresos
- **Respuesta:** CategoryStatsDTO

## MIEMBRO LEDGER Module - Endpoints Faltantes

### GET /miembros-ledger/invitations/pending
**Caso de Uso:** Ver Invitaciones Pendientes
- **Método:** GET
- **Autenticación:** JWT requerida
- **Descripción:** Usuario ve invitaciones pendientes de respuesta
- **Respuesta:** Lista de InvitationDTO

### POST /miembros-ledger/invitations/{id}/accept
**Caso de Uso:** Aceptar Invitación
- **Método:** POST
- **Parámetros:** id (invitation ID)
- **Autenticación:** JWT requerida
- **Autorización:** Invitación debe ser para el usuario autenticado
- **Descripción:** Usuario acepta invitación y se une al ledger
- **Respuesta:** MemberDTO

### POST /miembros-ledger/invitations/{id}/reject
**Caso de Uso:** Rechazar Invitación
- **Método:** POST
- **Parámetros:** id (invitation ID)
- **Autenticación:** JWT requerida
- **Autorización:** Invitación debe ser para el usuario autenticado
- **Descripción:** Usuario rechaza invitación
- **Respuesta:** 200 OK

### GET /miembros-ledger/my-role/{ledgerId}
**Caso de Uso:** Verificar Mis Permisos
- **Método:** GET
- **Parámetros:** ledgerId (path)
- **Autenticación:** JWT requerida
- **Descripción:** Usuario ve su rol y permisos en un ledger específico
- **Respuesta:** RolePermissionsDTO

---

## Notas de Implementación

### Consideraciones Generales
- Todos los endpoints requieren autenticación JWT
- Implementar validación de permisos basada en roles (PROPIETARIO, MIEMBRO)
- Usar paginación consistente para listas
- Manejo de errores estandarizado
- Documentación Swagger completa

### Priorización
1. **Alta Prioridad:** Endpoints de perfil de usuario y gestión básica de ledgers
2. **Media Prioridad:** Endpoints de transacciones y búsqueda
3. **Baja Prioridad:** Endpoints de análisis y estadísticas avanzadas

### Validaciones de Seguridad
- Verificar membresía en ledger antes de acceder a recursos
- Validar permisos de propietario para operaciones críticas
- Sanitizar todos los parámetros de entrada
- Implementar rate limiting para endpoints públicos