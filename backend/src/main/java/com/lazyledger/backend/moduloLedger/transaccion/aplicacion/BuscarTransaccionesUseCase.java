package com.lazyledger.backend.moduloLedger.transaccion.aplicacion;

import com.lazyledger.backend.commons.enums.Categoria;
import com.lazyledger.backend.commons.enums.TipoTransaccion;
import com.lazyledger.backend.commons.exceptions.InfrastructureException;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.Transaccion;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.repositorio.TransaccionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Caso de uso para buscar transacciones con filtros dinámicos.
 * Utiliza el patrón Specification para construir consultas complejas.
 */
public class BuscarTransaccionesUseCase {
    
    private final TransaccionRepository transaccionRepository;

    public BuscarTransaccionesUseCase(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    /**
     * Busca transacciones aplicando los filtros especificados.
     * Todos los parámetros son opcionales (pueden ser null).
     * 
     * @param ledgerId ID del ledger (opcional)
     * @param fechaDesde Fecha de inicio del rango (opcional)
     * @param fechaHasta Fecha de fin del rango (opcional)
     * @param tipo Tipo de transacción (opcional)
     * @param categoria Categoría de la transacción (opcional)
     * @param montoMinimo Monto mínimo (opcional)
     * @param montoMaximo Monto máximo (opcional)
     * @return Lista de transacciones que cumplen con los filtros
     */
    public List<Transaccion> buscar(UUID ledgerId, LocalDateTime fechaDesde, LocalDateTime fechaHasta,
                                     TipoTransaccion tipo, Categoria categoria, 
                                     Double montoMinimo, Double montoMaximo) {
        try {
            return transaccionRepository.buscarConFiltros(
                ledgerId, fechaDesde, fechaHasta, tipo, categoria, montoMinimo, montoMaximo
            );
        } catch (Exception e) {
            throw new InfrastructureException("Error al buscar transacciones con filtros", e);
        }
    }

    /**
     * Busca transacciones de un ledger en un rango de fechas.
     */
    public List<Transaccion> buscarPorLedgerYFechas(UUID ledgerId, LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
        return buscar(ledgerId, fechaDesde, fechaHasta, null, null, null, null);
    }

    /**
     * Busca transacciones de un ledger por tipo.
     */
    public List<Transaccion> buscarPorLedgerYTipo(UUID ledgerId, TipoTransaccion tipo) {
        return buscar(ledgerId, null, null, tipo, null, null, null);
    }

    /**
     * Busca transacciones de un ledger por categoría.
     */
    public List<Transaccion> buscarPorLedgerYCategoria(UUID ledgerId, Categoria categoria) {
        return buscar(ledgerId, null, null, null, categoria, null, null);
    }

    /**
     * Busca transacciones de un ledger en un rango de monto.
     */
    public List<Transaccion> buscarPorLedgerYRangoMonto(UUID ledgerId, Double montoMinimo, Double montoMaximo) {
        return buscar(ledgerId, null, null, null, null, montoMinimo, montoMaximo);
    }
}