package com.lazyledger.backend.moduloLedger.ledger.infraestructura;

import com.lazyledger.backend.commons.exceptions.InfrastructureException;
import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.moduloLedger.ledger.dominio.Ledger;
import com.lazyledger.backend.moduloLedger.ledger.dominio.repositorio.LedgerRepository;
import com.lazyledger.backend.moduloLedger.ledger.infraestructura.especificaciones.LedgerPorClienteSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaLedgerRepositoryImpl implements LedgerRepository {

    private final LedgerJpaRepository jpaRepository;

    public JpaLedgerRepositoryImpl(LedgerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    private Ledger toDomain(LedgerEntity entity) {
        return Ledger.rehydrate(
            LedgerId.of(entity.getId()),
            entity.getNombre(),
            entity.getDescripcion(),
            entity.getEstado()
        );
    }

    private LedgerEntity toEntity(Ledger ledger) {
        return new LedgerEntity(
            ledger.getId().value(),
            ledger.getNombre(),
            ledger.getDescripcion(),
            ledger.getEstado()
        );
    }

    @Override
    public Ledger save(Ledger ledger) {
        try {
            LedgerEntity saved = jpaRepository.save(toEntity(ledger));
            return toDomain(saved);
        } catch (Exception e) {
            throw new InfrastructureException("Error al guardar ledger", e);
        }
    }

    @Override
    public Optional<Ledger> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Ledger> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Page<Ledger> buscarPorClienteConFiltros(UUID clienteId, String nombre, Pageable pageable) {
        Specification<LedgerEntity> spec = (root, query, cb) -> cb.conjunction();
        spec = spec.and(LedgerPorClienteSpec.porCliente(clienteId));
        if (nombre != null && !nombre.isBlank()) {
            spec = spec.and(LedgerPorClienteSpec.nombreContiene(nombre));
        }
        Page<LedgerEntity> page = jpaRepository.findAll(spec, pageable);
        List<Ledger> content = page.getContent().stream().map(this::toDomain).toList();
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return jpaRepository.existsByNombre(nombre);
    }
}
