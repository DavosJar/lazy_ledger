package com.lazyledger.backend.modulocliente.infraestructura;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, UUID> {
    boolean existsByEmail(String email);
}