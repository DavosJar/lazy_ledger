package com.lazyledger.backend.moduloLedger.objetivo.dominio.repositorio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lazyledger.backend.moduloLedger.objetivo.dominio.Objetivo;

public interface ObjetivoRepository {
    Objetivo save(Objetivo objetivo);
    Optional<Objetivo> findBy(UUID id);
    List<Objetivo> findAll();
    void delete(UUID id);
    boolean existsById(UUID id);
    boolean existsByNombre(String nombre);
}
