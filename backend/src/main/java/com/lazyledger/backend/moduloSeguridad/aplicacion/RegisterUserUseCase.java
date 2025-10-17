package com.lazyledger.backend.moduloSeguridad.aplicacion;

import com.lazyledger.backend.commons.IdGenerator;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.modulocliente.dominio.Cliente;
import com.lazyledger.backend.modulocliente.dominio.Email;
import com.lazyledger.backend.modulocliente.dominio.repositorio.ClienteRepository;
import com.lazyledger.backend.moduloSeguridad.infrastructure.persistance.postgres.SpringDataJpaUserRepository;
import com.lazyledger.backend.moduloSeguridad.infrastructure.persistance.entity.UserDbo;
import com.lazyledger.backend.commons.exceptions.DuplicateException;
import com.lazyledger.backend.commons.exceptions.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

public class RegisterUserUseCase {

    private final PasswordEncoder passwordEncoder;
    private final SpringDataJpaUserRepository userRepository;
    private final ClienteRepository clienteRepository;
    private final IdGenerator idGenerator;

    public RegisterUserUseCase(PasswordEncoder passwordEncoder,
                                SpringDataJpaUserRepository userRepository,
                                ClienteRepository clienteRepository,
                                IdGenerator idGenerator) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.clienteRepository = clienteRepository;
        this.idGenerator = idGenerator;
    }

    @Transactional
    public void execute(String username, String password, String confirmPassword, String email) {
        if (!password.equals(confirmPassword)) {
            throw new ValidationException("La confirmación de contraseña no coincide con la contraseña proporcionada");
        }
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateException(
                "Ya existe una cuenta con este nombre de usuario: " + username
            );
        }

        // Verificar si el email ya existe en clientes
        if (clienteRepository.existsByEmail(email)) {
            throw new DuplicateException(
                "Ya existe una cuenta con este email: " + email
            );
        }

        // Generar ID para el cliente usando el generador UUID v7
        UUID clienteId = idGenerator.nextId();

        // Crear perfil de cliente mínimo con solo email e ID
        var emailObj = Email.of(email);
        var cliente = Cliente.createMinimal(ClienteId.of(clienteId), emailObj);

        // Guardar cliente en la base de datos
        clienteRepository.save(cliente);

        // Crear registro de usuario para autenticación
        UUID userId = idGenerator.nextId();
        UserDbo user = new UserDbo(
                userId, // user_id usando UUID v7
                clienteId, // customer_id
                username,
                passwordEncoder.encode(password),
                Instant.now()
        );

        // Guardar usuario en la base de datos
        userRepository.save(user);
    }
}