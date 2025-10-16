package com.lazyledger.backend.transaccion.aplicacion;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.transaccion.presentacion.dto.TransaccionDTO;
import com.lazyledger.backend.transaccion.presentacion.dto.TransaccionSaveRequest;
import com.lazyledger.backend.transaccion.aplicacion.assembler.TransaccionAssembler;
import com.lazyledger.backend.transaccion.dominio.repositorio.TransaccionRepository;
import com.lazyledger.backend.commons.exceptions.ApplicationException;
import org.springframework.stereotype.Service;
import java.util.UUID;
import com.lazyledger.backend.api.PagedResponse;
import org.springframework.hateoas.Link;

@Service
public class TransaccionFacade {
    private final TransaccionUseCases transaccionUseCases;
    private final TransaccionAssembler transaccionAssembler;

    public TransaccionFacade(TransaccionRepository transaccionRepository, TransaccionAssembler transaccionAssembler) {
        this.transaccionUseCases = new TransaccionUseCases(transaccionRepository);
        this.transaccionAssembler = transaccionAssembler;
    }

    public ApiResponse<TransaccionDTO> createTransaccion(TransaccionSaveRequest transaccionSaveRequest) {
        try {
            var transaccion = transaccionAssembler.toDomain(transaccionSaveRequest);
            var createdTransaccion = transaccionUseCases.createTransaccion(transaccion);
            var dto = transaccionAssembler.toDTO(createdTransaccion);
            return transaccionAssembler.assemble(dto);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("Datos inválidos en la solicitud de creación: " + e.getMessage(), e);
        }
    }

    public ApiResponse<TransaccionDTO> getTransaccionById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            var transaccion = transaccionUseCases.getTransaccionById(uuid);
            var dto = transaccionAssembler.toDTO(transaccion);
            return transaccionAssembler.assemble(dto);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("El ID proporcionado no es un UUID válido: " + id, e);
        }
    }

    public void deleteTransaccion(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            transaccionUseCases.deleteTransaccion(uuid);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("El ID proporcionado no es un UUID válido: " + id, e);
        }
    }

    public ApiResponse<TransaccionDTO> updateTransaccion(TransaccionDTO transaccionDTO) {
        try {
            var transaccion = transaccionAssembler.toDomain(transaccionDTO);
            var updatedTransaccion = transaccionUseCases.updateTransaccion(transaccion);
            var dto = transaccionAssembler.toDTO(updatedTransaccion);
            return transaccionAssembler.assemble(dto);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("Datos inválidos en la solicitud de actualización: " + e.getMessage(), e);
        }
    }

    public PagedResponse<TransaccionDTO> getAllTransacciones(int page, int size) {
        var allTransacciones = transaccionUseCases.getAllTransacciones().stream()
                .map(transaccionAssembler::toDTO)
                .collect(java.util.stream.Collectors.toList());
        long totalElements = allTransacciones.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int start = page * size;
        int end = Math.min(start + size, allTransacciones.size());
        var pageTransacciones = allTransacciones.subList(start, end);
        return new PagedResponse<>(pageTransacciones, page, size, totalElements, totalPages, new Link[0]);
    }

    public PagedResponse<TransaccionDTO> getTransaccionesByLedgerId(String ledgerId, int page, int size) {
        try {
            UUID uuid = UUID.fromString(ledgerId);
            var allTransacciones = transaccionUseCases.getTransaccionesByLedgerId(uuid).stream()
                    .map(transaccionAssembler::toDTO)
                    .collect(java.util.stream.Collectors.toList());
            long totalElements = allTransacciones.size();
            int totalPages = (int) Math.ceil((double) totalElements / size);
            int start = page * size;
            int end = Math.min(start + size, allTransacciones.size());
            var pageTransacciones = allTransacciones.subList(start, end);
            return new PagedResponse<>(pageTransacciones, page, size, totalElements, totalPages, new Link[0]);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("El ID del ledger proporcionado no es un UUID válido: " + ledgerId, e);
        }
    }
}