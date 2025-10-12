package com.lazyledger.backend.cliente.aplicacion;

import com.lazyledger.backend.cliente.presentacion.dto.ClienteDTO;
import com.lazyledger.backend.cliente.presentacion.dto.ClienteSaveRequest;
import com.lazyledger.backend.cliente.presentacion.mapper.ClienteMapper;
import com.lazyledger.backend.cliente.dominio.repositorio.ClienteRepository;
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
        var cliente = clienteMapper.toDomain(clienteSaveRequest);
        var createdCliente = clienteUseCases.createCliente(cliente);
        return clienteMapper.toDTO(createdCliente);
    }

    public ClienteDTO getClienteById(String id) {
        var cliente = clienteUseCases.getClienteById(UUID.fromString(id));
        return clienteMapper.toDTO(cliente);
    }

    public void deleteCliente(String id) {
        clienteUseCases.deleteCliente(UUID.fromString(id));
    }

    public ClienteDTO updateCliente(ClienteDTO clienteDTO) {
        var cliente = clienteMapper.toDomain(clienteDTO);
        var updatedCliente = clienteUseCases.updateCliente(cliente);
        return clienteMapper.toDTO(updatedCliente);
    }

    public List<ClienteDTO> getAllClientes() {
        var clientes = clienteUseCases.getAllClientes();
        return clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

}
