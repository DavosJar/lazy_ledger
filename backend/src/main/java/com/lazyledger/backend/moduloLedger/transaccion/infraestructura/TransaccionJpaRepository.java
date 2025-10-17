package com.lazyledger.backend.moduloLedger.transaccion.infraestructura;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;
import com.lazyledger.backend.commons.enums.TipoTransaccion;
import com.lazyledger.backend.commons.enums.Categoria;

public interface TransaccionJpaRepository extends JpaRepository<TransaccionEntity, UUID> {
    List<TransaccionEntity> findByLedgerId(UUID ledgerId);
    List<TransaccionEntity> findByTipo(TipoTransaccion tipo);
    List<TransaccionEntity> findByCategoria(Categoria categoria);
    boolean existsByLedgerId(UUID ledgerId);
}