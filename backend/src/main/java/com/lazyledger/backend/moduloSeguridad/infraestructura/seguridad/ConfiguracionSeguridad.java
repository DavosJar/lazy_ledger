package com.lazyledger.backend.moduloSeguridad.infraestructura.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lazyledger.backend.moduloSeguridad.infraestructura.jwt.FiltroAutenticacionJwt;
import com.lazyledger.backend.moduloSeguridad.infraestructura.jwt.PuntoEntradaAutenticacionJwt;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfiguracionSeguridad {

    @Value("${app.seguridad.habilitada:false}")
    private boolean seguridadHabilitada;

    private final PuntoEntradaAutenticacionJwt puntoEntradaAutenticacionJwt;
    private final FiltroAutenticacionJwt filtroAutenticacionJwt;

    public ConfiguracionSeguridad(@Autowired(required = false) PuntoEntradaAutenticacionJwt puntoEntradaAutenticacionJwt,
                                  @Autowired(required = false) FiltroAutenticacionJwt filtroAutenticacionJwt) {
        this.puntoEntradaAutenticacionJwt = puntoEntradaAutenticacionJwt;
        this.filtroAutenticacionJwt = filtroAutenticacionJwt;
    }

    @Bean
    public PasswordEncoder codificadorContrasena() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager administradorAutenticacion(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain cadenaFiltroSeguridad(HttpSecurity http) throws Exception {
        if (!seguridadHabilitada) {
            // Si la seguridad está deshabilitada, permitir todas las solicitudes
            http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authz -> authz
                            .anyRequest().permitAll());
            return http.build();
        }

        // Configuración de seguridad cuando está habilitada
        if (puntoEntradaAutenticacionJwt != null && filtroAutenticacionJwt != null) {
            http.csrf(AbstractHttpConfigurer::disable)
                    .exceptionHandling(exception -> exception
                            .authenticationEntryPoint(puntoEntradaAutenticacionJwt))
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(authz -> authz
                            .requestMatchers("/api/auth/**").permitAll()
                            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                            .anyRequest().authenticated());

            http.addFilterBefore(filtroAutenticacionJwt, UsernamePasswordAuthenticationFilter.class);
        } else {
            // Fallback si los beans JWT no están disponibles
            http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authz -> authz
                            .anyRequest().permitAll());
        }

        return http.build();
    }

}