package com.lazyledger.backend.moduloSeguridad.infrastructure.persistance.postgres;

import com.lazyledger.backend.moduloSeguridad.infrastructure.persistance.entity.UserDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataJpaUserRepository extends JpaRepository<UserDbo, UUID> {
    Optional<UserDbo> findByUsername(String username);
    Optional<UserDbo> findByCustomerId(UUID customerId);
    boolean existsByUsername(String username);
    boolean existsByCustomerId(UUID customerId);
}