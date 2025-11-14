package com.lazyledger.backend.moduloLedger.ledger.presentacion;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.api.PagedResponse;
import com.lazyledger.backend.commons.UserContextService;
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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Controller REST para gestión de Ledgers.
 * 
 * Los endpoints de este controller requieren autenticación JWT.
 * El clienteId se obtiene del token JWT del usuario autenticado mediante UserContextService.
 */
@RestController
@RequestMapping("/ledgers")
@Tag(name = "Ledgers", description = "API para gestión de ledgers financieros")
@SecurityRequirement(name = "Bearer Authentication")
public class LedgerController {

    // Lista blanca de campos permitidos para ordenamiento
    private static final Set<String> CAMPOS_ORDENAMIENTO_PERMITIDOS = Set.of(
        "nombre", "descripcion", "estado"
    );

    // Lista blanca de direcciones de ordenamiento
    private static final Set<String> DIRECCIONES_ORDENAMIENTO_PERMITIDAS = Set.of(
        "asc", "desc"
    );

    private final LedgerFacade ledgerFacade;
    private final UserContextService userContextService;

    public LedgerController(LedgerFacade ledgerFacade, UserContextService userContextService) {
        this.ledgerFacade = ledgerFacade;
        this.userContextService = userContextService;
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

    @GetMapping
    @Operation(summary = "Listar ledgers de un cliente",
               description = "Devuelve todos los ledgers donde el cliente es miembro. Requiere token JWT para autenticación. Soporta paginación, ordenamiento y filtro por nombre.")
    public ResponseEntity<PagedResponse<LedgerDTO>> listarLedgersDeCliente(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "sortBy", defaultValue = "nombre") String sortBy,
            @RequestParam(value = "dir", defaultValue = "asc") String dir) {

        // Obtener clienteId del token JWT (SecurityContext)
        String clienteId = obtenerClienteIdDesdeToken();

        // Validar y sanitizar parámetros para prevenir inyección
        String sanitizedSortBy = validateSortField(sortBy);
        String sanitizedDir = validateSortDirection(dir);
        String sanitizedNombre = sanitizeSearchTerm(nombre);

        boolean asc = "asc".equalsIgnoreCase(sanitizedDir);
        PagedResponse<LedgerDTO> response = ledgerFacade.listarLedgersDeCliente(clienteId, sanitizedNombre, page, size, sanitizedSortBy, asc);
        return ResponseEntity.ok(response);
    }

    /**
     * Valida que el campo de ordenamiento esté en la lista blanca
     */
    private String validateSortField(String sortBy) {
        if (sortBy == null || sortBy.trim().isEmpty()) {
            return "nombre"; // valor por defecto
        }

        String normalizedField = sortBy.trim().toLowerCase();
        if (!CAMPOS_ORDENAMIENTO_PERMITIDOS.contains(normalizedField)) {
            throw new IllegalArgumentException("Campo de ordenamiento no permitido: " + sortBy);
        }

        return normalizedField;
    }

    /**
     * Valida que la dirección de ordenamiento sea válida
     */
    private String validateSortDirection(String dir) {
        if (dir == null || dir.trim().isEmpty()) {
            return "asc"; // valor por defecto
        }

        String normalizedDir = dir.trim().toLowerCase();
        if (!DIRECCIONES_ORDENAMIENTO_PERMITIDAS.contains(normalizedDir)) {
            throw new IllegalArgumentException("Dirección de ordenamiento no permitida: " + dir);
        }

        return normalizedDir;
    }

    /**
     * Sanitiza términos de búsqueda para prevenir inyección
     */
    private String sanitizeSearchTerm(String term) {
        if (term == null || term.trim().isEmpty()) {
            return null;
        }

        String sanitized = term.trim();

        // Limitar longitud máxima
        if (sanitized.length() > 100) {
            throw new IllegalArgumentException("Término de búsqueda demasiado largo");
        }

        // Remover caracteres potencialmente peligrosos
        sanitized = sanitized.replaceAll("[<>\"'%;()&+]", "");

        // Validar que no contenga palabras clave SQL
        String lowerSanitized = sanitized.toLowerCase();
        if (lowerSanitized.contains("select") || lowerSanitized.contains("union") ||
            lowerSanitized.contains("drop") || lowerSanitized.contains("delete") ||
            lowerSanitized.contains("update") || lowerSanitized.contains("insert")) {
            throw new IllegalArgumentException("Término de búsqueda contiene caracteres no permitidos");
        }

        return sanitized;
    }

    /**
     * Obtiene el clienteId del usuario autenticado desde el SecurityContext.
     * Usa UserContextService para mapear userId (username del JWT) a clienteId.
     */
    private String obtenerClienteIdDesdeToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Usuario no autenticado");
        }

        // Obtener userId (username) del JWT
        String userId = authentication.getName();

        // Mapear userId a clienteId usando el servicio de contexto
        return userContextService.getClienteIdFromUserId(userId);
    }
}