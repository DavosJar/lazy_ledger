package com.lazyledger.backend.modulocliente.infraestructura;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
import com.lazyledger.backend.commons.enums.TipoCliente;
import com.lazyledger.backend.commons.enums.EstadoCliente;

@Entity
@Table(name = "clientes")
public class ClienteEntity {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "nombre", nullable = true)
    private String nombre;

    @Column(name = "apellido", nullable = true)
    private String apellido;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoCliente tipo;

    @Column(name = "telefono")
    private String telefono;

    @Embedded
    private DireccionEmbeddable direccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCliente estado;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "email_verificado", nullable = false)
    private boolean emailVerificado = false;

    // Constructors
    public ClienteEntity() {}

    public ClienteEntity(UUID id, String nombre, String apellido, String email, TipoCliente tipo, String telefono, DireccionEmbeddable direccion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.tipo = tipo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = EstadoCliente.ACTIVO;
        this.emailVerificado = false;
    }

    public ClienteEntity(UUID id, String nombre, String apellido, String email, TipoCliente tipo, String telefono,
                        DireccionEmbeddable direccion, EstadoCliente estado, LocalDate fechaNacimiento, boolean emailVerificado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.tipo = tipo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = estado;
        this.fechaNacimiento = fechaNacimiento;
        this.emailVerificado = emailVerificado;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public DireccionEmbeddable getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionEmbeddable direccion) {
        this.direccion = direccion;
    }

    public EstadoCliente getEstado() {
        return estado;
    }

    public void setEstado(EstadoCliente estado) {
        this.estado = estado;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isEmailVerificado() {
        return emailVerificado;
    }

    public void setEmailVerificado(boolean emailVerificado) {
        this.emailVerificado = emailVerificado;
    }
}