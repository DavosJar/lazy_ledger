package com.lazyledger.backend.modulocliente.dominio.repositorio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lazyledger.backend.modulocliente.dominio.Cliente;

public interface ClienteRepository {
    Optional<Cliente> save(Cliente cliente);
    Optional<Cliente> findById(UUID id);
    List<Cliente> findAll();
    void delete(UUID id);
    boolean existsById(UUID id);
    boolean existsByEmail(String email);

}
