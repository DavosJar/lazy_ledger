package com.lazyledger.backend.modulocliente.dominio;

import com.lazyledger.backend.commons.exceptions.ValidationException;

import java.time.LocalDate;
import java.time.Period;

public class FechaNacimiento {
    private final LocalDate fecha;

    private FechaNacimiento(LocalDate fecha) {
        this.fecha = fecha;
    }

    public static FechaNacimiento of(LocalDate fecha) {
        if (fecha == null) {
            return null; // Permitir null para clientes mínimos
        }

        // Validar que la fecha no sea anterior a 1900
        LocalDate fechaMinima = LocalDate.of(1900, 1, 1);
        if (fecha.isBefore(fechaMinima)) {
            throw new ValidationException("La fecha de nacimiento no puede ser anterior a 1900");
        }

        // Validar que la persona tenga al menos 18 años
        LocalDate fechaActual = LocalDate.now();
        Period edad = Period.between(fecha, fechaActual);
        if (edad.getYears() < 18) {
            throw new ValidationException("El cliente debe tener al menos 18 años");
        }

        return new FechaNacimiento(fecha);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return fecha != null ? fecha.toString() : null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FechaNacimiento that = (FechaNacimiento) obj;
        return fecha != null ? fecha.equals(that.fecha) : that.fecha == null;
    }

    @Override
    public int hashCode() {
        return fecha != null ? fecha.hashCode() : 0;
    }
}