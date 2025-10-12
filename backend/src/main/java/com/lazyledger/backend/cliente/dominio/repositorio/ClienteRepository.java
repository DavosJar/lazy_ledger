package com.lazyledger.backend.cliente.dominio.repositorio;

import com.lazyledger.backend.cliente.dominio.Cliente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository {
    Cliente save(Cliente cliente);
    Optional<Cliente> findById(UUID id);
    List<Cliente> findAll();
    void delete(UUID id);
    boolean existsById(UUID id);
    boolean existsByEmail(String email);

}
