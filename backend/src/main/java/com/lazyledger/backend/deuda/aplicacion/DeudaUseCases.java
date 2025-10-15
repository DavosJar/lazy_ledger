package com.lazyledger.backend.deuda.aplicacion;

import java.util.List;
import java.util.UUID;

import com.lazyledger.backend.commons.exceptions.InfrastructureException;
import com.lazyledger.backend.deuda.dominio.Deuda;
import com.lazyledger.backend.deuda.dominio.repositorio.DeudaRepository;

public class DeudaUseCases {

    private final DeudaRepository deudaRepository;

    public DeudaUseCases(DeudaRepository deudaRepository) {
        this.deudaRepository = deudaRepository;
    }

    public Deuda createDeuda(Deuda deuda) {
        try {
            return deudaRepository.save(deuda);
        } catch (Exception e) {
            throw new InfrastructureException("Error al guardar la deuda en la base de datos.", e);
        }
    }

    public Deuda getDeudaById(UUID id) {
        try {
            return deudaRepository.findById(id).orElseThrow(() -> new InfrastructureException("Deuda no encontrada con ID: " + id));
        } catch (Exception e) {
            throw new InfrastructureException("Error al buscar la deuda por ID.", e);
        }
    }

    public void deleteDeuda(UUID id) {
        if (!deudaRepository.existsById(id)) {
            throw new InfrastructureException("Deuda no encontrada con ID: " + id);
        }
        try {
            deudaRepository.delete(id);
        } catch (Exception e) {
            throw new InfrastructureException("Error al eliminar la deuda.", e);
        }
    }

    public Deuda updateDeuda(Deuda deuda) {
        if (!deudaRepository.existsById(deuda.getId().value())) {
            throw new InfrastructureException("Deuda no encontrada con ID: " + deuda.getId());
        }
        try {
            return deudaRepository.save(deuda);
        } catch (Exception e) {
            throw new InfrastructureException("Error al actualizar la deuda en la base de datos.", e);
        }
    }

    public List<Deuda> getAllDeudas() {
        try {
            return deudaRepository.findAll();
        } catch (Exception e) {
            throw new InfrastructureException("Error al obtener todas las deudas.", e);
        }
    }
}
