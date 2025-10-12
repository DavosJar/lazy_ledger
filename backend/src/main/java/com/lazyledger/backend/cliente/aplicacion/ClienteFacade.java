package com.lazyledger.backend.cliente.aplicacion;

import com.lazyledger.backend.cliente.presentacion.dto.ClienteDTO;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteSaveRequest;
import com.lazyledger.backend.cliente.presentacion.mapper.ClienteMapper;
import com.lazyledger.backend.cliente.dominio.repositorio.ClienteRepository;
import com.lazyledger.backend.commons.exceptions.ApplicationException;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteFacade {

    private final ClienteUseCases clienteUseCases;
    private final ClienteMapper clienteMapper;

    public ClienteFacade(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteUseCases = new ClienteUseCases(clienteRepository);
        this.clienteMapper = clienteMapper;
    }

    public ClienteDTO createCliente(ClienteSaveRequest clienteSaveRequest) {
        try {
            var cliente = clienteMapper.toDomain(clienteSaveRequest);
            var createdCliente = clienteUseCases.createCliente(cliente);
            return clienteMapper.toDTO(createdCliente);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("Datos inválidos en la solicitud de creación: " + e.getMessage(), e);
        }
    }

    public ClienteDTO getClienteById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            var cliente = clienteUseCases.getClienteById(uuid);
            return clienteMapper.toDTO(cliente);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("El ID proporcionado no es un UUID válido: " + id, e);
        }
    }

    public void deleteCliente(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            clienteUseCases.deleteCliente(uuid);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("El ID proporcionado no es un UUID válido: " + id, e);
        }
    }

    public ClienteDTO updateCliente(ClienteDTO clienteDTO) {
        try {
            var cliente = clienteMapper.toDomain(clienteDTO);
            var updatedCliente = clienteUseCases.updateCliente(cliente);
            return clienteMapper.toDTO(updatedCliente);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("Datos inválidos en la solicitud de actualización: " + e.getMessage(), e);
        }
    }

    public List<ClienteDTO> getAllClientes() {
        var clientes = clienteUseCases.getAllClientes();
        return clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

}
