package com.lazyledger.backend.moduloLedger.ledger.domainServices;

import java.util.List;
import java.util.UUID;

import com.lazyledger.backend.moduloLedger.ledger.dominio.Ledger;
import com.lazyledger.backend.moduloLedger.ledger.dominio.repositorio.LedgerRepository;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedger;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.repositorio.MiembroLedgerRepository;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.Monto;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.Transaccion;
import com.lazyledger.backend.commons.enums.Categoria;
import com.lazyledger.backend.commons.enums.EstadoLedger;
import com.lazyledger.backend.commons.enums.TipoTransaccion;
import com.lazyledger.backend.commons.identificadores.TransaccionId;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.Descripcion;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.Fecha;
import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.repositorio.TransaccionRepository;

/**
 * TransaccionId id, LedgerId ledgerId, Monto monto,
                        TipoTransaccion tipo, Categoria categoria, Descripcion descripcion,
                        Fecha fecha
 */
//devo poder crear transacciones solo si soy miembro activo del ledger, y debo validar el estado del ledger, si esta inactivo no puedo crear transacciones
public class LedgerTransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final LedgerRepository ledgerRepository;
    private final MiembroLedgerRepository miembroRepository;

    public LedgerTransaccionService(
            TransaccionRepository transaccionRepository,
            LedgerRepository ledgerRepository,
            MiembroLedgerRepository miembroRepository) {
        this.transaccionRepository = transaccionRepository;
        this.ledgerRepository = ledgerRepository;
        this.miembroRepository = miembroRepository;
    }

    public Transaccion crearTransaccion(UUID ledgerId,
                                        UUID miembroId,
                                        UUID transaccionId,
                                        Monto monto,
                                        TipoTransaccion tipo,
                                        Categoria categoria,
                                        Descripcion descripcion,
                                        Fecha fecha) {
        // Cargar agregados desde repositorios
        Ledger ledger = ledgerRepository.findById(ledgerId)
                .orElseThrow(() -> new ValidationException("Ledger no encontrado"));
        MiembroLedger miembro = miembroRepository.findById(miembroId)
                .orElseThrow(() -> new ValidationException("Miembro no encontrado"));
        if(!miembro.puedeGestionarElementos()) {
            throw new ValidationException("El miembro no tiene permisos para crear transacciones");
        }    
        validarOperacion(ledger, miembro);

        // Crear la transacción mediante factory del dominio
        Transaccion transaccion = Transaccion.create(
                TransaccionId.of(transaccionId),
                ledger.getId(),
                monto,
                tipo,
                categoria,
                descripcion,
                fecha
        );
        transaccionRepository.save(transaccion);

        return transaccion;
    }

    public Transaccion obtenerTransaccion(UUID miembroId, UUID transaccionId) {
        //esto devuelve optional
        MiembroLedger miembro = miembroRepository.findById(miembroId)
                .orElseThrow(() -> new ValidationException("Miembro no encontrado"));
        if (!miembro.puedeVerElementos()) {
            throw new ValidationException("El miembro no tiene permisos para ver transacciones");
        }
        Ledger ledger = ledgerRepository.findById(miembro.getLedgerId().value())
                .orElseThrow(() -> new ValidationException("Ledger no encontrado"));
        validarOperacion(ledger, miembro);
        Transaccion transaccion = transaccionRepository.findById(transaccionId)
                .orElseThrow(() -> new ValidationException("Transacción no encontrada"));

        return transaccion;
    }

    public List<Transaccion> obtenerTransacciones(UUID miembroId) {
        MiembroLedger miembro = miembroRepository.findById(miembroId)
                .orElseThrow(() -> new ValidationException("Miembro no encontrado"));
        if (!miembro.puedeVerElementos()) {
            throw new ValidationException("El miembro no tiene permisos para ver transacciones");
        }
        Ledger ledger = ledgerRepository.findById(miembro.getLedgerId().value())
                .orElseThrow(() -> new ValidationException("Ledger no encontrado"));
        validarOperacion(ledger, miembro);
        List<Transaccion> transacciones = transaccionRepository.findByLedgerId(ledger.getId().value());
        return transacciones;
    }

    

    private void validarOperacion(Ledger ledger, MiembroLedger miembro) {
        if (!miembro.isActivo()) {
            throw new ValidationException("El miembro no está activo");
        }
        if (ledger.getEstado() != EstadoLedger.ACTIVO) {
            throw new ValidationException("El ledger no está activo");
        }
        if (!miembro.getLedgerId().equals(ledger.getId())) {
            throw new ValidationException("El miembro no pertenece a este ledger");
        }
    }
}