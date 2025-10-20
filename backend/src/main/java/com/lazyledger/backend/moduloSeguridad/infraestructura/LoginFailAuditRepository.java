package com.lazyledger.backend.moduloSeguridad.infraestructura;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginFailAuditRepository extends JpaRepository<LoginFailAudit, Long> {
}
