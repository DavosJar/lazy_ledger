package com.lazyledger.backend.cliente.infraestructura;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, UUID> {
}