# Análisis de Vulnerabilidades OWASP Top 10 - Lazy Ledger

**Fecha de Análisis:** 19 de Octubre, 2025  
**Proyecto:** Lazy Ledger Backend  
**Versión Analizada:** 0.0.1-SNAPSHOT  

---

## Resumen Ejecutivo

Se han identificado **7 vulnerabilidades críticas y de alto riesgo** del OWASP Top 10 en la aplicación Lazy Ledger. Estas vulnerabilidades requieren atención inmediata ya que comprometen la seguridad, integridad y disponibilidad del sistema.

---

## 1. 🔴 A01:2021 - Broken Access Control (Control de Acceso Roto)

### Severidad: **CRÍTICA**

### Ubicaciones Afectadas:
- `LedgerController.java` (líneas 39-76, 80-102)
- `MiembroLedgerController.java` (líneas 34-58, 94-130)
- `TransaccionController.java` (todas las operaciones)

### Descripción de la Vulnerabilidad:
La aplicación **NO implementa control de acceso basado en roles** ni validación de autorización a nivel de método. Cualquier usuario autenticado puede:
- Acceder a ledgers de otros usuarios
- Modificar datos de ledgers que no le pertenecen
- Invitar miembros sin ser propietario
- Cambiar roles sin validar permisos

### Evidencia del Código:

```java
// LedgerController.java - Línea 80
@GetMapping("/debug/crear-simple")
public ResponseEntity<ApiResponse<LedgerDTO>> crearLedgerDemoSimple(@RequestParam String clienteId) {
    // NO HAY VALIDACIÓN DE QUE EL USUARIO AUTENTICADO SEA EL clienteId
    // Cualquiera puede crear ledgers para otro usuario
}

// MiembroLedgerController.java - Línea 58
@PostMapping("/invitar")
public ResponseEntity<ApiResponse<MiembroLedgerDTO>> invitarMiembro(
        @RequestBody InvitarMiembroRequest request,
        @RequestHeader("X-Solicitante-Id") String solicitanteId) {
    // NO HAY @PreAuthorize ni validación de que solicitanteId sea el usuario autenticado
    // Un atacante puede falsificar el header X-Solicitante-Id
}
```

### Impacto:
- **Acceso no autorizado** a datos financieros sensibles
- **Manipulación de ledgers** de otros usuarios
- **Escalada de privilegios** mediante falsificación de headers

### Recomendaciones:
1. Implementar `@PreAuthorize` y `@Secured` en todos los endpoints
2. Obtener el usuario autenticado desde `SecurityContext` en lugar de headers/parámetros
3. Validar que el usuario tiene permisos sobre el recurso solicitado
4. Implementar un sistema de autorización basado en roles (PROPIETARIO, MIEMBRO, ADMIN)

```java
@PreAuthorize("hasRole('USER')")
@GetMapping("/{id}")
public ResponseEntity<?> getLedger(@PathVariable String id) {
    String currentUserId = SecurityContextHolder.getContext()
        .getAuthentication().getName();
    // Validar que currentUserId tiene acceso al ledger
}
```

---

## 2. 🔴 A02:2021 - Cryptographic Failures (Fallos Criptográficos)

### Severidad: **CRÍTICA**

### Ubicaciones Afectadas:
- `application.properties` (líneas 25-27)
- `JwtUtils.java` (líneas 17-24)

### Descripción de la Vulnerabilidad:
**Secreto JWT expuesto en texto plano** y **clave débil** que compromete toda la seguridad del sistema de autenticación.

### Evidencia del Código:

```properties
# application.properties - Línea 26
app.jwt.secreto=mySecretKey12345678901234567890123456789012345678901234567890
app.jwt.expiracion=86400000
```

```java
// JwtUtils.java - Línea 18
@Value("${app.jwt.secret}")
private String jwtSecret;  // Clave débil y versionada en Git

private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes());
}
```

### Impacto:
- **Falsificación de tokens JWT** por cualquier atacante que acceda al repositorio
- **Compromiso total del sistema de autenticación**
- **Suplantación de identidad** de cualquier usuario
- **Acceso no autorizado** a todos los recursos protegidos

### Recomendaciones:
1. **NUNCA** almacenar secretos en archivos de configuración versionados
2. Utilizar variables de entorno o sistemas de gestión de secretos (HashiCorp Vault, AWS Secrets Manager)
3. Generar claves criptográficamente seguras de al menos 256 bits
4. Rotar claves periódicamente
5. Implementar múltiples entornos con secretos diferentes

```properties
# application.properties
app.jwt.secret=${JWT_SECRET:}  # Forzar variable de entorno
```

```bash
# Variable de entorno
export JWT_SECRET=$(openssl rand -base64 64)
```

---

## 3. 🟠 A03:2021 - Injection (Inyección)

### Severidad: **ALTA**

### Ubicaciones Afectadas:
- Aunque se usa JPA/Hibernate, no se encontraron `@Query` con validación explícita
- Búsquedas dinámicas en `LedgerController` línea 101

### Descripción de la Vulnerabilidad:
Si bien JPA protege contra SQL Injection, existen riesgos potenciales:
- **Falta de sanitización** en parámetros de búsqueda
- **No se valida** el input del usuario antes de construir consultas dinámicas
- **Posible LDAP/NoSQL injection** si se expanden funcionalidades

### Evidencia del Código:

```java
// LedgerController.java - Línea 101
@GetMapping
public ResponseEntity<PagedResponse<LedgerDTO>> listarLedgersDeCliente(
        @RequestParam String clienteId,
        @RequestParam(value = "nombre", required = false) String nombre,
        // Parámetro "nombre" NO es sanitizado antes de pasar a la capa de datos
        @RequestParam(value = "sortBy", defaultValue = "nombre") String sortBy) {
    // ¿Qué pasa si sortBy = "nombre; DROP TABLE ledgers--"?
}
```

### Impacto:
- **SQL Injection** en consultas nativas si se agregan en el futuro
- **Denegación de servicio** mediante parámetros maliciosos
- **Exposición de información** mediante manipulación de criterios de ordenamiento

### Recomendaciones:
1. Validar y sanitizar TODOS los inputs del usuario
2. Usar lista blanca para parámetros de ordenamiento
3. Implementar límites de longitud en campos de texto
4. Usar Prepared Statements incluso con JPA

```java
private static final Set<String> ALLOWED_SORT_FIELDS = 
    Set.of("nombre", "fechaCreacion", "estado");

@GetMapping
public ResponseEntity<?> listar(@RequestParam String sortBy) {
    if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
        throw new ValidationException("Campo de ordenamiento inválido");
    }
    // proceder...
}
```

---

## 4. 🟠 A04:2021 - Insecure Design (Diseño Inseguro)

### Severidad: **ALTA**

### Ubicaciones Afectadas:
- `ConfiguracionSeguridad.java` (líneas 31-34, 75-82)
- Arquitectura general del sistema de autenticación

### Descripción de la Vulnerabilidad:
**Seguridad completamente deshabilitada por defecto** mediante un switch configurable, violando el principio de "seguro por diseño".

### Evidencia del Código:

```java
// ConfiguracionSeguridad.java - Línea 32
@Value("${app.seguridad.habilitada:false}")
private boolean seguridadHabilitada;  // FALSE por defecto!!

@Bean
public SecurityFilterChain cadenaFiltroSeguridad(HttpSecurity http) throws Exception {
    if (!seguridadHabilitada) {
        // TODA LA APLICACIÓN EXPUESTA SIN AUTENTICACIÓN
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll());
        return http.build();
    }
    // ...
}
```

```properties
# application.properties - Línea 24
app.seguridad.habilitada=false
```

### Impacto:
- **Acceso público** a TODOS los endpoints sin autenticación
- **Exposición de datos sensibles** sin protección
- **Configuración peligrosa** que puede pasar a producción
- **Falso sentido de seguridad** durante el desarrollo

### Recomendaciones:
1. **ELIMINAR** el switch de seguridad completamente
2. Seguridad habilitada SIEMPRE en todos los entornos
3. Usar perfiles de Spring para diferenciar entornos
4. Implementar autenticación básica incluso en desarrollo
5. Aplicar el principio de "fail-secure" (fallar de forma segura)

```java
// ELIMINAR el boolean seguridadHabilitada
// Seguridad SIEMPRE activa
@Bean
public SecurityFilterChain cadenaFiltroSeguridad(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated());
    return http.build();
}
```

---

## 5. 🟠 A05:2021 - Security Misconfiguration (Configuración Errónea de Seguridad)

### Severidad: **ALTA**

### Ubicaciones Afectadas:
- `docker-compose.yml` (líneas 6-8)
- `application.properties` (líneas 10-12)
- `GlobalExceptionHandler.java` (líneas 110-116)
- `JwtUtils.java` (líneas 75-85)

### Descripción de la Vulnerabilidad:
Múltiples configuraciones inseguras que exponen información sensible y facilitan ataques.

### Evidencia del Código:

```yaml
# docker-compose.yml - Credenciales por defecto
environment:
  POSTGRES_DB: lazy_ledger_db
  POSTGRES_USER: postgres
  POSTGRES_PASSWORD: password  # Contraseña débil!!
```

```properties
# application.properties
spring.datasource.username=postgres
spring.datasource.password=password  # En texto plano en el código
```

```java
// GlobalExceptionHandler.java - Línea 112
@ExceptionHandler(Exception.class)
public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
    body.put("message", "Error interno del servidor");
    // NO se loguea el error real, dificulta debugging
    // Pero tampoco debe exponerse al cliente
}
```

```java
// JwtUtils.java - Línea 77
catch (MalformedJwtException e) {
    System.err.println("Invalid JWT token: " + e.getMessage());
    // Uso de System.err en lugar de logging framework
    // No hay auditoría ni trazabilidad
}
```

### Impacto:
- **Credenciales comprometidas** en repositorio público
- **Falta de logging** para auditoría de seguridad
- **Información sensible** expuesta en logs de consola
- **Configuración por defecto** insegura en producción

### Recomendaciones:
1. Usar variables de entorno para credenciales
2. Implementar logging estructurado con SLF4J
3. Configurar diferentes niveles de log por entorno
4. Implementar auditoría de eventos de seguridad
5. Usar secretos cifrados en Kubernetes/Docker Secrets

```properties
# application.properties
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
logging.level.com.lazyledger.backend.security=INFO
```

```java
// Usar SLF4J
private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

public boolean validateJwtToken(String authToken) {
    try {
        // validación...
    } catch (MalformedJwtException e) {
        logger.warn("Intento de autenticación con token inválido desde IP: {}", 
            request.getRemoteAddr());
    }
}
```

---

## 6. 🟡 A09:2021 - Security Logging and Monitoring Failures

### Severidad: **MEDIA-ALTA**

### Ubicaciones Afectadas:
- `JwtUtils.java` (líneas 75-85)
- `GlobalExceptionHandler.java` (todo el archivo)
- Ausencia de auditoría en operaciones críticas

### Descripción de la Vulnerabilidad:
**Falta de logging de seguridad** y **ausencia de monitoreo** de eventos críticos como:
- Intentos de autenticación fallidos
- Modificaciones de permisos
- Accesos a recursos sensibles
- Cambios en configuración

### Evidencia del Código:

```java
// JwtUtils.java - Uso de System.err en lugar de logging
public boolean validateJwtToken(String authToken) {
    try {
        // validación...
    } catch (MalformedJwtException e) {
        System.err.println("Invalid JWT token: " + e.getMessage());
        // NO se registra: IP, timestamp, usuario, contexto
    }
    return false;
}

// ServicioAutenticacion.java - Sin logging de intentos fallidos
public RespuestaJwt autenticarUsuario(SolicitudLogin solicitudLogin) {
    try {
        Authentication autenticacion = administradorAutenticacion.authenticate(...);
        // Sin log de autenticación exitosa
    } catch (BadCredentialsException e) {
        // Sin log de intento fallido (posible ataque de fuerza bruta)
        throw new AutenticacionException("Nombre de usuario o contraseña incorrectos");
    }
}
```

### Impacto:
- **Imposible detectar ataques** en curso
- **Brechas de seguridad** pueden pasar desapercibidas
- **Incumplimiento** de normativas (GDPR, PCI-DSS requieren auditoría)

### Recomendaciones:
1. Implementar logging estructurado con SLF4J + Logback
2. Registrar todos los eventos de seguridad
3. Implementar correlación de eventos con IDs de traza
4. Integrar con SIEM (Security Information and Event Management)
5. Configurar alertas automáticas para eventos sospechosos

```java
private static final Logger securityLogger = 
    LoggerFactory.getLogger("SECURITY_AUDIT");

public RespuestaJwt autenticarUsuario(SolicitudLogin solicitudLogin) {
    try {
        Authentication auth = authenticationManager.authenticate(...);
        securityLogger.info("Autenticación exitosa - Usuario: {}, IP: {}", 
            solicitudLogin.nombreUsuario(), getClientIP());
        return generarRespuesta(auth);
    } catch (BadCredentialsException e) {
        securityLogger.warn("Intento fallido de autenticación - Usuario: {}, IP: {}", 
            solicitudLogin.nombreUsuario(), getClientIP());
        throw new AutenticacionException("Credenciales inválidas");
    }
}

// En cada operación crítica
securityLogger.info("CAMBIO_ROL - Ledger: {}, Usuario: {}, RolNuevo: {}, Modificador: {}", 
    ledgerId, userId, newRole, currentUser);
```

---

## 7. 🟡 A10:2021 - Server-Side Request Forgery (SSRF)

### Severidad: **MEDIA**

### Ubicaciones Afectadas:
- Potencial en futuras integraciones
- Falta de validación de URLs en configuración

### Descripción de la Vulnerabilidad:
Aunque actualmente no hay endpoints que hagan requests externos, la arquitectura no previene SSRF:
- No hay validación de URLs si se agregan integraciones
- CORS configurado con patterns demasiado amplios
- Falta de lista blanca de servicios externos

### Evidencia del Código:

```java
// ConfiguracionSeguridad.java - Línea 60
CorsConfiguration configuration = new CorsConfiguration();
configuration.setAllowedOriginPatterns(List.of("http://localhost:*"));
// Permite CUALQUIER puerto en localhost - demasiado permisivo
```

### Impacto:
- **Acceso a servicios internos** no expuestos
- **Escaneo de red interna** desde el servidor
- **Bypass de firewalls** internos
- **Acceso a metadata de instancias** en cloud (AWS, GCP)

### Recomendaciones:
1. Validar y sanitizar todas las URLs antes de hacer requests
2. Implementar lista blanca de dominios permitidos
3. Usar DNS interno que no resuelva IPs privadas
4. Configurar CORS de forma restrictiva
5. Implementar timeout y límites en requests externos

```java
private static final Set<String> ALLOWED_HOSTS = 
    Set.of("api.trusted-service.com", "webhook.example.com");

public void makeExternalRequest(String url) {
    URL parsedUrl = new URL(url);
    if (!ALLOWED_HOSTS.contains(parsedUrl.getHost())) {
        throw new SecurityException("Host no permitido");
    }
    if (isPrivateIP(parsedUrl.getHost())) {
        throw new SecurityException("Acceso a IP privada no permitido");
    }
    // proceder con request...
}

// CORS más restrictivo
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of(
        "http://localhost:3000",  // Frontend específico
        "https://app.lazyledger.com"  // Producción
    ));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    // NO usar allowCredentials con allowedOrigins wildcard
    return source;
}
```

---

## Vulnerabilidades Adicionales Detectadas

### 8. 🔴 Rate Limiting Ausente
**No existe protección contra ataques de fuerza bruta** en endpoints de autenticación.

```java
@PostMapping("/signin")
public ResponseEntity<RespuestaJwt> autenticarUsuario(@Valid @RequestBody SolicitudLogin solicitudLogin) {
    // Sin rate limiting - permite intentos ilimitados
}
```

**Recomendación:** Implementar Bucket4j o similar para limitar intentos.

---

### 9. 🟠 Falta de CSRF Protection
Aunque CSRF está deshabilitado para APIs stateless, **falta protección para sesiones con cookies**.

```java
http.csrf(AbstractHttpConfigurer::disable)  // Deshabilitado completamente
```

**Recomendación:** Si se usan cookies, implementar tokens CSRF.

---

## Resumen de Prioridades

| Vulnerabilidad | Severidad | Prioridad | Esfuerzo |
|---------------|-----------|-----------|----------|
| A01 - Broken Access Control | CRÍTICA | P0 | Alto |
| A02 - Cryptographic Failures | CRÍTICA | P0 | Medio |
| A04 - Insecure Design | ALTA | P1 | Medio |
| A05 - Security Misconfiguration | ALTA | P1 | Bajo |
| A03 - Injection | ALTA | P2 | Medio |
| A09 - Logging Failures | MEDIA | P2 | Bajo |
| A10 - SSRF | MEDIA | P3 | Bajo |

---

## Plan de Remediación Recomendado

### Fase 1 (Inmediata - 1-2 semanas)
1. ✅ Habilitar seguridad permanentemente
2. ✅ Mover secretos JWT a variables de entorno
3. ✅ Implementar control de acceso con @PreAuthorize
4. ✅ Eliminar credenciales hardcodeadas

### Fase 2 (Corto plazo - 2-4 semanas)
5. ✅ Implementar logging de seguridad completo
6. ✅ Validar y sanitizar inputs
7. ✅ Configurar rate limiting
8. ✅ Auditoría de operaciones críticas

### Fase 3 (Mediano plazo - 1-2 meses)
9. ✅ Implementar sistema de roles granular
10. ✅ Configurar SIEM/monitoreo
11. ✅ Implementar testing de seguridad automatizado
12. ✅ Realizar pentesting externo

---

## Recursos y Referencias

- [OWASP Top 10 2021](https://owasp.org/Top10/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [JWT Best Practices](https://datatracker.ietf.org/doc/html/rfc8725)
- [NIST Cybersecurity Framework](https://www.nist.gov/cyberframework)

---

**Documento generado por:** GitHub Copilot Security Analysis  
**Fecha:** Octubre 19, 2025  
**Versión:** 1.0
