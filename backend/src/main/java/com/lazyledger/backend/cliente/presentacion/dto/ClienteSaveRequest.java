package com.lazyledger.backend.cliente.presentacion.dto;

public class ClienteSaveRequest {
    private String nombre;
    private String apellido;
    private String email;
    private String tipo;
    private String telefono;

    // Constructors
    public ClienteSaveRequest() {}

    public ClienteSaveRequest(String nombre, String apellido, String email, String tipo, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.tipo = tipo;
        this.telefono = telefono;
    }

    // Getters and Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}