package com.lazyledger.backend.moduloLedger.deuda.dominio;

import java.time.LocalDate;
import java.util.Objects;

import com.lazyledger.backend.commons.exceptions.ValidationException;

public record FechasDeuda(LocalDate fechaCreacion, LocalDate fechaVencimiento) {
    //constructor canonico para validaciones
    public FechasDeuda{
        // validar que las fechas no sean nulas
        Objects.requireNonNull(fechaCreacion, "La fecha de creación no puede ser nula");
        Objects.requireNonNull(fechaVencimiento, "La fecha de vencimiento no puede ser nula");
        //validar que la fecha de creacion no sea una fecha pasada
        if(fechaCreacion.isBefore(LocalDate.now())){
            throw new ValidationException("La fecha de creación no puede ser una fecha pasada");
        }
        //validar que la fecha de creación no sea posterior a la fecha de vencimiento
        if(fechaCreacion.isAfter(fechaVencimiento)){
            throw new ValidationException("La fecha de creación no puede ser posterior a la fecha de vencimiento");
        }
        //validar que la fecha de creacion no sea igual a la fecha de vencimiento
        if(fechaCreacion.isEqual(fechaVencimiento)){
            throw new ValidationException("La fecha de creación no puede ser igual a la fecha de vencimiento");
        }
        //validar que la fecha de vencimiento no sea una fecha pasada.
        if(fechaVencimiento.isBefore(LocalDate.now())){
            throw new ValidationException("La fecha de vencimiento no puede ser una fecha pasada");
        }
            }

    //metodo de fabrica para crear una instancia de FechasDeuda con la fecha de creacion como la fecha actual
    public static FechasDeuda nueva(LocalDate fechaVencimiento){
        //llama al constructor canonico que tiene las validaciones
        return new FechasDeuda(LocalDate.now(), fechaVencimiento);
    }

    //metodo de fabrica para crear una instancia de FechasDeuda con la fecha de creacion y fecha de vencimiento especificadas
    public static FechasDeuda of(LocalDate fechaCreacion, LocalDate fechaVencimiento){
        //llama al constructor canonico que tiene las validaciones
        return new FechasDeuda(fechaCreacion, fechaVencimiento);
    }

    //getters sin formatar devuelven los localDate
    public LocalDate fechaCreacion(){
        return fechaCreacion;
    }

    public LocalDate fechaVencimiento(){
        return fechaVencimiento;
    }

    //toString
    @Override
    public String toString() {
        return "FechasDeuda{" +
                "fechaCreacion=" + fechaCreacion +
                ", fechaVencimiento=" + fechaVencimiento +
                '}';
    }
}
