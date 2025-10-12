package com.lazyledger.backend.cliente.infraestructura;

import com.lazyledger.backend.cliente.dominio.Cliente;
import com.lazyledger.backend.cliente.dominio.Email;
import com.lazyledger.backend.cliente.dominio.NombreCompleto;
import com.lazyledger.backend.cliente.dominio.Telefono;
import com.lazyledger.backend.cliente.dominio.repositorio.ClienteRepository;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaClienteRepositoryImpl implements ClienteRepository {

    private final ClienteJpaRepository jpaRepository;

    public JpaClienteRepositoryImpl(ClienteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = toEntity(cliente);
        ClienteEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Cliente> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Cliente> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }

    private ClienteEntity toEntity(Cliente cliente) {
        return new ClienteEntity(
                cliente.getId().value(),
                cliente.getNombreCompleto().getNombre(),
                cliente.getNombreCompleto().getApellido(),
                cliente.getEmail().toString(),
                cliente.getTipo(),
                cliente.getTelefono() != null ? cliente.getTelefono().toString() : null
        );
    }

    private Cliente toDomain(ClienteEntity entity) {
        return Cliente.rehydrate(
                ClienteId.of(entity.getId()),
                NombreCompleto.of(entity.getNombre(), entity.getApellido()),
                Email.of(entity.getEmail()),
                entity.getTipo(),
                entity.getTelefono() != null ? Telefono.of(entity.getTelefono()) : null
        );
    }
}