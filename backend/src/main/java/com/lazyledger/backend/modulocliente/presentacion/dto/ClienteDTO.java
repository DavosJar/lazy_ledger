package com.lazyledger.backend.modulocliente.presentacion.dto;


public class ClienteDTO {
    // Atributos del DTO
    private String id;
    private String nombre;
    private String apellido;
    private String email;
    private String tipo;
    private String telefono;



    
    public ClienteDTO() {
    }

    public ClienteDTO(String id, String nombre, String apellido, String email, String tipo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.tipo = tipo;
        this.telefono = telefono;
    }
    // Getters y Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
