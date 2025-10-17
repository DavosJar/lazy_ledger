package com.lazyledger.backend.modulocliente.infraestructura;

import com.lazyledger.backend.commons.exceptions.InfrastructureException;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.modulocliente.dominio.Cliente;
import com.lazyledger.backend.modulocliente.dominio.Email;
import com.lazyledger.backend.modulocliente.dominio.NombreCompleto;
import com.lazyledger.backend.modulocliente.dominio.Telefono;
import com.lazyledger.backend.modulocliente.dominio.repositorio.ClienteRepository;

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
    public Optional<Cliente> save(Cliente cliente) {
        try {
            ClienteEntity entity = toEntity(cliente);
            ClienteEntity saved = jpaRepository.save(entity);
            return Optional.of(toDomain(saved));
        } catch (Exception e) {
            throw new InfrastructureException("Error al guardar el cliente en la base de datos", e);
        }
    }

    @Override
    public Optional<Cliente> findById(UUID id) {
        try {
            return jpaRepository.findById(id).map(this::toDomain);
        } catch (Exception e) {
            throw new InfrastructureException("Error al buscar el cliente por ID en la base de datos", e);
        }
    }

    @Override
    public List<Cliente> findAll() {
        try {
            return jpaRepository.findAll().stream().map(this::toDomain).toList();
        } catch (Exception e) {
            throw new InfrastructureException("Error al obtener todos los clientes de la base de datos", e);
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            jpaRepository.deleteById(id);
        } catch (Exception e) {
            throw new InfrastructureException("Error al eliminar el cliente de la base de datos", e);
        }
    }

    @Override
    public boolean existsById(UUID id) {
        try {
            return jpaRepository.existsById(id);
        } catch (Exception e) {
            throw new InfrastructureException("Error al verificar existencia del cliente por ID", e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try {
            return jpaRepository.existsByEmail(email);
        } catch (Exception e) {
            throw new InfrastructureException("Error al verificar existencia del cliente por email", e);
        }
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
                entity.getTelefono() != null ? Telefono.of(entity.getTelefono()) : null,
                null, // Dirección no mapeada en esta entidad
                null, // Estado no mapeado en esta entidad
                null, // Fecha de nacimiento no mapeada en esta entidad
                false // isEmailVerified no mapeado en esta entidad
        );
    }
}