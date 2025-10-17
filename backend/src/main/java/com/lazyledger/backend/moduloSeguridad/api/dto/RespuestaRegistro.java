package com.lazyledger.backend.moduloSeguridad.api.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de registro de usuario")
public record RespuestaRegistro(
    @Schema(description = "Mensaje de éxito", example = "Usuario registrado exitosamente")
    String mensaje,

    @Schema(description = "Indica si el registro fue exitoso", example = "true")
    boolean exito,

    @Schema(description = "Email del usuario que fue registrado", example = "juan.perez@example.com")
    String nombreUsuario
) {
    public static RespuestaRegistro exito(String nombreUsuario) {
        return new RespuestaRegistro("Usuario registrado exitosamente. Por favor complete su perfil.", true, nombreUsuario);
    }
}