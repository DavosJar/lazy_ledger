package com.lazyledger.backend.moduloLedger.ledger.dominio;

import com.lazyledger.backend.commons.identificadores.LedgerId;
import com.lazyledger.backend.commons.enums.EstadoLedger;
import java.util.Objects;

public class Ledger {
    private final LedgerId id;
    private final String nombre;
    private final String descripcion;
    private final EstadoLedger estado;

    private Ledger(LedgerId id, String nombre, String descripcion, EstadoLedger estado) {

        this.id = Objects.requireNonNull(id, "id no puede ser nulo");
        this.nombre = Objects.requireNonNull(nombre, "nombre no puede ser nulo");
        this.descripcion = Objects.requireNonNull(descripcion, "descripcion no puede ser nula");
        this.estado = Objects.requireNonNull(estado, "estado no puede ser nulo");
    }

    public static Ledger create(LedgerId id, String nombre, String descripcion, EstadoLedger estado) {
        return new Ledger(id, nombre, descripcion, estado);
    }

    public static Ledger rehydrate(LedgerId id, String nombre, String descripcion, EstadoLedger estado) {
        return new Ledger(id, nombre, descripcion, estado);
    }

    public Ledger actualizarDetalles(String nuevoNombre, String nuevaDescripcion) {
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
               nuevoNombre = this.nombre;
        }
        if (nuevaDescripcion == null || nuevaDescripcion.isBlank()) {
            nuevaDescripcion = this.descripcion;
        }
        return new Ledger(this.id, nuevoNombre, nuevaDescripcion, this.estado);
    }

    public Ledger archivar() {
        if (this.estado != EstadoLedger.ACTIVO) {
            throw new IllegalStateException("Solo se pueden archivar ledgers activos");
        }
        return new Ledger(this.id, this.nombre, this.descripcion, EstadoLedger.ARCHIVADO);
    }

    public Ledger activar() {
        if (this.estado != EstadoLedger.INACTIVO) {
            throw new IllegalStateException("Solo se pueden activar ledgers inactivos");
        }
        return new Ledger(this.id, this.nombre, this.descripcion, EstadoLedger.ACTIVO);
    }

    public Ledger inactivar() {
        if (this.estado != EstadoLedger.ACTIVO) {
            throw new IllegalStateException("Solo se pueden inactivar ledgers activos");
        }
        return new Ledger(this.id, this.nombre, this.descripcion, EstadoLedger.INACTIVO);
    }

    public Ledger desarchivar() {
        if (this.estado != EstadoLedger.ARCHIVADO) {
            throw new IllegalStateException("Solo se pueden desarchivar ledgers archivados");
        }
        return new Ledger(this.id, this.nombre, this.descripcion, EstadoLedger.ACTIVO);
    }

    public LedgerId getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }   
    public EstadoLedger getEstado() {
        return estado;
    }
}
