# Especificaciones de Transacciones

Este paquete contiene las especificaciones JPA para filtrar transacciones utilizando el patrón Specification de Spring Data JPA.

## Ubicación en la Arquitectura

Las especificaciones están en **infraestructura** porque:
- Trabajan con `TransaccionEntity` (entidad JPA)
- Usan la API de Criterios de JPA
- Son detalles de implementación de persistencia

## Especificaciones Disponibles

### 1. TransaccionPorLedgerSpec
Filtra transacciones por un ledger específico.

```java
Specification<TransaccionEntity> spec = TransaccionPorLedgerSpec.porLedger(ledgerId);
```

### 2. TransaccionPorIntervaloFechasSpec
Filtra transacciones por un intervalo de fechas.

```java
Specification<TransaccionEntity> spec = TransaccionPorIntervaloFechasSpec.porIntervaloFechas(fechaDesde, fechaHasta);
```

### 3. TransaccionPorTipoSpec
Filtra transacciones por tipo (INGRESO, GASTO, etc.).

```java
Specification<TransaccionEntity> spec = TransaccionPorTipoSpec.porTipo(TipoTransaccion.INGRESO);
```

### 4. TransaccionPorCategoriaSpec
Filtra transacciones por categoría.

```java
Specification<TransaccionEntity> spec = TransaccionPorCategoriaSpec.porCategoria(Categoria.ALIMENTACION);
```

### 5. TransaccionPorRangoMontoSpec
Filtra transacciones por rango de monto.

```java
Specification<TransaccionEntity> spec = TransaccionPorRangoMontoSpec.porRangoMonto(montoMinimo, montoMaximo);
```

## Uso en el Repositorio

Las especificaciones se combinan en `JpaTransaccionRepositoryImpl.buscarConFiltros()`:

```java
@Override
public List<Transaccion> buscarConFiltros(UUID ledgerId, LocalDateTime fechaDesde, 
                                          LocalDateTime fechaHasta, TipoTransaccion tipo, 
                                          Categoria categoria, Double montoMinimo, Double montoMaximo) {
    // Construir la especificación combinando los filtros
    Specification<TransaccionEntity> spec = Specification.where(null);
    
    if (ledgerId != null) {
        spec = spec.and(TransaccionPorLedgerSpec.porLedger(ledgerId));
    }
    
    if (fechaDesde != null || fechaHasta != null) {
        spec = spec.and(TransaccionPorIntervaloFechasSpec.porIntervaloFechas(fechaDesde, fechaHasta));
    }
    
    // ... más filtros
    
    return jpaRepository.findAll(spec).stream()
            .map(this::toDomain)
            .toList();
}
```

## Uso desde la Capa de Aplicación

### BuscarTransaccionesUseCase

```java
// Buscar transacciones con filtros específicos
List<Transaccion> transacciones = buscarTransaccionesUseCase.buscar(
    ledgerId,           // UUID del ledger
    fechaDesde,         // LocalDateTime (opcional)
    fechaHasta,         // LocalDateTime (opcional)
    tipo,               // TipoTransaccion (opcional)
    categoria,          // Categoria (opcional)
    montoMinimo,        // Double (opcional)
    montoMaximo         // Double (opcional)
);
```

### Métodos de Conveniencia

```java
// Buscar por ledger y fechas
List<Transaccion> transacciones = buscarTransaccionesUseCase.buscarPorLedgerYFechas(
    ledgerId, fechaDesde, fechaHasta
);

// Buscar por ledger y tipo
List<Transaccion> transacciones = buscarTransaccionesUseCase.buscarPorLedgerYTipo(
    ledgerId, TipoTransaccion.INGRESO
);

// Buscar por ledger y categoría
List<Transaccion> transacciones = buscarTransaccionesUseCase.buscarPorLedgerYCategoria(
    ledgerId, Categoria.ALIMENTACION
);

// Buscar por ledger y rango de monto
List<Transaccion> transacciones = buscarTransaccionesUseCase.buscarPorLedgerYRangoMonto(
    ledgerId, 100.0, 1000.0
);
```

## Ejemplos de Casos de Uso Típicos

### Ejemplo 1: Transacciones del mes actual de un ledger

```java
UUID ledgerId = UUID.fromString("...");
LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);
LocalDateTime finMes = LocalDateTime.now();

List<Transaccion> transacciones = buscarTransaccionesUseCase.buscarPorLedgerYFechas(
    ledgerId, inicioMes, finMes
);
```

### Ejemplo 2: Ingresos mayores a $500 del último trimestre

```java
UUID ledgerId = UUID.fromString("...");
LocalDateTime hace3Meses = LocalDateTime.now().minusMonths(3);
LocalDateTime ahora = LocalDateTime.now();

List<Transaccion> transacciones = buscarTransaccionesUseCase.buscar(
    ledgerId,                    // ledger específico
    hace3Meses,                  // desde hace 3 meses
    ahora,                       // hasta ahora
    TipoTransaccion.INGRESO,     // solo ingresos
    null,                        // cualquier categoría
    500.0,                       // monto mínimo $500
    null                         // sin monto máximo
);
```

### Ejemplo 3: Gastos en alimentación o transporte

```java
UUID ledgerId = UUID.fromString("...");

// Buscar gastos en alimentación
List<Transaccion> gastosAlimentacion = buscarTransaccionesUseCase.buscar(
    ledgerId, null, null, TipoTransaccion.GASTO, Categoria.ALIMENTACION, null, null
);

// Buscar gastos en transporte
List<Transaccion> gastosTransporte = buscarTransaccionesUseCase.buscar(
    ledgerId, null, null, TipoTransaccion.GASTO, Categoria.TRANSPORTE, null, null
);

// Combinar resultados
List<Transaccion> todosLosGastos = new ArrayList<>();
todosLosGastos.addAll(gastosAlimentacion);
todosLosGastos.addAll(gastosTransporte);
```

## Ventajas de esta Implementación

1. **Simple y Pragmática**: No requiere múltiples capas de abstracción
2. **Usa Spring Data JPA**: Aprovecha las capacidades nativas del framework
3. **Flexible**: Todos los filtros son opcionales y se pueden combinar
4. **Mantenible**: Cada especificación está en su propio archivo
5. **Testeable**: Fácil de probar cada especificación individualmente
6. **Respeta DDD**: Las especificaciones JPA están en infraestructura donde deben estar