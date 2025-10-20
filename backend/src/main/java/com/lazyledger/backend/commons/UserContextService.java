package com.lazyledger.backend.commons;

import com.lazyledger.backend.modulocliente.infraestructura.ClienteJpaRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio para obtener información del contexto del usuario autenticado.
 * Mapea userId (username del JWT) a clienteId.
 */
@Service
public class UserContextService {
    
    private final ClienteJpaRepository clienteRepository;
    
    public UserContextService(ClienteJpaRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    /**
     * Obtiene el clienteId del único cliente en el sistema.
     * 
     * @param userId username del usuario autenticado (obtenido del JWT) - no usado por ahora
     * @return clienteId (UUID) del único cliente
     * @throws IllegalStateException si no hay clientes en el sistema
     */
    public String getClienteIdFromUserId(String userId) {
        // Solo hay un cliente en el sistema, rescatar el primero
        var cliente = clienteRepository.findAll().stream()
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No se encontró ningún cliente en el sistema"));
        
        return cliente.getId().toString();
    }
}
