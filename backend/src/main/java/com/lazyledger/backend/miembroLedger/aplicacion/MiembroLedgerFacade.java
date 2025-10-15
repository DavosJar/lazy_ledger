package com.lazyledger.backend.miembroLedger.aplicacion;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.miembroLedger.dominio.MiembroLedger;
import com.lazyledger.backend.miembroLedger.dominio.rerpositorio.MiembroLedgerRepository;
import com.lazyledger.backend.miembroLedger.presentacion.dto.MiembroLedgerDTO;
import com.lazyledger.backend.miembroLedger.presentacion.mapper.MiembroLedgerMapper;

@Service
public class MiembroLedgerFacade {

    private final MiembroLedgerRepository miembroLedgerRepository;
    private final InvitarMiembroUseCase invitarMiembroUseCase;
    private final CambiarRolMiembroUseCase cambiarRolMiembroUseCase;
    private final ExpulsarMiembroUseCase expulsarMiembroUseCase;
    private final EliminarLedgerUseCase eliminarLedgerUseCase;
    private final MiembroLedgerMapper mapper;

    public MiembroLedgerFacade(MiembroLedgerRepository miembroLedgerRepository,
                              InvitarMiembroUseCase invitarMiembroUseCase,
                              CambiarRolMiembroUseCase cambiarRolMiembroUseCase,
                              ExpulsarMiembroUseCase expulsarMiembroUseCase,
                              EliminarLedgerUseCase eliminarLedgerUseCase,
                              MiembroLedgerMapper mapper) {
        this.miembroLedgerRepository = miembroLedgerRepository;
        this.invitarMiembroUseCase = invitarMiembroUseCase;
        this.cambiarRolMiembroUseCase = cambiarRolMiembroUseCase;
        this.expulsarMiembroUseCase = expulsarMiembroUseCase;
        this.eliminarLedgerUseCase = eliminarLedgerUseCase;
        this.mapper = mapper;
    }

    // === GESTIÓN DE MIEMBROS ===

    public MiembroLedgerDTO invitarMiembro(UUID solicitanteId, UUID clienteId, UUID ledgerId) {
        MiembroLedger miembro = invitarMiembroUseCase.execute(solicitanteId, clienteId, ledgerId);
        return mapper.toDTO(miembro);
    }

    public MiembroLedgerDTO cambiarRol(UUID solicitanteId, UUID clienteId, UUID ledgerId, String nuevoRol) {
        MiembroLedger miembro = cambiarRolMiembroUseCase.execute(solicitanteId, clienteId, ledgerId, nuevoRol);
        return mapper.toDTO(miembro);
    }

    public void expulsarMiembro(UUID solicitanteId, UUID clienteId, UUID ledgerId) {
        expulsarMiembroUseCase.execute(solicitanteId, clienteId, ledgerId);
    }

    // === CONSULTAS ===

    public List<MiembroLedgerDTO> obtenerMiembrosActivos(UUID ledgerId) {
        // Nota: Este método requeriría un query adicional en el repository
        // Por ahora, filtramos en memoria
        return miembroLedgerRepository.findAll().stream()
            .filter(m -> m.getLedgerId().equals(LedgerId.of(ledgerId)) && m.isActivo())
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }

    public MiembroLedgerDTO obtenerMiembro(UUID clienteId, UUID ledgerId) {
        MiembroLedger miembro = miembroLedgerRepository
            .findByClienteIdAndLedgerId(clienteId, ledgerId)
            .orElse(null);
        return miembro != null ? mapper.toDTO(miembro) : null;
    }

    // === GESTIÓN DE LEDGER ===

    public void eliminarLedger(UUID solicitanteId, UUID ledgerId) {
        eliminarLedgerUseCase.execute(solicitanteId, ledgerId);
    }

    // === UTILIDADES ===

    public boolean esPropietario(UUID clienteId, UUID ledgerId) {
        return miembroLedgerRepository
            .findByClienteIdAndLedgerId(clienteId, ledgerId)
            .map(MiembroLedger::esPropietario)
            .orElse(false);
    }

    public boolean esMiembroActivo(UUID clienteId, UUID ledgerId) {
        return miembroLedgerRepository
            .findByClienteIdAndLedgerId(clienteId, ledgerId)
            .map(MiembroLedger::isActivo)
            .orElse(false);
    }
}