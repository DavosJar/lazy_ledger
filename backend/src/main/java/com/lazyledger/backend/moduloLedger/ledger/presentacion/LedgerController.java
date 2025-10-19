package com.lazyledger.backend.moduloLedger.ledger.presentacion;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.api.PagedResponse;
import com.lazyledger.backend.moduloLedger.ledger.aplicacion.LedgerFacade;
import com.lazyledger.backend.moduloLedger.ledger.presentacion.dto.LedgerDTO;
import com.lazyledger.backend.moduloLedger.ledger.presentacion.dto.LedgerSaveRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para gestión de Ledgers.
 * 
 * Los endpoints de este controller requieren autenticación JWT.
 * El clienteId se obtiene del token JWT del usuario autenticado.
 */
@RestController
@RequestMapping("/ledgers")
@Tag(name = "Ledgers", description = "API para gestión de ledgers financieros")
@SecurityRequirement(name = "Bearer Authentication")
public class LedgerController {

    private final LedgerFacade ledgerFacade;

    public LedgerController(LedgerFacade ledgerFacade) {
        this.ledgerFacade = ledgerFacade;
    }

    @PostMapping
    @Operation(
        summary = "Crear nuevo ledger",
        description = "Crea un nuevo ledger financiero. El usuario autenticado se convierte automáticamente en PROPIETARIO del ledger. " +
                     "El ledger se crea en estado ACTIVO por defecto."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Ledger creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Datos inválidos en la solicitud"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = "No autenticado"
        )
    })
    public ResponseEntity<ApiResponse<LedgerDTO>> crearLedger(
            @Parameter(description = "Datos del ledger a crear", required = true)
            @Valid @RequestBody LedgerSaveRequest request,
            @Parameter(description = "ID del cliente autenticado (obtenido del token JWT)", hidden = true)
            @RequestHeader(value = "X-Client-Id", required = false) String clienteId) {
        
        // TODO: En producción, obtener clienteId del SecurityContext/JWT
        // Por ahora se recibe como header para testing
        if (clienteId == null || clienteId.isBlank()) {
            // Valor temporal para testing - REMOVER en producción
            throw new IllegalArgumentException("El header X-Client-Id es requerido para testing");
        }
        
        ApiResponse<LedgerDTO> response = ledgerFacade.crearLedger(request, clienteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Endpoint temporal de testing: crea un ledger quemado sin seguridad
    // GET /ledgers/debug/crear
        // Endpoint de prueba rápida: crea ledger demo y propietario fijo
    @GetMapping("/debug/crear-simple")
    @Operation(summary = "[DEBUG] Crear ledger demo sin parámetros",
               description = "Crea un ledger demo y asigna propietario con datos fijos. Solo para pruebas rápidas.")
    public ResponseEntity<ApiResponse<LedgerDTO>> crearLedgerDemoSimple(@RequestParam String clienteId) {
        // Datos fijos para pruebas
        String nombre = "Ledger Demo Simple";
        String descripcion = "Ledger creado por GET simple de prueba";
        var request = new com.lazyledger.backend.moduloLedger.ledger.presentacion.dto.LedgerSaveRequest(
            nombre,
            descripcion
        );
        ApiResponse<LedgerDTO> response = ledgerFacade.crearLedger(request, clienteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    @Operation(summary = "Listar ledgers de un cliente",
               description = "Devuelve todos los ledgers donde el cliente es miembro. Se usa clienteId como query param para pruebas. Soporta paginación, ordenamiento y filtro por nombre.")
    public ResponseEntity<PagedResponse<LedgerDTO>> listarLedgersDeCliente(
            @RequestParam String clienteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "sortBy", defaultValue = "nombre") String sortBy,
            @RequestParam(value = "dir", defaultValue = "asc") String dir) {
        boolean asc = !"desc".equalsIgnoreCase(dir);
        PagedResponse<LedgerDTO> response = ledgerFacade.listarLedgersDeCliente(clienteId, nombre, page, size, sortBy, asc);
        return ResponseEntity.ok(response);
    }
}