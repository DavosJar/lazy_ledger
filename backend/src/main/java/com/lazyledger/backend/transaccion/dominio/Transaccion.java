package com.lazyledger.backend.transaccion.dominio;

import com.lazyledger.backend.commons.identificadores.TransaccionId;
import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.commons.enums.TipoTransaccion;
import com.lazyledger.backend.commons.enums.Categoria;

public class Transaccion {

    private final TransaccionId id;
    private final LedgerId ledgerId;
    private final Monto monto;
    private final TipoTransaccion tipo;
    private final Categoria categoria;
    private final Descripcion descripcion;
    private final Fecha fecha;

    private Transaccion(TransaccionId id, LedgerId ledgerId, Monto monto,
                        TipoTransaccion tipo, Categoria categoria, Descripcion descripcion,
                        Fecha fecha) {
        if (id == null) {
            throw new ValidationException("El ID de la transacción no puede ser nulo");
        }
        if (ledgerId == null) {
            throw new ValidationException("El ID del ledger no puede ser nulo");
        }
        if (tipo == null) {
            throw new ValidationException("El tipo de transacción no puede ser nulo");
        }
        if (categoria == null) {
            throw new ValidationException("La categoría no puede ser nula");
        }

        this.id = id;
        this.ledgerId = ledgerId;
        this.monto = monto;
        this.tipo = tipo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public static Transaccion create(TransaccionId id, LedgerId ledgerId, Monto monto,
                                     TipoTransaccion tipo, Categoria categoria, Descripcion descripcion,
                                     Fecha fecha) {
        return new Transaccion(id, ledgerId, monto, tipo, categoria, descripcion, fecha);
    }

    public static Transaccion rehydrate(TransaccionId id, LedgerId ledgerId, Monto monto,
                                        TipoTransaccion tipo, Categoria categoria, Descripcion descripcion,
                                        Fecha fecha) {
        return new Transaccion(id, ledgerId, monto, tipo, categoria, descripcion, fecha);
    }

    public TransaccionId getId() {
        return id;
    }

    public LedgerId getLedgerId() {
        return ledgerId;
    }

    public Monto getMonto() {
        return monto;
    }

    public TipoTransaccion getTipo() {
        return tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Descripcion getDescripcion() {
        return descripcion;
    }

    public Fecha getFecha() {
        return fecha;
    }
}