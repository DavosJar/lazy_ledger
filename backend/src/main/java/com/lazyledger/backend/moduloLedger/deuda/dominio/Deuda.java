package com.lazyledger.backend.moduloLedger.deuda.dominio;

import java.time.LocalDate;

import com.lazyledger.backend.commons.enums.EstadoDeuda;
import com.lazyledger.backend.commons.exceptions.ValidationException;
import com.lazyledger.backend.commons.identificadores.DeudaId;

public class Deuda {
    private final DeudaId id;
    private final String nombre;
    private final Double montoTotal;
    private final String descripcion;
    private final FechasDeuda fechas;
    private final EstadoDeuda estado;

    private Deuda(DeudaId id, String nombre, Double montoTotal, String descripcion, FechasDeuda fechas, EstadoDeuda estado) {
        if (id == null) {
            throw new ValidationException("El ID de la deuda no puede ser nulo");
        }
        if (descripcion == null) {
            throw new ValidationException("La descripción de la deuda no puede ser nula");
        }
        if (montoTotal == null || montoTotal <= 0) {
            throw new ValidationException("El monto total de la deuda debe ser mayor que cero");
        }
        this.id = id;
        this.nombre = nombre;
        this.montoTotal = montoTotal;
        this.descripcion = descripcion;
        this.fechas = fechas;
        this.estado = estado;
    }

    public static Deuda create(DeudaId id, String nombre, Double montoTotal, String descripcion, FechasDeuda fechas, EstadoDeuda estado) {
        return new Deuda(id, nombre, montoTotal, descripcion, fechas, estado);
    }

    public static Deuda rehydrate(DeudaId id, String nombre, Double montoTotal, String descripcion, FechasDeuda fechas, EstadoDeuda estado) {
        return new Deuda(id, nombre, montoTotal, descripcion, fechas, estado);
    }

    public DeudaId getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaCreacion() {
        return fechas.fechaCreacion();
    }

    public LocalDate getFechaVencimiento() {
        return fechas.fechaVencimiento();
    }

    public EstadoDeuda getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Deuda{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", montoTotal=" + montoTotal +
                ", descripcion='" + descripcion + '\'' +
                ", fechas=" + fechas +
                ", estado=" + estado +
                '}';
    }

    public static void main(String[] args) {
        DeudaId deudaId = DeudaId.of(java.util.UUID.randomUUID());

        LocalDate fechaManualCreacion = LocalDate.now().plusDays(20);
        LocalDate fechaManualVencimiento = LocalDate.now().plusDays(30);
        FechasDeuda fechasDeudaManual = FechasDeuda.of(fechaManualCreacion, fechaManualVencimiento);
        Deuda deuda = new Deuda(deudaId, "Deuda Ejemplo", 500.0, "Descripción de la Deuda Ejemplo", fechasDeudaManual, EstadoDeuda.VENCIDA);
        System.out.println(deuda);
    }
}
