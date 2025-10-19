package com.lazyledger.backend.moduloLedger.miembroLedger.infraestructura;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.lazyledger.backend.commons.enums.MiembroRol;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedger;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.repositorio.MiembroLedgerRepository;

@Repository
public class MiembroJpaRepositoryImpl implements MiembroLedgerRepository{
    
    private final MiembroLedgerJpaRepository jpaRepository;
    public MiembroJpaRepositoryImpl(MiembroLedgerJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    @Override
    public Optional<MiembroLedger> save(MiembroLedger miembro) {
        MiembroLedgerEntity entity = toEntity(miembro);
        MiembroLedgerEntity saved = jpaRepository.save(entity);
        return Optional.of(toDomain(saved));
    }
    @Override
    public Optional<MiembroLedger> findByClienteIdAndLedgerId(UUID clienteId, UUID ledgerId) {
        return jpaRepository.findById(MiembroLedgerId.of(ledgerId, clienteId))
                .map(this::toDomain);
    }
    @Override
    public List<MiembroLedger> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }
    @Override
    public boolean existsByClienteIdAndLedgerId(UUID clienteId, UUID ledgerId) {
        return jpaRepository.existsByIdClienteIdAndIdLedgerId(clienteId, ledgerId);
    }

    @Override
    public List<MiembroLedger> findByClienteId(UUID clienteId) {
        return jpaRepository.findByIdClienteId(clienteId).stream()
                .map(this::toDomain)
                .toList();
    }
    @Override
    public Optional<MiembroLedger> findById(UUID id) {
        return jpaRepository.findByIdClienteId(id).stream()
                .findFirst()
                .map(this::toDomain);
    }
    
        @Override
        public void delete(UUID clienteId, UUID ledgerId) {
            MiembroLedgerId id = MiembroLedgerId.of(ledgerId, clienteId);
            jpaRepository.deleteById(id);
        }

    private MiembroLedger toDomain(MiembroLedgerEntity entity) {
        MiembroRol rol = MiembroRol.valueOf(entity.getRol().name());
        return MiembroLedger.rehydrate(
            ClienteId.of(entity.getClienteId()),
            rol,
            LedgerId.of(entity.getLedgerId()),
            entity.isActivo()
        );
    }
    private MiembroLedgerEntity toEntity(MiembroLedger miembro) {
        MiembroLedgerId id = MiembroLedgerId.of(miembro.getLedgerId().value(), miembro.getClienteId().value());
        return new MiembroLedgerEntity(
            id,
            miembro.getRol(),
            miembro.isActivo()
        );
    }


    


    
}
