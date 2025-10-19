# Especificaciones de Transacciones

Este paquete contiene las especificaciones para filtrar transacciones utilizando el patrón Specification.

## Especificaciones Disponibles

### 1. TransaccionPorLedger
Filtra transacciones por un ledger específico.

```java
UUID ledgerId = UUID.fromString("...");
EspecificacionJpa<TransaccionEntity> spec = new TransaccionPorLedger(ledgerId);
```

### 2. TransaccionPorIntervaloFechas
Filtra transacciones por un intervalo de fechas.

```java
LocalDateTime fechaDesde = LocalDateTime.of(2024, 1, 1, 0, 0);
LocalDateTime fechaHasta = LocalDateTime.of(2024, 12, 31, 23, 59);
EspecificacionJpa<TransaccionEntity> spec = new TransaccionPorIntervaloFechas(fechaDesde, fechaHasta);
```

### 3. TransaccionPorTipo
Filtra transacciones por tipo (INGRESO, GASTO, etc.).

```java
EspecificacionJpa<TransaccionEntity> spec = new TransaccionPorTipo(TipoTransaccion.INGRESO);
```

### 4. TransaccionPorCategoria
Filtra transacciones por categoría.

```java
EspecificacionJpa<TransaccionEntity> spec = new TransaccionPorCategoria(Categoria.ALIMENTACION);
```

### 5. TransaccionPorRangoMonto
Filtra transacciones por rango de monto.

```java
Double montoMinimo = 100.0;
Double montoMaximo = 1000.0;
EspecificacionJpa<TransaccionEntity> spec = new TransaccionPorRangoMonto(montoMinimo, montoMaximo);
```

## Ejemplos de Uso Combinado

Las especificaciones se pueden combinar usando los operadores lógicos del patrón Specification base.

### Ejemplo 1: Transacciones de un ledger en un período específico

```java
UUID ledgerId = UUID.fromString("...");
LocalDateTime fechaDesde = LocalDateTime.of(2024, 1, 1, 0, 0);
LocalDateTime fechaHasta = LocalDateTime.of(2024, 12, 31, 23, 59);

// Combinar especificaciones con AND
EspecificacionJpa<TransaccionEntity> especificacion = 
    new TransaccionPorLedger(ledgerId)
        .y(new TransaccionPorIntervaloFechas(fechaDesde, fechaHasta));
```

### Ejemplo 2: Ingresos o gastos de un ledger

```java
UUID ledgerId = UUID.fromString("...");

// Combinar especificaciones con OR
EspecificacionJpa<TransaccionEntity> especificacion = 
    new TransaccionPorLedger(ledgerId)
        .y(new TransaccionPorTipo(TipoTransaccion.INGRESO)
            .o(new TransaccionPorTipo(TipoTransaccion.GASTO)));
```

### Ejemplo 3: Transacciones de un ledger, excluyendo una categoría

```java
UUID ledgerId = UUID.fromString("...");

// Combinar especificaciones con NOT
EspecificacionJpa<TransaccionEntity> especificacion = 
    new TransaccionPorLedger(ledgerId)
        .y(new TransaccionPorCategoria(Categoria.ENTRETENIMIENTO).no());
```

### Ejemplo 4: Búsqueda compleja - Ingresos del último mes mayores a $500

```java
UUID ledgerId = UUID.fromString("...");
LocalDateTime fechaDesde = LocalDateTime.now().minusMonths(1);
LocalDateTime fechaHasta = LocalDateTime.now();
Double montoMinimo = 500.0;

EspecificacionJpa<TransaccionEntity> especificacion = 
    new TransaccionPorLedger(ledgerId)
        .y(new TransaccionPorIntervaloFechas(fechaDesde, fechaHasta))
        .y(new TransaccionPorTipo(TipoTransaccion.INGRESO))
        .y(new TransaccionPorRangoMonto(montoMinimo, null));
```

### Ejemplo 5: Gastos en alimentación o transporte del mes actual

```java
UUID ledgerId = UUID.fromString("...");
LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);
LocalDateTime finMes = LocalDateTime.now();

EspecificacionJpa<TransaccionEntity> especificacion = 
    new TransaccionPorLedger(ledgerId)
        .y(new TransaccionPorIntervaloFechas(inicioMes, finMes))
        .y(new TransaccionPorTipo(TipoTransaccion.GASTO))
        .y(new TransaccionPorCategoria(Categoria.ALIMENTACION)
            .o(new TransaccionPorCategoria(Categoria.TRANSPORTE)));
```

## Uso en el Repositorio

Para usar estas especificaciones en el repositorio, necesitarás un método que acepte una especificación JPA:

```java
public interface TransaccionRepository {
    List<Transaccion> findAll(EspecificacionJpa<TransaccionEntity> especificacion);
    Page<Transaccion> findAll(EspecificacionJpa<TransaccionEntity> especificacion, Pageable pageable);
}
```

## Ventajas del Patrón Specification

1. **Reutilización**: Las especificaciones se pueden reutilizar en diferentes contextos.
2. **Composición**: Se pueden combinar especificaciones simples para crear consultas complejas.
3. **Mantenibilidad**: La lógica de filtrado está encapsulada y es fácil de mantener.
4. **Testabilidad**: Cada especificación se puede probar de forma independiente.
5. **Legibilidad**: El código es más expresivo y fácil de entender.