package com.lazyledger.backend.moduloLedger.ledger.presentacion.assembler;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.api.BaseAssembler;
import com.lazyledger.backend.api.PagedResponse;
import com.lazyledger.backend.moduloLedger.ledger.presentacion.dto.LedgerDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LedgerAssembler implements BaseAssembler<LedgerDTO> {

    @Override
    public ApiResponse<LedgerDTO> assemble(LedgerDTO dto) {
        ApiResponse<LedgerDTO> response = new ApiResponse<>(dto);
        // Agregar enlaces HATEOAS comunes si aplica en el futuro
        return response;
    }

    public PagedResponse<LedgerDTO> assemblePaged(List<LedgerDTO> dtos, PagedResponse.PaginationInfo info) {
        // Agregar enlaces HATEOAS de paginación si aplica en el futuro
        return new PagedResponse<>(dtos, info);
    }
}