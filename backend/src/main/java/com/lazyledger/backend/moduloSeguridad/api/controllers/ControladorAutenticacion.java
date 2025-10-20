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
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import com.lazyledger.backend.moduloSeguridad.infraestructura.LoginFailAuditRepository;
import com.lazyledger.backend.moduloSeguridad.infraestructura.LoginFailAudit;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "APIs de gestión de autenticación")
public class ControladorAutenticacion {
    private static final Logger log = LoggerFactory.getLogger(ControladorAutenticacion.class);
    private final ConcurrentHashMap<String, Integer> failedAttemptsUser = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> failedAttemptsIp = new ConcurrentHashMap<>();

    private final ServicioAutenticacion servicioAutenticacion;
    @Autowired
    private LoginFailAuditRepository loginFailAuditRepository;

    public ControladorAutenticacion(ServicioAutenticacion servicioAutenticacion) {
        this.servicioAutenticacion = servicioAutenticacion;
    }

    @PostMapping("/signin")
    @Operation(summary = "Autenticar usuario", description = "Autenticar usuario con nombre de usuario y contraseña")
    public ResponseEntity<?> autenticarUsuario(@Valid @RequestBody SolicitudLogin solicitudLogin, HttpServletRequest request) {
        String username = solicitudLogin.nombreUsuario();
        String ip = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        int userFails = failedAttemptsUser.getOrDefault(username, 0);
        int ipFails = failedAttemptsIp.getOrDefault(ip, 0);
        if (userFails >= 5 || ipFails >= 5) {
            log.warn("LOGIN BLOCKED | user: {} | ip: {} | agent: {} | time: {} | userFails: {} | ipFails: {}", username, ip, userAgent, LocalDateTime.now(), userFails, ipFails);
            return ResponseEntity.status(429).body("Demasiados intentos fallidos. Bloqueado por seguridad.");
        }
        try {
            RespuestaJwt respuestaJwt = servicioAutenticacion.autenticarUsuario(solicitudLogin);
            failedAttemptsUser.remove(username);
            failedAttemptsIp.remove(ip);
            return ResponseEntity.ok(respuestaJwt);
        } catch (Exception e) {
            int countUser = failedAttemptsUser.merge(username, 1, Integer::sum);
            int countIp = failedAttemptsIp.merge(ip, 1, Integer::sum);
            log.warn("LOGIN FAIL | user: {} | ip: {} | agent: {} | time: {} | userFails: {} | ipFails: {}", username, ip, userAgent, LocalDateTime.now(), countUser, countIp);
            // Guardar en base de datos
            LoginFailAudit audit = new LoginFailAudit(username, ip, userAgent, LocalDateTime.now(), Math.max(countUser, countIp));
            loginFailAuditRepository.save(audit);
            return ResponseEntity.status(401).body("Login FAIL. Intentos fallidos usuario: " + countUser + ", IP: " + countIp);
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        if (xf != null && !xf.isEmpty()) {
            return xf.split(",")[0];
        }
        return request.getRemoteAddr();
    }

    @PostMapping("/signup")
    @Operation(summary = "Registrar nuevo usuario", description = "Registrar un nuevo usuario con email, nombre de usuario y contraseña")
    public ResponseEntity<RespuestaRegistro> registrarUsuario(@Valid @RequestBody SolicitudRegistro solicitudRegistro) {
        servicioAutenticacion.registrarUsuario(solicitudRegistro);
        RespuestaRegistro respuesta = RespuestaRegistro.exito(solicitudRegistro.nombreUsuario());
        return ResponseEntity.ok(respuesta);
    }
}