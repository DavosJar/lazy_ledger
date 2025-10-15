package com.lazyledger.backend.objetivo.aplicacion;

import java.util.List;
import java.util.UUID;

import com.lazyledger.backend.commons.exceptions.DuplicateException;
import com.lazyledger.backend.commons.exceptions.InfrastructureException;
import com.lazyledger.backend.commons.exceptions.NotFoundException;
import com.lazyledger.backend.objetivo.dominio.Objetivo;
import com.lazyledger.backend.objetivo.dominio.repositorio.ObjetivoRepository;

public class ObjetivoUseCases {
    
    private final ObjetivoRepository objetivoRepository;

    public ObjetivoUseCases(ObjetivoRepository objetivoRepository) {
        this.objetivoRepository = objetivoRepository;
    }

    public Objetivo createObjetivo(Objetivo objetivo) {
        if (objetivoRepository.existsByNombre(objetivo.getNombre())) {
            throw new DuplicateException("Ya existe un objetivo con el nombre: " + objetivo.getNombre());
        }
        try {
            return objetivoRepository.save(objetivo);
        } catch (Exception e) {
            throw new InfrastructureException("Error al crear el objetivo: " + e.getMessage());
        }
    }

    public Objetivo getObjetivoById(UUID id) {
        try {
            return objetivoRepository.findBy(id)
                    .orElseThrow(() -> new NotFoundException("Objetivo no encontrado con ID: " + id));
        } catch (Exception e) {
            throw new InfrastructureException("Error al obtener el objetivo: " + e.getMessage());
        }
    }

    public void deleteObjetivo(UUID id) {
        if (!objetivoRepository.existsById(id)) {
            throw new NotFoundException("Objetivo no encontrado con ID: " + id);
        }
        try {
            objetivoRepository.delete(id);
        } catch (Exception e) {
            throw new InfrastructureException("Error al eliminar el objetivo: " + e.getMessage());
        }
    }

    public Objetivo updateObjetivo(Objetivo objetivo) {
        if (!objetivoRepository.existsById(objetivo.getId().value())) {
            throw new NotFoundException("Objetivo no encontrado con ID: " + objetivo.getId());
        }
        // Check if nombre is taken by another objetivo
        var existing = objetivoRepository.findBy(objetivo.getId().value()).orElseThrow();
        if (!existing.getNombre().equals(objetivo.getNombre()) && objetivoRepository.existsByNombre(objetivo.getNombre())) {
            throw new DuplicateException("Ya existe otro objetivo con el nombre: " + objetivo.getNombre());
        }
        try {
            return objetivoRepository.save(objetivo);
        } catch (Exception e) {
            throw new InfrastructureException("Error al actualizar el objetivo: " + e.getMessage());
        }
    }

    public List<Objetivo> getAllObjetivos() {
        try {
            return objetivoRepository.findAll();
        } catch (Exception e) {
            throw new InfrastructureException("Error al obtener los objetivos: " + e.getMessage());
        }
    }
}
