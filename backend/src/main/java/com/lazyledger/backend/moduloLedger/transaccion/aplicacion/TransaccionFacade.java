package com.lazyledger.backend.moduloLedger.transaccion.aplicacion;

import com.lazyledger.backend.api.ApiResponse;
import com.lazyledger.backend.commons.enums.Categoria;
import com.lazyledger.backend.commons.enums.TipoTransaccion;
import com.lazyledger.backend.commons.exceptions.ApplicationException;
import com.lazyledger.backend.moduloLedger.transaccion.aplicacion.assembler.TransaccionAssembler;
import com.lazyledger.backend.moduloLedger.transaccion.dominio.repositorio.TransaccionRepository;
import com.lazyledger.backend.moduloLedger.transaccion.presentacion.dto.TransaccionDTO;
import com.lazyledger.backend.moduloLedger.transaccion.presentacion.dto.TransaccionSaveRequest;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import com.lazyledger.backend.api.PagedResponse;
import com.lazyledger.backend.api.PagedResponse.PaginationInfo;

@Service
public class TransaccionFacade {
    private final TransaccionUseCases transaccionUseCases;
    private final BuscarTransaccionesUseCase buscarTransaccionesUseCase;
    private final CrearTransaccionUseCase crearTransaccionUseCase;
    private final TransaccionAssembler transaccionAssembler;

    public TransaccionFacade(TransaccionRepository transaccionRepository, 
                            CrearTransaccionUseCase crearTransaccionUseCase,
                            TransaccionAssembler transaccionAssembler) {
        this.transaccionUseCases = new TransaccionUseCases(transaccionRepository);
        this.buscarTransaccionesUseCase = new BuscarTransaccionesUseCase(transaccionRepository);
        this.crearTransaccionUseCase = crearTransaccionUseCase;
        this.transaccionAssembler = transaccionAssembler;
    }

    public ApiResponse<TransaccionDTO> createTransaccion(TransaccionSaveRequest request) {
        try {
            // Parsear y validar datos
            UUID ledgerId = UUID.fromString(request.getLedgerId());
            UUID clienteId = UUID.fromString(request.getClienteId());
            TipoTransaccion tipo = TipoTransaccion.valueOf(request.getTipo().toUpperCase());
            Categoria categoria = Categoria.valueOf(request.getCategoria().toUpperCase());
            
            // Parsear fecha si viene, sino null para usar fecha actual
            LocalDateTime fecha = null;
            if (request.getFecha() != null && !request.getFecha().isBlank()) {
                fecha = LocalDateTime.parse(request.getFecha());
            }

            // Usar el UseCase que valida permisos y reglas de negocio
            var transaccion = crearTransaccionUseCase.ejecutar(
                ledgerId,
                clienteId,
                request.getMonto(),
                tipo,
                categoria,
                request.getDescripcion(),
                fecha
            );

            var dto = transaccionAssembler.toDTO(transaccion);
            return transaccionAssembler.assemble(dto);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("Datos inválidos en la solicitud de creación: " + e.getMessage(), e);
        }
    }

    public ApiResponse<TransaccionDTO> getTransaccionById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            var transaccion = transaccionUseCases.getTransaccionById(uuid);
            var dto = transaccionAssembler.toDTO(transaccion);
            return transaccionAssembler.assemble(dto);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("El ID proporcionado no es un UUID válido: " + id, e);
        }
    }

    public void deleteTransaccion(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            transaccionUseCases.deleteTransaccion(uuid);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("El ID proporcionado no es un UUID válido: " + id, e);
        }
    }

    public ApiResponse<TransaccionDTO> updateTransaccion(TransaccionDTO transaccionDTO) {
        try {
            var transaccion = transaccionAssembler.toDomain(transaccionDTO);
            var updatedTransaccion = transaccionUseCases.updateTransaccion(transaccion);
            var dto = transaccionAssembler.toDTO(updatedTransaccion);
            return transaccionAssembler.assemble(dto);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("Datos inválidos en la solicitud de actualización: " + e.getMessage(), e);
        }
    }

    /**
     * Busca transacciones aplicando filtros dinámicos con paginación.
     * 
     * El ledgerId es OBLIGATORIO ya que las transacciones siempre pertenecen a un ledger específico.
     * Los demás filtros son opcionales y se combinan con AND (deben cumplirse todos).
     * 
     * Ejemplos:
     * - ledgerId + sin filtros → Todas las transacciones del ledger
     * - ledgerId + categoria=COMIDA → Solo transacciones de comida del ledger
     * - ledgerId + categoria=COMIDA + fechas → Transacciones de comida en ese rango de fechas
     * 
     * @param ledgerId ID del ledger (OBLIGATORIO)
     * @param fechaDesde Fecha de inicio del rango (opcional)
     * @param fechaHasta Fecha de fin del rango (opcional)
     * @param tipo Tipo de transacción (opcional)
     * @param categoria Categoría de la transacción (opcional)
     * @param montoMinimo Monto mínimo (opcional)
     * @param montoMaximo Monto máximo (opcional)
     * @param page Número de página (0-based)
     * @param size Tamaño de página
     * @return PagedResponse con transacciones filtradas y paginadas
     * @throws ApplicationException si el ledgerId es inválido o null
     */
    public PagedResponse<TransaccionDTO> buscarTransacciones(String ledgerId, LocalDateTime fechaDesde,
                                                              LocalDateTime fechaHasta, TipoTransaccion tipo,
                                                              Categoria categoria, Double montoMinimo, 
                                                              Double montoMaximo, int page, int size) {
        try {
            if (ledgerId == null || ledgerId.trim().isEmpty()) {
                throw new ApplicationException("El ledgerId es obligatorio para buscar transacciones");
            }
            UUID ledgerUuid = UUID.fromString(ledgerId);
            var allTransacciones = buscarTransaccionesUseCase.buscar(
                ledgerUuid, fechaDesde, fechaHasta, tipo, categoria, montoMinimo, montoMaximo
            ).stream()
                    .map(transaccionAssembler::toDTO)
                    .collect(java.util.stream.Collectors.toList());
            
            long totalElements = allTransacciones.size();
            int totalPages = (int) Math.ceil((double) totalElements / size);
            int start = page * size;
            int end = Math.min(start + size, allTransacciones.size());
            var pageTransacciones = allTransacciones.subList(start, end);
            
            PaginationInfo paginationInfo = new PaginationInfo(
                page, size, totalElements, totalPages, page == 0, page >= totalPages - 1
            );
            return new PagedResponse<>(pageTransacciones, paginationInfo);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException("El ID del ledger proporcionado no es un UUID válido: " + ledgerId, e);
        }
    }
}