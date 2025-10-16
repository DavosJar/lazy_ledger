package com.lazyledger.backend.transaccion.dominio.repositorio;

import com.lazyledger.backend.transaccion.dominio.Transaccion;
import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.commons.enums.TipoTransaccion;
import com.lazyledger.backend.commons.enums.Categoria;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransaccionRepository {
    Transaccion save(Transaccion transaccion);
    Optional<Transaccion> findById(UUID id);
    List<Transaccion> findAll();
    void delete(UUID id);
    boolean existsById(UUID id);
    List<Transaccion> findByLedgerId(UUID ledgerId);
    List<Transaccion> findByTipo(TipoTransaccion tipo);
    List<Transaccion> findByCategoria(Categoria categoria);
    boolean existsByLedgerId(UUID ledgerId);
    void deleteByLedgerId(UUID ledgerId);
}