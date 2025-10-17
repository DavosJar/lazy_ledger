package com.lazyledger.backend.moduloLedger.miembroLedger.infraestructura;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.lazyledger.backend.commons.enums.MiembroRol;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedger;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.rerpositorio.MiembroLedgerRepository;

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
        return jpaRepository.findById(new MiembroLedgerId(clienteId, ledgerId))
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


    private MiembroLedger toDomain(MiembroLedgerEntity entity) {
        MiembroRol rol = MiembroRol.valueOf(entity.getRol().name());
        return MiembroLedger.create(ClienteId.of(entity.getClienteId()),
            LedgerId.of(entity.getLedgerId()),
                rol
        );
    }
    private MiembroLedgerEntity toEntity(MiembroLedger miembro) {
        MiembroLedgerId id = new MiembroLedgerId(miembro.getClienteId().value(), miembro.getLedgerId().value());
        return new MiembroLedgerEntity(
            id,
            miembro.getRol(),
            miembro.isActivo()
        );
    }


    


    
}
