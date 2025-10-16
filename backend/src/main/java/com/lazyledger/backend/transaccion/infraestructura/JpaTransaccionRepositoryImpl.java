package com.lazyledger.backend.transaccion.infraestructura;

import com.lazyledger.backend.transaccion.dominio.Transaccion;
import com.lazyledger.backend.transaccion.dominio.Descripcion;
import com.lazyledger.backend.transaccion.dominio.Fecha;
import com.lazyledger.backend.transaccion.dominio.Monto;
import com.lazyledger.backend.transaccion.dominio.repositorio.TransaccionRepository;
import com.lazyledger.backend.commons.exceptions.InfrastructureException;
import com.lazyledger.backend.commons.identificadores.TransaccionId;
import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.commons.enums.TipoTransaccion;
import com.lazyledger.backend.commons.enums.Categoria;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;

@Repository
public class JpaTransaccionRepositoryImpl implements TransaccionRepository {
    private final TransaccionJpaRepository jpaRepository;

    public JpaTransaccionRepositoryImpl(TransaccionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Transaccion save(Transaccion transaccion) {
        try {
            TransaccionEntity entity = toEntity(transaccion);
            TransaccionEntity saved = jpaRepository.save(entity);
            return toDomain(saved);
        } catch (Exception e) {
            throw new InfrastructureException("Error al guardar la transacción en la base de datos", e);
        }
    }

    @Override
    public Optional<Transaccion> findById(UUID id) {
        try {
            return jpaRepository.findById(id).map(this::toDomain);
        } catch (Exception e) {
            throw new InfrastructureException("Error al buscar la transacción por ID en la base de datos", e);
        }
    }

    @Override
    public List<Transaccion> findAll() {
        try {
            return jpaRepository.findAll().stream().map(this::toDomain).toList();
        } catch (Exception e) {
            throw new InfrastructureException("Error al obtener todas las transacciones de la base de datos", e);
        }
    }

    @Override
    public List<Transaccion> findByLedgerId(UUID ledgerId) {
        try {
            return jpaRepository.findByLedgerId(ledgerId).stream().map(this::toDomain).toList();
        } catch (Exception e) {
            throw new InfrastructureException("Error al obtener transacciones por ledger ID", e);
        }
    }

    @Override
    public List<Transaccion> findByTipo(TipoTransaccion tipo) {
        try {
            return jpaRepository.findByTipo(tipo).stream().map(this::toDomain).toList();
        } catch (Exception e) {
            throw new InfrastructureException("Error al obtener transacciones por tipo", e);
        }
    }

    @Override
    public List<Transaccion> findByCategoria(Categoria categoria) {
        try {
            return jpaRepository.findByCategoria(categoria).stream().map(this::toDomain).toList();
        } catch (Exception e) {
            throw new InfrastructureException("Error al obtener transacciones por categoría", e);
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            jpaRepository.deleteById(id);
        } catch (Exception e) {
            throw new InfrastructureException("Error al eliminar la transacción de la base de datos", e);
        }
    }

    @Override
    public boolean existsById(UUID id) {
        try {
            return jpaRepository.existsById(id);
        } catch (Exception e) {
            throw new InfrastructureException("Error al verificar existencia de la transacción por ID", e);
        }
    }

    @Override
    public boolean existsByLedgerId(UUID ledgerId) {
        try {
            return jpaRepository.existsByLedgerId(ledgerId);
        } catch (Exception e) {
            throw new InfrastructureException("Error al verificar existencia de transacciones por ledger ID", e);
        }
    }

    @Override
    public void deleteByLedgerId(UUID ledgerId) {
        try {
            jpaRepository.findByLedgerId(ledgerId).forEach(entity -> jpaRepository.delete(entity));
        } catch (Exception e) {
            throw new InfrastructureException("Error al eliminar transacciones por ledger ID", e);
        }
    }

    private TransaccionEntity toEntity(Transaccion transaccion) {
        return new TransaccionEntity(
                transaccion.getId().value(),
                transaccion.getLedgerId().id(),
                transaccion.getMonto().valor().doubleValue(),
                transaccion.getTipo(),
                transaccion.getCategoria(),
                transaccion.getDescripcion() != null ? transaccion.getDescripcion().valor() : null,
                transaccion.getFecha().valor()
        );
    }

    private Transaccion toDomain(TransaccionEntity entity) {
        return Transaccion.rehydrate(
                TransaccionId.of(entity.getId()),
                LedgerId.of(entity.getLedgerId()),
                Monto.of(BigDecimal.valueOf(entity.getMonto())),
                entity.getTipo(),
                entity.getCategoria(),
                entity.getDescripcion() != null ? Descripcion.of(entity.getDescripcion()) : null,
                Fecha.of(entity.getFecha())
        );
    }
}