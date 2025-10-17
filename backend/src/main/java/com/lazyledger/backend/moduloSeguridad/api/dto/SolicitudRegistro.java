package com.lazyledger.backend.moduloSeguridad.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud de registro de usuario")
public record SolicitudRegistro(
    @Schema(description = "Nombre de usuario para inicio de sesión (identificador único)", example = "juan_perez", required = true)
    @NotBlank(message = "El nombre de usuario es requerido")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "El nombre de usuario solo puede contener letras, números y guiones bajos")
    String nombreUsuario,

    @Schema(description = "Dirección de email para verificación de cuenta y comunicación", example = "juan.perez@example.com", required = true)
    @NotBlank(message = "El email es requerido")
    @Email(message = "Formato de email inválido")
    String email,

    @Schema(description = "Contraseña del usuario", example = "Password123!", required = true)
    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 8, max = 128, message = "La contraseña debe tener entre 8 y 128 caracteres")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "La contraseña debe contener al menos una letra mayúscula, una minúscula, un número y un carácter especial"
    )
    String contrasena,

    @Schema(description = "Confirmación de contraseña (debe coincidir con la contraseña)", example = "Password123!", required = true)
    @NotBlank(message = "La confirmación de contraseña es requerida")
    String confirmarContrasena
) {
}