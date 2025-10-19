package com.lazyledger.backend.moduloSeguridad.aplicacion;

import com.lazyledger.backend.commons.exceptions.NotFoundException;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.moduloSeguridad.infrastructure.persistance.postgres.SpringDataJpaUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Servicio intermedio para resolver el ClienteId a partir de un UserId.
 * No valida ni lanza errores de formato: delega a un handler global.
 */
@Service
public class ResolverClientePorUserIdService {

    private final SpringDataJpaUserRepository userRepository;

    public ResolverClientePorUserIdService(SpringDataJpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retorna el ClienteId al que pertenece el usuario dado.
     * @param userId UUID del usuario (autenticado)
     * @return ClienteId asociado
     * @throws NotFoundException si el user no está vinculado a un cliente
     */
    public ClienteId obtenerClienteId(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + userId));
        if (user.getCustomerId() == null) {
            throw new NotFoundException("El usuario no tiene cliente asociado: " + userId);
        }
        return ClienteId.of(user.getCustomerId());
    }
}
