# Documento de Requisitos - Lazy Ledger

**Versión**: 1.0  
**Fecha**: 23 de octubre de 2025  
**Proyecto**: Sistema Inteligente de Asistencia Financiera Personal y Colaborativa

---

## 1. Introducción

### 1.1 Propósito
Este documento describe los requisitos del **Sistema Lazy Ledger**, una plataforma completa de asistencia financiera que combina capacidades de registro, análisis y consulta de movimientos financieros mediante interfaces conversacionales (chat con IA/LLM) y tradicionales (formularios y navegación web).

### 1.2 Alcance del Proyecto
Lazy Ledger es un **asistente inteligente de finanzas personales y grupales** que:
- Permite registrar movimientos financieros mediante **lenguaje natural** (datos no estructurados) o formularios tradicionales
- Analiza flujos de efectivo sin realizar transacciones financieras reales
- Facilita consultas de información financiera en **lenguaje de alto nivel** mediante chat
- Integra modelos LLM para procesamiento de lenguaje natural
- Conecta con plataformas de chat nativas o de terceros
- Proporciona navegación tradicional mediante interfaz web
- Gestiona libros contables (ledgers) compartidos entre múltiples personas

**Nota importante**: El sistema NO es una plataforma de interacción financiera (no mueve dinero real). Es un **asistente de análisis y control** de flujo de efectivo.

### 1.3 Componentes del Sistema
- **Backend API REST**: Servicios de autenticación, gestión de datos y lógica de negocio
- **Frontend Web**: Interfaz de usuario con formularios y navegación tradicional
- **Módulo de IA/Chat**: Integración con LLMs para procesamiento de lenguaje natural
- **Integraciones**: Conectores con plataformas de chat (nativas o terceros)

### 1.4 Usuarios del Sistema
- **Usuarios individuales**: Personas que desean controlar y analizar sus finanzas personales
- **Grupos colaborativos**: Familias, roommates o grupos que comparten gastos y desean análisis conjunto
- **Creadores de ledgers**: Usuarios que administran libros contables compartidos
- **Miembros**: Participantes con permisos de lectura/escritura en ledgers colaborativos

### 1.5 Definiciones
- **Ledger**: Libro contable que agrupa registros de movimientos financieros compartido por múltiples miembros
- **Cliente**: Usuario registrado en el sistema
- **Miembro**: Usuario asociado a un ledger específico con un rol determinado
- **Movimiento/Registro**: Anotación de ingreso o gasto (no es una transacción bancaria real)
- **Asistente IA**: Interfaz conversacional basada en LLM para registro y consulta en lenguaje natural
- **Datos no estructurados**: Información financiera proporcionada en lenguaje natural (ej: "gasté $50 en el super ayer")
- **Deuda del Ledger**: Compromiso financiero del ledger hacia terceros (proveedores, bancos, servicios) - NO entre miembros

---

## 2. Requisitos Funcionales

| ID | Requisito | Descripción | Prioridad |
|----|-----------|-------------|-----------|
| **RF-001** | Registro de usuarios | El sistema debe permitir crear cuentas con email y contraseña. Debe validar unicidad de email y nombre de usuario. | Alta |
| **RF-002** | Inicio de sesión | Los usuarios deben poder autenticarse con sus credenciales. El sistema debe generar un token de sesión. | Alta |
| **RF-003** | Auditoría de intentos fallidos | El sistema debe registrar todos los intentos de login fallidos con: usuario, IP, agente de usuario, timestamp. | Alta |
| **RF-004** | Bloqueo de cuenta | El sistema debe bloquear cuentas/IPs tras múltiples intentos fallidos consecutivos de autenticación. | Alta |
| **RF-005** | Crear perfil de cliente | Permitir registrar información personal (nombre, apellido, email) con identificador único. | Alta |
| **RF-006** | Consultar clientes | Listar clientes con paginación, filtrado por nombre y ordenamiento. | Media |
| **RF-007** | Actualizar cliente | Modificar datos del perfil de cliente. | Media |
| **RF-008** | Eliminar cliente | Eliminar cliente solo si no tiene ledgers activos. Registrar operación en auditoría. | Baja |
| **RF-009** | Crear ledger | Usuarios autenticados pueden crear libros contables con nombre, descripción y límite de presupuesto opcional. El creador se asocia automáticamente como administrador. | Alta |
| **RF-010** | Listar ledgers | Mostrar solo ledgers donde el usuario es miembro. Soportar paginación, ordenamiento y filtros. | Alta |
| **RF-011** | Consultar detalle de ledger | Mostrar información completa del ledger incluyendo lista de miembros y estadísticas. | Alta |
| **RF-012** | Actualizar ledger | Solo creadores y editores pueden modificar nombre, descripción y límite. | Media |
| **RF-013** | Archivar ledger | Solo el creador puede archivar. Ledgers archivados quedan en modo solo lectura. | Baja |
| **RF-014** | Agregar miembro a ledger | Solo el creador puede invitar miembros asignando rol (EDITOR o SOLO_LECTURA). | Alta |
| **RF-015** | Modificar rol de miembro | Solo el creador puede cambiar roles. No se puede modificar el rol del creador original. | Media |
| **RF-016** | Eliminar miembro | Solo el creador puede remover miembros. No se puede eliminar al creador. | Media |
| **RF-017** | Registrar movimiento (formulario) | Creadores y editores pueden registrar movimientos clasificados como ingreso/gasto con categoría, monto, fecha y descripción. | Alta |
| **RF-018** | Registrar movimiento (lenguaje natural) | Procesar entrada de texto libre (ej: "pagué $50 en uber ayer") extrayendo automáticamente: monto, categoría, fecha, descripción. Solicitar confirmación antes de guardar. | Media |
| **RF-019** | Consultar movimientos (interfaz tradicional) | Mostrar histórico del ledger en tabla paginada con filtros por tipo, categoría, rango de fechas. | Alta |
| **RF-020** | Consultar movimientos (lenguaje natural) | Responder preguntas en lenguaje natural sobre movimientos (ej: "¿cuánto gasté en transporte este mes?"). | Media |
| **RF-021** | Actualizar movimiento | Solo quien creó el movimiento o el creador del ledger puede editar. Validar que el ledger esté activo. | Media |
| **RF-022** | Eliminar movimiento | Solo quien creó el movimiento o el creador del ledger puede eliminar. Registrar operación en logs. | Baja |
| **RF-023** | Crear objetivo financiero | Creadores y editores pueden definir objetivos especificando tipo (ahorro/límite de gasto), monto y fecha límite. | Media |
| **RF-024** | Consultar objetivos | Listar objetivos del ledger mostrando porcentaje de cumplimiento y filtros por estado. | Media |
| **RF-025** | Actualizar objetivo | Modificar parámetros del objetivo. Solo creadores y editores. | Baja |
| **RF-026** | Completar objetivo | Marcar como completado cuando se alcance la meta. | Baja |
| **RF-027** | Registrar deuda del ledger | Creadores y editores pueden registrar deudas del ledger especificando acreedor (tercero externo), monto, fecha de vencimiento y concepto. | Media |
| **RF-028** | Consultar deudas | Listar deudas del ledger con filtros por estado (PENDIENTE/PAGADA) y mostrar total de deuda pendiente. | Media |
| **RF-029** | Liquidar deuda | Marcar deuda como PAGADA registrando fecha de pago. Solo creadores y editores. | Media |
| **RF-030** | Actualizar deuda | Modificar datos de deuda pendiente (monto, fecha, acreedor). Solo creadores y editores. | Baja |
| **RF-031** | Balance general | Calcular y mostrar total de ingresos, gastos, balance neto, desglose por categoría con filtros por período. Incluir total de deudas pendientes. | Alta |
| **RF-032** | Exportar datos | Descargar movimientos y datos en formato CSV aplicando filtros. | Baja |
| **RF-033** | Integración con LLM | Conectar con modelos de lenguaje natural para procesar datos no estructurados, extraer intención/entidades financieras y generar respuestas conversacionales. | Media |
| **RF-034** | Conector de plataformas de chat | Integración con chat nativo y plataformas de terceros (WhatsApp, Telegram, Slack) manteniendo contexto multi-turno. | Media |
| **RF-035** | Procesamiento de lenguaje natural | Interpretar comandos en lenguaje natural, reconocer entidades (montos, fechas, categorías), manejar ambigüedad. | Media |
| **RF-036** | Documentación de API | Proporcionar documentación interactiva con ejemplos de uso y capacidad de pruebas. | Alta |

---

## 3. Requisitos No Funcionales

| ID | Categoría | Requisito | Descripción | Criterio de Aceptación |
|----|-----------|-----------|-------------|------------------------|
| **RNF-001** | Rendimiento | Tiempo de respuesta | Las respuestas del sistema deben ser rápidas. | Demostrable en uso normal |
| **RNF-002** | Seguridad | Protección de contraseñas | Las contraseñas no deben ser visibles ni recuperables en texto plano. | Verificable en base de datos |
| **RNF-003** | Seguridad | Comunicación protegida | Las comunicaciones deben estar cifradas. | Verificable con inspector de red |
| **RNF-004** | Seguridad | Validación de entrada | Rechazar entradas inválidas o maliciosas. | Demostrable con casos de prueba |
| **RNF-005** | Seguridad | Auditoría de eventos críticos | Registrar intentos de login fallidos y operaciones críticas. | Verificable en logs del sistema |
| **RNF-006** | Seguridad | Control de acceso | Bloquear cuenta tras múltiples intentos fallidos. | Demostrable en flujo de login |
| **RNF-007** | Seguridad | Expiración de sesión | Las sesiones deben expirar después de un período de inactividad. | Demostrable esperando timeout |
| **RNF-008** | Usabilidad | Mensajes comprensibles | Los errores deben ser claros y sin jerga técnica. | Verificable en interfaz |
| **RNF-009** | Usabilidad | Paginación | Listados largos deben estar paginados. | Demostrable con datos de prueba |
| **RNF-010** | Usabilidad | Navegación intuitiva | Facilitar navegación entre recursos relacionados. | Demostrable en uso |
| **RNF-011** | Funcionalidad | Manejo de errores | Todos los errores deben manejarse sin romper la aplicación. | Demostrable con casos extremos |
| **RNF-012** | Funcionalidad | Validación de permisos | Solo usuarios autorizados pueden realizar operaciones según su rol. | Demostrable con diferentes usuarios |
| **RNF-013** | Funcionalidad | Consistencia de datos | Los datos deben ser consistentes tras operaciones CRUD. | Verificable consultando después de modificar |

---

## 4. Restricciones del Negocio

- El sistema **NO realiza transacciones financieras reales** (no mueve dinero)
- El sistema **NO se conecta con bancos** o instituciones financieras
- Los datos financieros son sensibles y requieren protección adecuada
- Debe cumplir con normativas de protección de datos personales
- Debe informar al usuario cuando interactúa con IA (transparencia)

---

**Fin del Documento**