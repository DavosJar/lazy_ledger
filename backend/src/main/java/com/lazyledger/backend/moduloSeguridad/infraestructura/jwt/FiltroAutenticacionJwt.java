package com.lazyledger.backend.moduloSeguridad.infraestructura.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroAutenticacionJwt extends OncePerRequestFilter {

    @Autowired
    private UtilidadesJwt utilidadesJwt;

    @Autowired
    private UserDetailsService servicioDetallesUsuario;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String tokenJwt = parseJwt(request);
            if (tokenJwt != null && utilidadesJwt.validarTokenJwt(tokenJwt)) {
                String nombreUsuario = utilidadesJwt.obtenerNombreUsuarioDesdeTokenJwt(tokenJwt);

                UserDetails detallesUsuario = servicioDetallesUsuario.loadUserByUsername(nombreUsuario);
                UsernamePasswordAuthenticationToken autenticacion =
                    new UsernamePasswordAuthenticationToken(detallesUsuario, null, detallesUsuario.getAuthorities());
                autenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(autenticacion);
            }
        } catch (Exception e) {
            logger.error("No se puede establecer la autenticación del usuario: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}