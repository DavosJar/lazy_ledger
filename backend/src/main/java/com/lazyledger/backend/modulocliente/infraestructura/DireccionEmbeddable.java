package com.lazyledger.backend.modulocliente.infraestructura;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class DireccionEmbeddable {

    @Column(name = "direccion_calle")
    private String calle;

    @Column(name = "direccion_ciudad")
    private String ciudad;

    @Column(name = "direccion_pais")
    private String pais;

    @Column(name = "direccion_codigo_postal")
    private String codigoPostal;

    // Constructor por defecto
    public DireccionEmbeddable() {}

    public DireccionEmbeddable(String calle, String ciudad, String pais, String codigoPostal) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.pais = pais;
        this.codigoPostal = codigoPostal;
    }

    // Getters y setters
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @Override
    public String toString() {
        if (calle == null && ciudad == null && pais == null) {
            return null;
        }
        return (calle != null ? calle : "") +
               (ciudad != null ? ", " + ciudad : "") +
               (pais != null ? ", " + pais : "") +
               (codigoPostal != null ? " " + codigoPostal : "");
    }
}