package com.lazyledger.backend.moduloLedger.ledger.aplicacion;

import com.lazyledger.backend.commons.IdGenerator;
import com.lazyledger.backend.commons.enums.EstadoLedger;
import com.lazyledger.backend.commons.exceptions.ApplicationException;
import com.lazyledger.backend.commons.exceptions.DuplicateException;
import com.lazyledger.backend.commons.exceptions.InfrastructureException;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.moduloLedger.ledger.dominio.Ledger;
import com.lazyledger.backend.moduloLedger.ledger.dominio.repositorio.LedgerRepository;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.MiembroLedger;
import com.lazyledger.backend.moduloLedger.miembroLedger.dominio.repositorio.MiembroLedgerRepository;
import com.lazyledger.backend.modulocliente.dominio.repositorio.ClienteRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Caso de uso para crear un nuevo Ledger.
 * 
 * Responsabilidades:
 * 1. Validar que el cliente existe
 * 2. Crear el ledger en estado ACTIVO
 * 3. Asignar automáticamente al cliente creador como PROPIETARIO
 * 
 * Reglas de negocio:
 * - El ledger se crea en estado ACTIVO por defecto
 * - El cliente que crea el ledger es automáticamente PROPIETARIO
 * - El propietario inicial está ACTIVO desde el inicio
 */
public class CrearLedgerUseCase {

    private final LedgerRepository ledgerRepository;
    private final MiembroLedgerRepository miembroLedgerRepository;
    private final ClienteRepository clienteRepository;
    private final IdGenerator idGenerator;

    public CrearLedgerUseCase(LedgerRepository ledgerRepository,
                             MiembroLedgerRepository miembroLedgerRepository,
                             ClienteRepository clienteRepository,
                             IdGenerator idGenerator) {
        this.ledgerRepository = ledgerRepository;
        this.miembroLedgerRepository = miembroLedgerRepository;
        this.clienteRepository = clienteRepository;
        this.idGenerator = idGenerator;
    }

    /**
     * Crea un nuevo ledger y asigna al cliente como propietario.
     * 
     * @param nombre Nombre del ledger
     * @param descripcion Descripción del ledger (puede ser null o vacío)
     * @param clienteId ID del cliente que crea el ledger (será el propietario)
     * @return El ledger creado
     * @throws ApplicationException si el cliente no existe o hay errores de validación
     */
    @Transactional
    public Ledger ejecutar(String nombre, String descripcion, ClienteId clienteId) {
        // 1. Validar que el cliente existe
        if (!clienteRepository.existsById(clienteId.value())) {
            throw new ApplicationException("El cliente no existe: " + clienteId.value());
        }

        // 1.1 Validar que no exista un ledger con el mismo nombre
        if (ledgerRepository.existsByNombre(nombre)) {
            throw new DuplicateException("Ya existe un ledger con el nombre: " + nombre);
        }

        try {
            // 2. Crear el ledger
            LedgerId ledgerId = LedgerId.of(idGenerator.nextId());
            String descripcionFinal = (descripcion == null || descripcion.isBlank()) ? "" : descripcion;
            Ledger ledger = Ledger.create(ledgerId, nombre, descripcionFinal, EstadoLedger.ACTIVO);
            
            Ledger ledgerCreado = ledgerRepository.save(ledger);

            // 3. Asignar al cliente como PROPIETARIO del ledger
            // Verificación defensiva (aunque el ledger es nuevo, nunca está de más)
            if (miembroLedgerRepository.existsByClienteIdAndLedgerId(clienteId.value(), ledgerId.value())) {
                throw new DuplicateException("El cliente ya es miembro del ledger");
            }

            MiembroLedger propietario = MiembroLedger.createPropietario(
                clienteId,
                ledgerId
            );
            
            miembroLedgerRepository.save(propietario);

            return ledgerCreado;
            
        } catch (Exception e) {
            throw new InfrastructureException("Error al crear el ledger y asignar propietario", e);
        }
    }
}
