package com.lazyledger.backend.deuda.dominio.repositorio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lazyledger.backend.deuda.dominio.Deuda;

public interface DeudaRepository {
    Deuda save(Deuda save);
    Optional<Deuda> findById(UUID id);
    List<Deuda> findAll();
    void delete(UUID id);
    boolean existsById(UUID id);
    boolean existsByNombre(String nombre);
}
