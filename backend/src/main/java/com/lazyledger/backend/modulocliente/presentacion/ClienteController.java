package com.lazyledger.backend.modulocliente.presentacion;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.api.PagedResponse;
import com.lazyledger.backend.modulocliente.aplicacion.ClienteFacade;
import com.lazyledger.backend.modulocliente.presentacion.dto.ClienteDTO;
import com.lazyledger.backend.modulocliente.presentacion.dto.ClienteSaveRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "API para gestión de clientes")
public class ClienteController {

    private final ClienteFacade clienteFacade;
    public ClienteController(ClienteFacade clienteFacade) {
        this.clienteFacade = clienteFacade;
    }

    @GetMapping
    @Operation(
        summary = "Obtener todos los clientes",
        description = "Retorna una lista paginada de todos los clientes registrados en el sistema. " +
                     "Permite paginación para manejar grandes volúmenes de datos."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Lista de clientes obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResponse.class))
        )
    })
    public ResponseEntity<PagedResponse<ClienteDTO>> getAllClientes(
            @Parameter(description = "Número de página (comienza en 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página (máximo 100)", example = "10") @RequestParam(defaultValue = "10") int size) {
        PagedResponse<ClienteDTO> response = clienteFacade.getAllClientes(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener cliente por ID",
        description = "Retorna la información completa de un cliente específico identificado por su ID único."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Cliente encontrado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Cliente no encontrado"
        )
    })
    public ResponseEntity<ApiResponse<ClienteDTO>> getClienteById(
            @Parameter(description = "ID único del cliente", example = "01HXXXXXXXXXXXXXXXXXXXXX") @PathVariable String id) {
        var apiResponse = clienteFacade.getClienteById(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    @Operation(
        summary = "Crear nuevo cliente",
        description = "Registra un nuevo cliente en el sistema con la información proporcionada."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Cliente creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Datos inválidos en la solicitud"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "409",
            description = "Ya existe un cliente con el mismo email"
        )
    })
    public ResponseEntity<ApiResponse<ClienteDTO>> createCliente(
            @Parameter(description = "Datos del cliente a crear") @RequestBody ClienteSaveRequest clienteSaveRequest) {
        var apiResponse = clienteFacade.createCliente(clienteSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar cliente",
        description = "Actualiza la información de un cliente existente identificado por su ID."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Cliente actualizado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Datos inválidos en la solicitud"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Cliente no encontrado"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "409",
            description = "Ya existe otro cliente con el mismo email"
        )
    })
    public ResponseEntity<ApiResponse<ClienteDTO>> updateCliente(
            @Parameter(description = "ID del cliente a actualizar", example = "01HXXXXXXXXXXXXXXXXXXXXX") @PathVariable String id,
            @Parameter(description = "Datos actualizados del cliente") @RequestBody ClienteDTO clienteDTO) {
        clienteDTO.setId(id);
        var apiResponse = clienteFacade.updateCliente(clienteDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar cliente",
        description = "Elimina permanentemente un cliente del sistema identificado por su ID."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "204",
            description = "Cliente eliminado exitosamente"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Cliente no encontrado"
        )
    })
    public ResponseEntity<Void> deleteCliente(
            @Parameter(description = "ID del cliente a eliminar", example = "01HXXXXXXXXXXXXXXXXXXXXX") @PathVariable String id) {
        clienteFacade.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }
}