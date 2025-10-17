package com.lazyledger.backend.moduloSeguridad.infraestructura.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class UtilidadesJwt {

    @Value("${app.jwt.secreto}")
    private String jwtSecreto;

    @Value("${app.jwt.expiracion}")
    private int jwtExpiracionMs;

    private SecretKey obtenerClaveFirma() {
        return Keys.hmacShaKeyFor(jwtSecreto.getBytes());
    }

    public String generarToken(Authentication autenticacion) {
        UserDetails usuarioPrincipal = (UserDetails) autenticacion.getPrincipal();

        return Jwts.builder()
                .setSubject(usuarioPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiracionMs))
                .signWith(obtenerClaveFirma(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generarTokenDesdeNombreUsuario(String nombreUsuario) {
        return Jwts.builder()
                .setSubject(nombreUsuario)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiracionMs))
                .signWith(obtenerClaveFirma(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String obtenerNombreUsuarioDesdeTokenJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(obtenerClaveFirma())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public <T> T obtenerReclamacionDesdeTokenJwt(String token, Function<Claims, T> resolvedorReclamaciones) {
        final Claims reclamaciones = Jwts.parserBuilder()
                .setSigningKey(obtenerClaveFirma())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return resolvedorReclamaciones.apply(reclamaciones);
    }

    public Date obtenerFechaExpiracionDesdeTokenJwt(String token) {
        return obtenerReclamacionDesdeTokenJwt(token, Claims::getExpiration);
    }

    public boolean validarTokenJwt(String tokenAutenticacion) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(obtenerClaveFirma())
                    .build()
                    .parseClaimsJws(tokenAutenticacion);
            return true;
        } catch (MalformedJwtException e) {
            System.err.println("Token JWT inválido: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("Token JWT expirado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Token JWT no soportado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Cadena de reclamaciones JWT vacía: " + e.getMessage());
        }
        return false;
    }
}