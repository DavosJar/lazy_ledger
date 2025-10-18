package com.lazyledger.backend.moduloLedger.miembroLedger.dominio;

import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.commons.enums.MiembroRol;
import com.lazyledger.backend.commons.identificadores.ClienteId;
import java.util.Objects;
import com.lazyledger.backend.commons.exceptions.InvalidStateException;

public class MiembroLedger {
    private final ClienteId clienteId;
    private final MiembroRol rol;
    private final LedgerId ledgerId;
    private final boolean activo;

    private MiembroLedger(ClienteId clienteId, MiembroRol rol, LedgerId ledgerId, boolean activo) {
        this.clienteId = Objects.requireNonNull(clienteId, "clienteId no puede ser nulo");
        this.rol = Objects.requireNonNull(rol, "rol no puede ser nulo");
        this.ledgerId = Objects.requireNonNull(ledgerId, "ledgerId no puede ser nulo");
        this.activo = activo;
    }

    public static MiembroLedger create(ClienteId clienteId, LedgerId ledgerId, MiembroRol rol) {
        if (rol == MiembroRol.PROPIETARIO) {
            throw new InvalidStateException("Use createPropietario() para crear un propietario");
        }
        return new MiembroLedger(clienteId, rol, ledgerId, true);
    }

    public static MiembroLedger createPropietario(ClienteId clienteId, LedgerId ledgerId) {
        return new MiembroLedger(clienteId, MiembroRol.PROPIETARIO, ledgerId, true);
    }

    public static MiembroLedger rehydrate(ClienteId clienteId, MiembroRol rol, LedgerId ledgerId, boolean activo) {
        if (rol == MiembroRol.PROPIETARIO && !activo) {
            throw new InvalidStateException("Un propietario no puede estar inactivo");
        }
        return new MiembroLedger(clienteId, rol, ledgerId, activo);
    }

    public MiembroLedger cambiarRol(MiembroRol nuevoRol) {
        if (this.rol == MiembroRol.PROPIETARIO) {
            throw new InvalidStateException("No se puede cambiar el rol de un propietario");
        }
        
        if (nuevoRol == MiembroRol.PROPIETARIO) {
            throw new InvalidStateException("No se puede asignar el rol de propietario mediante cambio de rol");
        }
        
        return new MiembroLedger(this.clienteId, nuevoRol, this.ledgerId, this.activo);
    }

    public MiembroLedger desactivar() {
        if (this.rol == MiembroRol.PROPIETARIO) {
            throw new InvalidStateException("No se puede desactivar al propietario");
        }
        if (!this.activo) {
            throw new InvalidStateException("El miembro ya está inactivo");
        }
        return new MiembroLedger(this.clienteId, this.rol, this.ledgerId, false);
    }

    public MiembroLedger activar() {
        if (this.rol == MiembroRol.PROPIETARIO) {
            throw new InvalidStateException("El propietario siempre está activo");
        }
        if (this.activo) {
            throw new InvalidStateException("El miembro ya está activo");
        }
        return new MiembroLedger(this.clienteId, this.rol, this.ledgerId, true);
    }
    public boolean puedeGestionarLedger() {
        return this.rol == MiembroRol.PROPIETARIO;
    }
    public boolean puedeGestionarMiembros() {
        return this.rol == MiembroRol.PROPIETARIO;
    }
    public boolean puedeGestionarElementos() {
        return this.rol == MiembroRol.PROPIETARIO || this.rol == MiembroRol.ASISTENTE;
    }

    public boolean puedeVerElementos() {
        return this.rol == MiembroRol.PROPIETARIO || this.rol == MiembroRol.ASISTENTE || this.rol == MiembroRol.ANALISTA;
    }

    // Getters
    public ClienteId getClienteId() {
        return clienteId;
    }

    public MiembroRol getRol() {
        return rol;
    }

    public LedgerId getLedgerId() {
        return ledgerId;
    }

    public boolean isActivo() {
        return activo;
    }

    public boolean esPropietario() {
        return this.rol == MiembroRol.PROPIETARIO;
    }

    public boolean perteneceAlLedger(LedgerId ledgerId) {
        return Objects.equals(this.ledgerId, ledgerId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MiembroLedger)) return false;
        MiembroLedger that = (MiembroLedger) o;
        return Objects.equals(clienteId, that.clienteId) && 
               Objects.equals(ledgerId, that.ledgerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clienteId, ledgerId);
    }

}