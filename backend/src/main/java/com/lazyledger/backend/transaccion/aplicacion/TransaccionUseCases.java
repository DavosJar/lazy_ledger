package com.lazyledger.backend.transaccion.aplicacion;

import java.util.UUID;
import java.util.List;
import com.lazyledger.backend.transaccion.dominio.Transaccion;
import com.lazyledger.backend.transaccion.dominio.repositorio.TransaccionRepository;
import com.lazyledger.backend.commons.exceptions.NotFoundException;
import com.lazyledger.backend.commons.exceptions.InfrastructureException;

public class TransaccionUseCases {
    private final TransaccionRepository transaccionRepository;

    public TransaccionUseCases(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    public Transaccion createTransaccion(Transaccion transaccion) {
        try {
            return transaccionRepository.save(transaccion);
        } catch (Exception e) {
            throw new InfrastructureException("Error al guardar la transacción en la base de datos", e);
        }
    }

    public Transaccion getTransaccionById(UUID id) {
        try {
            return transaccionRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Transacción no encontrada con ID: " + id));
        } catch (Exception e) {
            if (e instanceof NotFoundException) {
                throw e;
            }
            throw new InfrastructureException("Error al buscar la transacción por ID", e);
        }
    }

    public void deleteTransaccion(UUID id) {
        if (!transaccionRepository.existsById(id)) {
            throw new NotFoundException("Transacción no encontrada con ID: " + id);
        }
        try {
            transaccionRepository.delete(id);
        } catch (Exception e) {
            throw new InfrastructureException("Error al eliminar la transacción", e);
        }
    }

    public Transaccion updateTransaccion(Transaccion transaccion) {
        if (!transaccionRepository.existsById(transaccion.getId().value())) {
            throw new NotFoundException("Transacción no encontrada con ID: " + transaccion.getId());
        }
        try {
            return transaccionRepository.save(transaccion);
        } catch (Exception e) {
            throw new InfrastructureException("Error al actualizar la transacción en la base de datos", e);
        }
    }

    public List<Transaccion> getAllTransacciones() {
        try {
            return transaccionRepository.findAll();
        } catch (Exception e) {
            throw new InfrastructureException("Error al obtener la lista de transacciones", e);
        }
    }

    public List<Transaccion> getTransaccionesByLedgerId(UUID ledgerId) {
        try {
            return transaccionRepository.findByLedgerId(ledgerId);
        } catch (Exception e) {
            throw new InfrastructureException("Error al obtener transacciones por ledger ID", e);
        }
    }
}