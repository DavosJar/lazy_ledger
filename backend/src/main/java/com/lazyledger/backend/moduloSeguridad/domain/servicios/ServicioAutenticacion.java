package com.lazyledger.backend.moduloSeguridad.domain.servicios;

import com.lazyledger.backend.moduloSeguridad.api.exceptions.AutenticacionException;
import com.lazyledger.backend.moduloSeguridad.api.dto.RespuestaJwt;
import com.lazyledger.backend.moduloSeguridad.api.dto.SolicitudLogin;
import com.lazyledger.backend.moduloSeguridad.api.dto.SolicitudRegistro;
import com.lazyledger.backend.moduloSeguridad.aplicacion.RegisterUserUseCase;
import com.lazyledger.backend.moduloSeguridad.infraestructura.jwt.UtilidadesJwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioAutenticacion {

    private final AuthenticationManager administradorAutenticacion;
    private final UtilidadesJwt utilidadesJwt;
    private final RegisterUserUseCase casoUsoRegistrarUsuario;

    public ServicioAutenticacion(AuthenticationManager administradorAutenticacion,
                                 UtilidadesJwt utilidadesJwt,
                                 RegisterUserUseCase casoUsoRegistrarUsuario) {
        this.administradorAutenticacion = administradorAutenticacion;
        this.utilidadesJwt = utilidadesJwt;
        this.casoUsoRegistrarUsuario = casoUsoRegistrarUsuario;
    }

    public RespuestaJwt autenticarUsuario(SolicitudLogin solicitudLogin) {
        try {
            Authentication autenticacion = administradorAutenticacion.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            solicitudLogin.nombreUsuario(),
                            solicitudLogin.contrasena()));

            SecurityContextHolder.getContext().setAuthentication(autenticacion);
            String jwt = utilidadesJwt.generarToken(autenticacion);

            UserDetails detallesUsuario = (UserDetails) autenticacion.getPrincipal();
            List<String> roles = detallesUsuario.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            return new RespuestaJwt(jwt, detallesUsuario.getUsername(), roles.toArray(new String[0]));
        } catch (BadCredentialsException e) {
            throw new AutenticacionException("Nombre de usuario o contraseña incorrectos");
        } catch (AuthenticationException e) {
            throw new AutenticacionException("Error en el proceso de autenticación");
        }
    }

    public void registrarUsuario(SolicitudRegistro solicitudRegistro) {
        casoUsoRegistrarUsuario.execute(solicitudRegistro.nombreUsuario(), solicitudRegistro.contrasena(),
                                        solicitudRegistro.confirmarContrasena(), solicitudRegistro.email());
    }
}