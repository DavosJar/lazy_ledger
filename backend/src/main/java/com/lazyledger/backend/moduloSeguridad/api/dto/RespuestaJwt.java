package com.lazyledger.backend.moduloSeguridad.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de autenticación JWT")
public record RespuestaJwt(
    @Schema(description = "Token de acceso JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    String token,

    @Schema(description = "Nombre de usuario", example = "usuario@example.com")
    String nombreUsuario,

    @Schema(description = "Roles del usuario", example = "[\"USER\"]")
    String[] roles
) {
}