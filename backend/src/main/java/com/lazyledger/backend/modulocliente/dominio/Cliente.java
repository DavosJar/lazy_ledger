package com.lazyledger.backend.modulocliente.dominio;

import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.commons.enums.TipoCliente;
import com.lazyledger.backend.commons.enums.EstadoCliente;
import com.lazyledger.backend.commons.exceptions.ValidationException;

import java.time.LocalDate;
import java.time.Period;

public class Cliente {
    private final ClienteId id;
    private final NombreCompleto nombreCompleto;
    private final Email email;
    private final TipoCliente tipo;
    private final Telefono telefono;
    private final Direccion direccion;
    private final EstadoCliente estado;
    private final LocalDate fechaNacimiento;
    private final boolean isEmailVerified;

    private Cliente(ClienteId id, NombreCompleto nombreCompleto, Email email, TipoCliente tipo,
                   Telefono telefono, Direccion direccion, EstadoCliente estado,
                   LocalDate fechaNacimiento, boolean isEmailVerified) {
        if (id == null) {
            throw new ValidationException("El ID del cliente no puede ser nulo");
        }
        if (nombreCompleto == null) {
            throw new ValidationException("El nombre completo del cliente no puede ser nulo");
        }
        if (email == null) {
            throw new ValidationException("El email del cliente no puede ser nulo");
        }
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.tipo = tipo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = estado != null ? estado : EstadoCliente.ACTIVO;
        this.fechaNacimiento = fechaNacimiento;
        this.isEmailVerified = isEmailVerified;
    }

    public static Cliente create(ClienteId id, NombreCompleto nombre, Email email, TipoCliente tipo,
                               Telefono telefono, Direccion direccion, LocalDate fechaNacimiento) {
        return new Cliente(id, nombre, email, tipo, telefono, direccion, EstadoCliente.ACTIVO, fechaNacimiento, false);
    }

    public static Cliente create(ClienteId id, NombreCompleto nombre, Email email, TipoCliente tipo, Telefono telefono) {
        return new Cliente(id, nombre, email, tipo, telefono, null, EstadoCliente.ACTIVO, null, false);
    }

    public static Cliente rehydrate(ClienteId id, NombreCompleto nombre, Email email, TipoCliente tipo,
                                  Telefono telefono, Direccion direccion, EstadoCliente estado,
                                  LocalDate fechaNacimiento, boolean isEmailVerified) {
        return new Cliente(id, nombre, email, tipo, telefono, direccion, estado, fechaNacimiento, isEmailVerified);
    }

    public Cliente verificarEmail() {
        return new Cliente(id, nombreCompleto, email, tipo, telefono, direccion, estado, fechaNacimiento, true);
    }

    public Cliente activar() {
        return new Cliente(id, nombreCompleto, email, tipo, telefono, direccion, EstadoCliente.ACTIVO, fechaNacimiento, isEmailVerified);
    }

    public Cliente desactivar() {
        return new Cliente(id, nombreCompleto, email, tipo, telefono, direccion, EstadoCliente.INACTIVO, fechaNacimiento, isEmailVerified);
    }

    public boolean esMayorDeEdad() {
        return fechaNacimiento != null &&
               Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 18;
    }

    public Cliente cambiarDireccion(Direccion nuevaDireccion) {
        return new Cliente(id, nombreCompleto, email, tipo, telefono, nuevaDireccion, estado, fechaNacimiento, isEmailVerified);
    }

    public Cliente cambiarTelefono(Telefono nuevoTelefono) {
        return new Cliente(id, nombreCompleto, email, tipo, nuevoTelefono, direccion, estado, fechaNacimiento, isEmailVerified);
    }

    // Getters
    public ClienteId getId() {
        return id;
    }

    public NombreCompleto getNombreCompleto() {
        return nombreCompleto;
    }

    public Email getEmail() {
        return email;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public Telefono getTelefono() {
        return telefono;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public EstadoCliente getEstado() {
        return estado;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }
}