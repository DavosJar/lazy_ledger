package com.lazyledger.backend.transaccion.dominio;

import java.math.BigDecimal;
import com.lazyledger.backend.commons.identificadores.TransaccionId;
//import com.lazyledger.backend.commons.identificadores.LedgerId;

public class Transaccion {

    private final TransaccionId id;
    //private final LedgerId ledgerId;
    private final BigDecimal monto;

    private Transaccion(TransaccionId id, BigDecimal monto) {
        this.id = id;
        this.monto = monto;
    }

    public static Transaccion create(TransaccionId id, BigDecimal monto) {
        return new Transaccion(id, monto);
    }

    public static Transaccion rehydrate(TransaccionId id, BigDecimal monto) {
        return new Transaccion(id, monto);
    }

    public TransaccionId getId() {
        return id;
    }
    public BigDecimal getMonto() {
        return monto;
    }
    

    
}
