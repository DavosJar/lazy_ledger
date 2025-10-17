package com.lazyledger.backend.moduloSeguridad.aplicacion;

import com.lazyledger.backend.commons.IdGenerator;
import com.lazyledger.backend.modulocliente.dominio.repositorio.ClienteRepository;
import com.lazyledger.backend.moduloSeguridad.infrastructure.persistance.postgres.SpringDataJpaUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ConfiguracionAplicacionSeguridad {

    @Bean
    public RegisterUserUseCase registerUserUseCase(PasswordEncoder passwordEncoder,
                                                    SpringDataJpaUserRepository userRepository,
                                                    ClienteRepository clienteRepository,
                                                    IdGenerator idGenerator) {
        return new RegisterUserUseCase(passwordEncoder, userRepository, clienteRepository, idGenerator);
    }
}