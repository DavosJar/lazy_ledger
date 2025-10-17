package com.lazyledger.backend.moduloSeguridad.api.controllers;

import com.lazyledger.backend.moduloSeguridad.api.dto.RespuestaJwt;
import com.lazyledger.backend.moduloSeguridad.api.dto.SolicitudLogin;
import com.lazyledger.backend.moduloSeguridad.api.dto.SolicitudRegistro;
import com.lazyledger.backend.moduloSeguridad.api.dto.RespuestaRegistro;
import com.lazyledger.backend.moduloSeguridad.domain.servicios.ServicioAutenticacion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "APIs de gestión de autenticación")
public class ControladorAutenticacion {

    private final ServicioAutenticacion servicioAutenticacion;

    public ControladorAutenticacion(ServicioAutenticacion servicioAutenticacion) {
        this.servicioAutenticacion = servicioAutenticacion;
    }

    @PostMapping("/signin")
    @Operation(summary = "Autenticar usuario", description = "Autenticar usuario con nombre de usuario y contraseña")
    public ResponseEntity<RespuestaJwt> autenticarUsuario(@Valid @RequestBody SolicitudLogin solicitudLogin) {
        RespuestaJwt respuestaJwt = servicioAutenticacion.autenticarUsuario(solicitudLogin);
        return ResponseEntity.ok(respuestaJwt);
    }

    @PostMapping("/signup")
    @Operation(summary = "Registrar nuevo usuario", description = "Registrar un nuevo usuario con email, nombre de usuario y contraseña")
    public ResponseEntity<RespuestaRegistro> registrarUsuario(@Valid @RequestBody SolicitudRegistro solicitudRegistro) {
        servicioAutenticacion.registrarUsuario(solicitudRegistro);
        RespuestaRegistro respuesta = RespuestaRegistro.exito(solicitudRegistro.nombreUsuario());
        return ResponseEntity.ok(respuesta);
    }
}