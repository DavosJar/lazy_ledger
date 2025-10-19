package com.lazyledger.backend.moduloLedger.miembroLedger.presentacion;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.api.PagedResponse;
import com.lazyledger.backend.moduloLedger.miembroLedger.aplicacion.MiembroLedgerFacade;
import com.lazyledger.backend.moduloLedger.miembroLedger.presentacion.dto.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@RestController
@RequestMapping("/miembros-ledger")
@Tag(name = "Miembros de Ledger", description = "API para gestión de membresías en ledgers")
public class MiembroLedgerController {

    private final MiembroLedgerFacade miembroLedgerFacade;

    public MiembroLedgerController(MiembroLedgerFacade miembroLedgerFacade) {
        this.miembroLedgerFacade = miembroLedgerFacade;
    }

    // === ENDPOINTS PARA USUARIOS ===

    @GetMapping("/usuario/{clienteId}/ledgers")
    @Operation(
        summary = "Obtener ledgers de un usuario",
        description = "Retorna una lista paginada de todos los ledgers donde el usuario es miembro activo. " +
                     "Perfecto para que un usuario vea todos sus ledgers."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Lista de ledgers del usuario obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResponse.class))
        )
    })
    public ResponseEntity<PagedResponse<MiembroLedgerDTO>> getLedgersDeUsuario(
            @Parameter(description = "ID del cliente/usuario", example = "01HXXXXXXXXXXXXXXXXXXXXX") @PathVariable String clienteId,
            @Parameter(description = "Número de página (comienza en 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página (máximo 100)", example = "10") @RequestParam(defaultValue = "10") int size) {
        UUID uuid = UUID.fromString(clienteId);
        PagedResponse<MiembroLedgerDTO> response = miembroLedgerFacade.obtenerLedgersDeUsuario(uuid, page, size);
        return ResponseEntity.ok(response);
    }

    // === ENDPOINTS PARA GESTIÓN DE MIEMBROS ===

    @PostMapping("/invitar")
    @Operation(
        summary = "Invitar miembro a un ledger",
        description = "Invita a un cliente existente a unirse a un ledger específico. Solo propietarios pueden invitar."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Miembro invitado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Datos inválidos en la solicitud"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "No tienes permisos para invitar miembros"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "409",
            description = "El cliente ya es miembro de este ledger"
        )
    })
    public ResponseEntity<ApiResponse<MiembroLedgerDTO>> invitarMiembro(
            @Parameter(description = "Datos de la invitación") @RequestBody InvitarMiembroRequest request,
            @Parameter(description = "ID del solicitante (debe ser propietario)", example = "01HXXXXXXXXXXXXXXXXXXXXX")
            @RequestHeader("X-Solicitante-Id") String solicitanteId) {
        UUID solicitanteUuid = UUID.fromString(solicitanteId);
        UUID clienteUuid = UUID.fromString(request.getClienteId());
        UUID ledgerUuid = UUID.fromString(request.getLedgerId());

        ApiResponse<MiembroLedgerDTO> response = miembroLedgerFacade.invitarMiembro(solicitanteUuid, clienteUuid, ledgerUuid);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/cambiar-rol")
    @Operation(
        summary = "Cambiar rol de un miembro",
        description = "Cambia el rol de un miembro en un ledger. Solo propietarios pueden cambiar roles."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Rol cambiado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Datos inválidos en la solicitud"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "No tienes permisos para cambiar roles"
        )
    })
    public ResponseEntity<ApiResponse<MiembroLedgerDTO>> cambiarRol(
            @Parameter(description = "Datos del cambio de rol") @RequestBody CambiarRolRequest request,
            @Parameter(description = "ID del solicitante (debe ser propietario)", example = "01HXXXXXXXXXXXXXXXXXXXXX")
            @RequestHeader("X-Solicitante-Id") String solicitanteId) {
        UUID solicitanteUuid = UUID.fromString(solicitanteId);
        UUID clienteUuid = UUID.fromString(request.getClienteId());
        UUID ledgerUuid = UUID.fromString(request.getLedgerId());

        ApiResponse<MiembroLedgerDTO> response = miembroLedgerFacade.cambiarRol(solicitanteUuid, clienteUuid, ledgerUuid, request.getNuevoRol());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/expulsar")
    @Operation(
        summary = "Expulsar miembro de un ledger",
        description = "Expulsa a un miembro de un ledger. Solo propietarios pueden expulsar miembros."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Miembro expulsado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "No tienes permisos para expulsar miembros"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Miembro no encontrado"
        )
    })
    public ResponseEntity<ApiResponse<Void>> expulsarMiembro(
            @Parameter(description = "Datos de la expulsión") @RequestBody ExpulsarMiembroRequest request,
            @Parameter(description = "ID del solicitante (debe ser propietario)", example = "01HXXXXXXXXXXXXXXXXXXXXX")
            @RequestHeader("X-Solicitante-Id") String solicitanteId) {
        UUID solicitanteUuid = UUID.fromString(solicitanteId);
        UUID clienteUuid = UUID.fromString(request.getClienteId());
        UUID ledgerUuid = UUID.fromString(request.getLedgerId());

        ApiResponse<Void> response = miembroLedgerFacade.expulsarMiembro(solicitanteUuid, clienteUuid, ledgerUuid);
        return ResponseEntity.ok(response);
    }

    // === ENDPOINTS PARA ADMINISTRACIÓN ===

    @GetMapping("/ledger/{ledgerId}/miembros")
    @Operation(
        summary = "Obtener miembros activos de un ledger",
        description = "Retorna una lista paginada de todos los miembros activos de un ledger específico."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Lista de miembros obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResponse.class))
        )
    })
    public ResponseEntity<PagedResponse<MiembroLedgerDTO>> getMiembrosActivos(
            @Parameter(description = "ID del ledger", example = "01HXXXXXXXXXXXXXXXXXXXXX") @PathVariable String ledgerId,
            @Parameter(description = "Número de página (comienza en 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página (máximo 100)", example = "10") @RequestParam(defaultValue = "10") int size) {
        UUID uuid = UUID.fromString(ledgerId);
        PagedResponse<MiembroLedgerDTO> response = miembroLedgerFacade.obtenerMiembrosActivos(uuid, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{clienteId}/{ledgerId}")
    @Operation(
        summary = "Obtener membresía específica",
        description = "Retorna la información de una membresía específica de un cliente en un ledger."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Membresía encontrada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Membresía no encontrada"
        )
    })
    public ResponseEntity<ApiResponse<MiembroLedgerDTO>> getMiembro(
            @Parameter(description = "ID del cliente", example = "01HXXXXXXXXXXXXXXXXXXXXX") @PathVariable String clienteId,
            @Parameter(description = "ID del ledger", example = "01HXXXXXXXXXXXXXXXXXXXXX") @PathVariable String ledgerId) {
        UUID clienteUuid = UUID.fromString(clienteId);
        UUID ledgerUuid = UUID.fromString(ledgerId);

        ApiResponse<MiembroLedgerDTO> response = miembroLedgerFacade.obtenerMiembro(clienteUuid, ledgerUuid);
        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar-ledger")
    @Operation(
        summary = "Eliminar un ledger completo",
        description = "Elimina permanentemente un ledger y todas sus membresías. Solo propietarios pueden eliminar ledgers."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Ledger eliminado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "403",
            description = "No tienes permisos para eliminar este ledger"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Ledger no encontrado"
        )
    })
    public ResponseEntity<ApiResponse<Void>> eliminarLedger(
            @Parameter(description = "Datos de eliminación del ledger") @RequestBody EliminarLedgerRequest request,
            @Parameter(description = "ID del solicitante (debe ser propietario)", example = "01HXXXXXXXXXXXXXXXXXXXXX")
            @RequestHeader("X-Solicitante-Id") String solicitanteId) {
        UUID solicitanteUuid = UUID.fromString(solicitanteId);
        UUID ledgerUuid = UUID.fromString(request.getLedgerId());

        ApiResponse<Void> response = miembroLedgerFacade.eliminarLedger(solicitanteUuid, ledgerUuid);
        return ResponseEntity.ok(response);
    }

        // === ENDPOINTS DEMO PARA INVITACIONES ===

        @GetMapping("/demo/invitar")
        @Operation(summary = "[DEMO] Invitar miembro con parámetros por URL")
        public ResponseEntity<ApiResponse<MiembroLedgerDTO>> invitarMiembroDemo(
                @RequestParam String solicitanteId,
                @RequestParam String clienteId,
                @RequestParam String ledgerId) {
        
            UUID solicitanteUuid = UUID.fromString(solicitanteId.trim());
            UUID clienteUuid = UUID.fromString(clienteId.trim());
            UUID ledgerUuid = UUID.fromString(ledgerId.trim());
        
            ApiResponse<MiembroLedgerDTO> response = miembroLedgerFacade.invitarMiembro(solicitanteUuid, clienteUuid, ledgerUuid);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @GetMapping("/demo/aceptar")
        @Operation(summary = "[DEMO] Aceptar invitación con parámetros por URL")
        public ResponseEntity<ApiResponse<MiembroLedgerDTO>> aceptarInvitacionDemo(
                @RequestParam String clienteId,
                @RequestParam String ledgerId) {
        
            UUID clienteUuid = UUID.fromString(clienteId.trim());
            UUID ledgerUuid = UUID.fromString(ledgerId.trim());
        
            ApiResponse<MiembroLedgerDTO> response = miembroLedgerFacade.aceptarInvitacion(clienteUuid, ledgerUuid);
            return ResponseEntity.ok(response);
        }

        @GetMapping("/demo/rechazar")
        @Operation(summary = "[DEMO] Rechazar invitación con parámetros por URL")
        public ResponseEntity<ApiResponse<Void>> rechazarInvitacionDemo(
                @RequestParam String clienteId,
                @RequestParam String ledgerId) {
        
            UUID clienteUuid = UUID.fromString(clienteId.trim());
            UUID ledgerUuid = UUID.fromString(ledgerId.trim());
        
            ApiResponse<Void> response = miembroLedgerFacade.rechazarInvitacion(clienteUuid, ledgerUuid);
            return ResponseEntity.ok(response);
        }

        @GetMapping("/demo/pendientes")
        @Operation(summary = "[DEMO] Listar invitaciones pendientes con parámetros por URL")
        public ResponseEntity<ApiResponse<java.util.List<MiembroLedgerDTO>>> listarInvitacionesPendientesDemo(
                @RequestParam String clienteId) {
        
            UUID clienteUuid = UUID.fromString(clienteId.trim());
        
            ApiResponse<java.util.List<MiembroLedgerDTO>> response = miembroLedgerFacade.listarInvitacionesPendientes(clienteUuid);
            return ResponseEntity.ok(response);
        }
}