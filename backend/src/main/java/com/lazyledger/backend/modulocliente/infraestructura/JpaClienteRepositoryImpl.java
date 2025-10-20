package com.lazyledger.backend.modulocliente.infraestructura;

import com.lazyledger.backend.commons.exceptions.InfrastructureException;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.modulocliente.dominio.Cliente;
import com.lazyledger.backend.modulocliente.dominio.Direccion;
import com.lazyledger.backend.modulocliente.dominio.Email;
import com.lazyledger.backend.modulocliente.dominio.NombreCompleto;
import com.lazyledger.backend.modulocliente.dominio.Telefono;
import com.lazyledger.backend.modulocliente.dominio.repositorio.ClienteRepository;

import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaClienteRepositoryImpl implements ClienteRepository {

    private static final Logger logger = LoggerFactory.getLogger(JpaClienteRepositoryImpl.class);

    private final ClienteJpaRepository jpaRepository;

    public JpaClienteRepositoryImpl(ClienteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Cliente> save(Cliente cliente) {
        try {
            logger.info("=== REPOSITORIO CLIENTE: Iniciando save ===");
            logger.info("Cliente ID: {}", cliente.getId().value());
            logger.info("Cliente Email: {}", cliente.getEmail().toString());

            ClienteEntity entity = toEntity(cliente);
            logger.info("=== REPOSITORIO CLIENTE: Entity creado ===");

            ClienteEntity saved = jpaRepository.save(entity);
            logger.info("=== REPOSITORIO CLIENTE: Entity guardado en BD ===");

            Cliente clienteGuardado = toDomain(saved);
            logger.info("=== REPOSITORIO CLIENTE: Cliente convertido a dominio ===");
            return Optional.of(clienteGuardado);
        } catch (Exception e) {
            logger.error("=== REPOSITORIO CLIENTE: ERROR ===", e);
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
            logger.info("[CLIENTES][REPO] Ejecutando findAll en JPA");
            List<Cliente> list = jpaRepository.findAll().stream().map(this::toDomain).toList();
            logger.info("[CLIENTES][REPO] JPA retornó {} registros", list != null ? list.size() : 0);
            return list;
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
        DireccionEmbeddable direccionEmbeddable = null;
        if (cliente.getDireccion() != null) {
            // Convertir Direccion del dominio a DireccionEmbeddable
            // Por simplicidad, ponemos la dirección completa en calle
            direccionEmbeddable = new DireccionEmbeddable(
                cliente.getDireccion().toString(),
                null, // ciudad
                null, // pais
                null  // codigoPostal
            );
        }

        return new ClienteEntity(
                cliente.getId().value(),
                cliente.getNombreCompleto() != null ? cliente.getNombreCompleto().getNombre() : null,
                cliente.getNombreCompleto() != null ? cliente.getNombreCompleto().getApellido() : null,
                cliente.getEmail().toString(),
                cliente.getTipo(),
                cliente.getTelefono() != null ? cliente.getTelefono().toString() : null,
                direccionEmbeddable,
                cliente.getEstado(),
                cliente.getFechaNacimiento() != null ? cliente.getFechaNacimiento().getFecha() : null,
                false // Por defecto false para clientes nuevos
        );
    }

    private Cliente toDomain(ClienteEntity entity) {
        Direccion direccion = null;
        if (entity.getDireccion() != null) {
            // Convertir DireccionEmbeddable a Direccion del dominio
            DireccionEmbeddable embeddable = entity.getDireccion();
            if (embeddable.getCalle() != null || embeddable.getCiudad() != null || embeddable.getPais() != null) {
                direccion = Direccion.of(
                    embeddable.getCalle() != null ? embeddable.getCalle() : "Dirección no especificada",
                    embeddable.getCiudad() != null ? embeddable.getCiudad() : "Ciudad no especificada",
                    embeddable.getPais() != null ? embeddable.getPais() : "País no especificado",
                    embeddable.getCodigoPostal()
                );
            }
        }

        return Cliente.rehydrate(
                ClienteId.of(entity.getId()),
                entity.getNombre() != null && entity.getApellido() != null ?
                        NombreCompleto.of(entity.getNombre(), entity.getApellido()) : null,
                Email.of(entity.getEmail()),
                entity.getTipo(),
                entity.getTelefono() != null ? Telefono.of(entity.getTelefono()) : null,
                direccion,
                entity.getEstado(),
                entity.getFechaNacimiento(),
                entity.isEmailVerificado()
        );
    }
}