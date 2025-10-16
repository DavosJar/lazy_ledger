package com.lazyledger.backend.cliente.aplicacion;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import com.lazyledger.backend.cliente.dominio.Cliente;
import com.lazyledger.backend.cliente.dominio.repositorio.ClienteRepository;
import com.lazyledger.backend.cliente.dominio.NombreCompleto;
import com.lazyledger.backend.cliente.dominio.Email;
import com.lazyledger.backend.cliente.dominio.Telefono;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.commons.enums.TipoCliente;
import com.lazyledger.backend.commons.exceptions.DuplicateException;
import com.lazyledger.backend.commons.exceptions.NotFoundException;
import com.lazyledger.backend.commons.exceptions.InfrastructureException;
public class ClienteUseCases {

    private final ClienteRepository clienteRepository;

    public ClienteUseCases(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Optional<Cliente> createCliente(Cliente cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail().toString())) {
            throw new DuplicateException("Ya existe un cliente con el email: " + cliente.getEmail());
        }
        try {
            return clienteRepository.save(cliente);
        } catch (Exception e) {
            throw new InfrastructureException("Error al guardar el cliente en la base de datos", e);
        }
    }

    public Optional<Cliente> createCliente(String nombre, String apellido, String email, String tipo, String telefono) {
        var nombreCompleto = NombreCompleto.of(nombre, apellido);
        var emailObj = Email.of(email);
        var tipoCliente = tipo != null ? TipoCliente.valueOf(tipo) : null;
        var telefonoObj = telefono != null ? Telefono.of(telefono) : null;

        var cliente = Cliente.create(
            ClienteId.of(java.util.UUID.randomUUID()),
            nombreCompleto,
            emailObj,
            tipoCliente,
            telefonoObj
        );

        return createCliente(cliente);
    }

    public Optional<Cliente> getClienteById(UUID id) {
        return clienteRepository.findById(id);

    }

    public void deleteCliente(UUID id) {
        if (!clienteRepository.existsById(id)) {
            throw new NotFoundException("Cliente no encontrado con ID: " + id);
        }
        try {
            clienteRepository.delete(id);
        } catch (Exception e) {
            throw new InfrastructureException("Error al eliminar el cliente", e);
        }
    }

    public Optional<Cliente> updateCliente(Cliente cliente) {
        if (!clienteRepository.existsById(cliente.getId().value())) {
            throw new NotFoundException("Cliente no encontrado con ID: " + cliente.getId());
        }
        // Check if email is taken by another client
        var existing = clienteRepository.findById(cliente.getId().value()).orElseThrow();
        if (!existing.getEmail().equals(cliente.getEmail()) && clienteRepository.existsByEmail(cliente.getEmail().toString())) {
            throw new DuplicateException("Ya existe otro cliente con el email: " + cliente.getEmail());
        }
        try {
            return clienteRepository.save(cliente);
        } catch (Exception e) {
            throw new InfrastructureException("Error al actualizar el cliente en la base de datos", e);
        }
    }

    public List<Cliente> getAllClientes() {
        try {
            return clienteRepository.findAll();
        } catch (Exception e) {
            throw new InfrastructureException("Error al obtener la lista de clientes", e);
        }
    }
}
