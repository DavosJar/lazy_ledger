package com.lazyledger.backend.commons.enums;

public enum MiembroRol {
    PROPIETARIO("Propietario"),
    ASISTENTE("Asistente"),
    ANALISTA("Analista");

    // Metodos y atributos adicionales si es necesario

    public final String displayName;
    MiembroRol(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
