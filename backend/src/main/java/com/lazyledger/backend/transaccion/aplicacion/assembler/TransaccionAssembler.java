package com.lazyledger.backend.transaccion.aplicacion.assembler;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.api.BaseAssembler;
import com.lazyledger.backend.transaccion.presentacion.dto.TransaccionDTO;
import com.lazyledger.backend.transaccion.presentacion.dto.TransaccionSaveRequest;
import com.lazyledger.backend.transaccion.presentacion.mapper.TransaccionMapper;
import com.lazyledger.backend.transaccion.dominio.Transaccion;
import com.lazyledger.backend.commons.IdGenerator;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.lazyledger.backend.transaccion.presentacion.TransaccionController;

@Component
public class TransaccionAssembler implements BaseAssembler<TransaccionDTO> {
    private final TransaccionMapper transaccionMapper;
    private final IdGenerator idGenerator;

    public TransaccionAssembler(TransaccionMapper transaccionMapper, IdGenerator idGenerator) {
        this.transaccionMapper = transaccionMapper;
        this.idGenerator = idGenerator;
    }

    @Override
    public ApiResponse<TransaccionDTO> assemble(TransaccionDTO dto) {
        Link selfLink = linkTo(methodOn(TransaccionController.class).getTransaccionById(dto.getId())).withSelfRel();
        ApiResponse<TransaccionDTO> response = new ApiResponse<>(dto, selfLink);
        addCommonLinks(response);
        return response;
    }

    @Override
    public void addCommonLinks(ApiResponse<TransaccionDTO> response) {
        // Agregar enlaces comunes si es necesario, por ejemplo, enlace a lista de transacciones
        Link allTransaccionesLink = linkTo(methodOn(TransaccionController.class).getAllTransacciones(0, 10)).withRel("all-transacciones");
        response.add(allTransaccionesLink);
    }

    // Método para convertir TransaccionSaveRequest a Transaccion (dominio)
    public Transaccion toDomain(TransaccionSaveRequest request) {
        var transaccion = transaccionMapper.toDomain(request);
        return Transaccion.create(
                com.lazyledger.backend.commons.identificadores.TransaccionId.of(idGenerator.nextId()),
                transaccion.getLedgerId(),
                transaccion.getMonto(),
                transaccion.getTipo(),
                transaccion.getCategoria(),
                transaccion.getDescripcion(),
                transaccion.getFecha()
        );
    }

    // Método para convertir TransaccionDTO a Transaccion (dominio)
    public Transaccion toDomain(TransaccionDTO dto) {
        return transaccionMapper.toDomain(dto);
    }

    // Método para convertir Transaccion (dominio) a TransaccionDTO
    public TransaccionDTO toDTO(Transaccion transaccion) {
        return transaccionMapper.toDTO(transaccion);
    }
}