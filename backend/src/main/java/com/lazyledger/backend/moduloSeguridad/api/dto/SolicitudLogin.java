package com.lazyledger.backend.moduloSeguridad.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Solicitud de inicio de sesión")
public record SolicitudLogin(
    @Schema(description = "Nombre de usuario para inicio de sesión", example = "juan_perez", required = true)
    @NotBlank(message = "El nombre de usuario es requerido")
    String nombreUsuario,

    @Schema(description = "Contraseña del usuario", example = "password123", required = true)
    @NotBlank(message = "La contraseña es requerida")
    String contrasena
) {
}