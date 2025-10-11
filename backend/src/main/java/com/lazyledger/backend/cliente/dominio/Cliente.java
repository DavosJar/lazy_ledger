package com.lazyledger.backend.cliente.dominio;


import com.lazyledger.backend.commons.identificadores.ClienteId;
import com.lazyledger.backend.cliente.dominio.Email;

public class Cliente {
    private final ClienteId id;
    private final String nombre;
    private final Email email;

    private Cliente(ClienteId id, String nombre, Email email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }
    public static Cliente create(ClienteId id, String nombre, Email email) {
        return new Cliente(id, nombre, email);
    }

    public static Cliente rehydrate(ClienteId id, String nombre, Email email) {
        return new Cliente(id, nombre, email);
    }

    public ClienteId getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Email getEmail() {
        return email;
    }

    public static void main(String[] args) {
        ClienteId clienteId = ClienteId.of(java.util.UUID.randomUUID());
        Email email = Email.of("ejemplo@dominio.com");
        Cliente cliente = Cliente.create(clienteId, "Nombre Ejemplo", email);
        System.out.println("Cliente ID: " + cliente.getId());
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("Email: " + cliente.getEmail());
    }
}