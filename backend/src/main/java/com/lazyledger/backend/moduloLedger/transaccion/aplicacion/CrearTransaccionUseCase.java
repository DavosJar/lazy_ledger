package com.lazyledger.backend.moduloLedger.transaccion.aplicacion;

import com.lazyledger.backend.commons.IdGenerator;
import com.lazyledger.backend.commons.enums.Categoria;
import com.lazyledger.backend.commons.enums.TipoTransaccion;
import com.lazyledger.backend.commons.exceptions.ApplicationException;
import com.lazyledger.backend.moduloLedger.ledger.domainServices.LedgerTransaccionService;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.Descripcion;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.Fecha;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.Monto;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.Transaccion;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Caso de uso para crear una transacción en un ledger.
 * 
 * Responsabilidades:
 * 1. Validar los datos de entrada
 * 2. Delegar al LedgerTransaccionService que valida:
 *    - El miembro está activo
 *    - El ledger está ACTIVO
 *    - El miembro tiene permisos para crear transacciones
 *    - El miembro pertenece al ledger
 * 3. Generar el ID de la transacción
 * 4. Crear y persistir la transacción
 */
@Service
public class CrearTransaccionUseCase {

    private final LedgerTransaccionService ledgerTransaccionService;
    private final IdGenerator idGenerator;

    public CrearTransaccionUseCase(LedgerTransaccionService ledgerTransaccionService,
                                   IdGenerator idGenerator) {
        this.ledgerTransaccionService = ledgerTransaccionService;
        this.idGenerator = idGenerator;
    }

    /**
     * Crea una nueva transacción.
     * 
     * @param ledgerId ID del ledger donde se creará la transacción
     * @param clienteId ID del cliente que crea la transacción (debe ser miembro del ledger)
     * @param monto Monto de la transacción
     * @param tipo Tipo de transacción (INGRESO/EGRESO)
     * @param categoria Categoría de la transacción
     * @param descripcion Descripción de la transacción
     * @param fecha Fecha de la transacción (si es null, usa fecha actual)
     * @return La transacción creada
     * @throws ApplicationException si hay errores de validación o permisos
     */
    public Transaccion ejecutar(UUID ledgerId,
                                UUID clienteId,
                                double monto,
                                TipoTransaccion tipo,
                                Categoria categoria,
                                String descripcion,
                                LocalDateTime fecha) {
        
        // Validaciones básicas
        if (ledgerId == null) {
            throw new ApplicationException("El ID del ledger es requerido");
        }
        if (clienteId == null) {
            throw new ApplicationException("El ID del cliente es requerido");
        }

        // Generar ID para la transacción
        UUID transaccionId = idGenerator.nextId();

        // Crear value objects
        Monto montoVO = Monto.of(BigDecimal.valueOf(monto));
        Descripcion descripcionVO = Descripcion.of(descripcion);
        Fecha fechaVO = fecha != null ? Fecha.of(fecha) : Fecha.of(LocalDateTime.now());

        // Delegar al domain service que maneja todas las validaciones de negocio
        return ledgerTransaccionService.crearTransaccion(
            ledgerId,
            clienteId,
            transaccionId,
            montoVO,
            tipo,
            categoria,
            descripcionVO,
            fechaVO
        );
    }
}
