package com.lazyledger.backend.cliente.dominio;

import com.lazyledger.backend.commons.identificadores.ClienteId;

import java.util.Objects;

import com.lazyledger.backend.commons.enums.TipoCliente;
import com.lazyledger.backend.commons.exceptions.ValidationException;



public class Cliente {
    private final ClienteId id;
    private final NombreCompleto nombreCompleto;
    private final Email email;
    private final TipoCliente tipo;
    private final Telefono telefono;

    private Cliente(ClienteId id, NombreCompleto nombreCompleto, Email email, TipoCliente tipo, Telefono telefono) {
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
    }

    public static Cliente create(ClienteId id, NombreCompleto nombre, Email email, TipoCliente tipo, Telefono telefono) {
        return new Cliente(id, nombre, email, tipo, telefono);
    }

    public static Cliente rehydrate(ClienteId id, NombreCompleto nombre, Email email, TipoCliente tipo, Telefono telefono) {
        return new Cliente(id, nombre, email, tipo, telefono);
    }
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
}