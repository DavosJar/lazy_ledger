package com.lazyledger.backend.moduloLedger.ledger.aplicacion;


import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.moduloLedger.ledger.presentacion.dto.LedgerDTO;
import com.lazyledger.backend.moduloLedger.ledger.presentacion.dto.LedgerSaveRequest;
import com.lazyledger.backend.moduloLedger.ledger.presentacion.mapper.LedgerMapper;
import com.lazyledger.backend.moduloLedger.ledger.presentacion.assembler.LedgerAssembler;
import org.springframework.stereotype.Service;
import com.lazyledger.backend.api.PagedResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.UUID;

/**
 * Facade para operaciones de Ledger.
 * Coordina entre los casos de uso y el controller, manejando conversiones de DTO.
 */
@Service
public class LedgerFacade {

    private final CrearLedgerUseCase crearLedgerUseCase;
    private final LedgerMapper ledgerMapper;
    private final LedgerAssembler ledgerAssembler;
    private final BuscarLedgersDeClientePaginadoUseCase buscarLedgersDeClientePaginadoUseCase;

    public LedgerFacade(CrearLedgerUseCase crearLedgerUseCase, LedgerMapper ledgerMapper, BuscarLedgersDeClientePaginadoUseCase buscarLedgersDeClientePaginadoUseCase, LedgerAssembler ledgerAssembler) {
        this.crearLedgerUseCase = crearLedgerUseCase;
        this.ledgerMapper = ledgerMapper;
        this.buscarLedgersDeClientePaginadoUseCase = buscarLedgersDeClientePaginadoUseCase;
        this.ledgerAssembler = ledgerAssembler;
    }
    /**
     * Lista los ledgers donde el cliente es miembro, paginado.
     */
    public PagedResponse<LedgerDTO> listarLedgersDeCliente(String clienteId, int page, int size) {
        return listarLedgersDeCliente(clienteId, null, page, size, "nombre", true);
    }

    public PagedResponse<LedgerDTO> listarLedgersDeCliente(String clienteId, String nombre, int page, int size, String sortBy, boolean asc) {
        ClienteId clienteIdObj = ClienteId.of(UUID.fromString(clienteId));
        Sort sort = Sort.by(sortBy == null || sortBy.isBlank() ? "nombre" : sortBy);
        sort = asc ? sort.ascending() : sort.descending();
        Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, sort);
        var pageLedgers = buscarLedgersDeClientePaginadoUseCase.ejecutar(clienteIdObj, nombre, pageable);
        var dtoPage = pageLedgers.map(ledgerMapper::toDTO);
        return PagedResponse.from(dtoPage);
    }    /**
     * Crea un nuevo ledger para el cliente autenticado.
     * 
     * @param request Datos del ledger a crear
     * @param clienteId ID del cliente que crea el ledger (obtenido del token JWT)
     * @return ApiResponse con el ledger creado
     */
    public ApiResponse<LedgerDTO> crearLedger(LedgerSaveRequest request, String clienteId) {
        ClienteId clienteIdObj = ClienteId.of(UUID.fromString(clienteId));
        var ledger = crearLedgerUseCase.ejecutar(
            request.getNombre(),
            request.getDescripcion(),
            clienteIdObj
        );
        LedgerDTO dto = ledgerMapper.toDTO(ledger);
        return ledgerAssembler.assemble(dto);
    }
}
