package com.lazyledger.backend.cliente.aplicacion;

import java.util.UUID;

import com.lazyledger.backend.cliente.dominio.Cliente;
import com.lazyledger.backend.cliente.dominio.repositorio.ClienteRepository;
public class ClienteUseCases {

    private final ClienteRepository clienteRepository;

    public ClienteUseCases(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente getClienteById(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
    }

    public void deleteCliente(UUID id) {
        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente no encontrado con ID: " + id);
        }
        clienteRepository.delete(id);
    }
    
    public Cliente updateCliente(Cliente cliente) {
        if (!clienteRepository.existsById(cliente.getId().value())) {
            throw new IllegalArgumentException("Cliente no encontrado con ID: " + cliente.getId());
        }
        return clienteRepository.save(cliente);
    }
}
