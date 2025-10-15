package com.lazyledger.backend.miembroLedger.aplicacion;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.api.PagedResponse;
import com.lazyledger.backend.api.ResponseFactory;
import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.miembroLedger.aplicacion.assembler.MiembroLedgerHateoasLinkBuilder;
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
    private final ResponseFactory responseFactory;
    private final MiembroLedgerHateoasLinkBuilder hateoasLinkBuilder;

    public MiembroLedgerFacade(MiembroLedgerRepository miembroLedgerRepository,
                               InvitarMiembroUseCase invitarMiembroUseCase,
                               CambiarRolMiembroUseCase cambiarRolMiembroUseCase,
                               ExpulsarMiembroUseCase expulsarMiembroUseCase,
                               EliminarLedgerUseCase eliminarLedgerUseCase,
                               MiembroLedgerMapper mapper,
                               ResponseFactory responseFactory,
                               MiembroLedgerHateoasLinkBuilder hateoasLinkBuilder) {
        this.miembroLedgerRepository = miembroLedgerRepository;
        this.invitarMiembroUseCase = invitarMiembroUseCase;
        this.cambiarRolMiembroUseCase = cambiarRolMiembroUseCase;
        this.expulsarMiembroUseCase = expulsarMiembroUseCase;
        this.eliminarLedgerUseCase = eliminarLedgerUseCase;
        this.mapper = mapper;
        this.responseFactory = responseFactory;
        this.hateoasLinkBuilder = hateoasLinkBuilder;
    }

    // === GESTIÓN DE MIEMBROS ===

    public ApiResponse<MiembroLedgerDTO> invitarMiembro(UUID solicitanteId, UUID clienteId, UUID ledgerId) {
        MiembroLedger miembro = invitarMiembroUseCase.execute(solicitanteId, clienteId, ledgerId);
        MiembroLedgerDTO dto = mapper.toDTO(miembro);
        var links = hateoasLinkBuilder.buildLinks(dto);
        return responseFactory.createResponse(dto, links);
    }

    public ApiResponse<MiembroLedgerDTO> cambiarRol(UUID solicitanteId, UUID clienteId, UUID ledgerId, String nuevoRol) {
        MiembroLedger miembro = cambiarRolMiembroUseCase.execute(solicitanteId, clienteId, ledgerId, nuevoRol);
        MiembroLedgerDTO dto = mapper.toDTO(miembro);
        var links = hateoasLinkBuilder.buildLinks(dto);
        return responseFactory.createResponse(dto, links);
    }

    public ApiResponse<Void> expulsarMiembro(UUID solicitanteId, UUID clienteId, UUID ledgerId) {
        expulsarMiembroUseCase.execute(solicitanteId, clienteId, ledgerId);
        return responseFactory.createResponse(null);
    }

    // === CONSULTAS ===

    public PagedResponse<MiembroLedgerDTO> obtenerMiembrosActivos(UUID ledgerId, int page, int size) {
        // Nota: Este método requeriría un query adicional en el repository
        // Por ahora, filtramos en memoria
        var allMiembros = miembroLedgerRepository.findAll().stream()
            .filter(m -> m.getLedgerId().equals(LedgerId.of(ledgerId)) && m.isActivo())
            .map(mapper::toDTO)
            .collect(Collectors.toList());

        long totalElements = allMiembros.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int start = page * size;
        int end = Math.min(start + size, allMiembros.size());
        var pageMiembros = allMiembros.subList(start, end);

        PagedResponse.PaginationInfo pagination = new PagedResponse.PaginationInfo(
            page, size, totalElements, totalPages, page == 0, page >= totalPages - 1
        );

        var links = hateoasLinkBuilder.buildCollectionLinks(page, size, totalElements, totalPages);
        return responseFactory.createPagedResponse(pageMiembros, pagination, links);
    }

    public ApiResponse<MiembroLedgerDTO> obtenerMiembro(UUID clienteId, UUID ledgerId) {
        MiembroLedger miembro = miembroLedgerRepository
            .findByClienteIdAndLedgerId(clienteId, ledgerId)
            .orElse(null);
        if (miembro == null) {
            return responseFactory.createErrorResponse("Miembro no encontrado");
        }
        MiembroLedgerDTO dto = mapper.toDTO(miembro);
        var links = hateoasLinkBuilder.buildLinks(dto);
        return responseFactory.createResponse(dto, links);
    }

    public PagedResponse<MiembroLedgerDTO> obtenerLedgersDeUsuario(UUID clienteId, int page, int size) {
        var allMiembros = miembroLedgerRepository.findByClienteId(clienteId).stream()
            .filter(MiembroLedger::isActivo)
            .map(mapper::toDTO)
            .collect(Collectors.toList());

        long totalElements = allMiembros.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int start = page * size;
        int end = Math.min(start + size, allMiembros.size());
        var pageMiembros = allMiembros.subList(start, end);

        PagedResponse.PaginationInfo pagination = new PagedResponse.PaginationInfo(
            page, size, totalElements, totalPages, page == 0, page >= totalPages - 1
        );

        var links = hateoasLinkBuilder.buildUserLedgersLinks(clienteId.toString(), page, size, totalElements, totalPages);
        return responseFactory.createPagedResponse(pageMiembros, pagination, links);
    }

    public ApiResponse<Void> eliminarLedger(UUID solicitanteId, UUID ledgerId) {
        eliminarLedgerUseCase.execute(solicitanteId, ledgerId);
        return responseFactory.createResponse(null);
    }

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