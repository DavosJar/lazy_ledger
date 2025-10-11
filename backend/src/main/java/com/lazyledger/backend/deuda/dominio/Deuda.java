package com.lazyledger.backend.deuda.dominio;

import java.time.LocalDate;

import com.lazyledger.backend.commons.identificadores.DeudaId;

public class Deuda {
    private final DeudaId id;
    private final String nombre;
    private final Limite limite;
    private final Double montoTotal;
    private final String descripcion;
    private final FechasDeuda fechas;
    //recordar agregar el enum del estado de la deuda (PENDIENTE, PAGADA, VENCIDA)

    public Deuda(DeudaId id, String nombre, Limite limite, Double montoTotal, String descripcion, FechasDeuda fechas) {
        this.id = id;
        this.nombre = nombre;
        this.limite = limite;
        this.montoTotal = montoTotal;
        this.descripcion = descripcion;
        this.fechas = fechas;
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

    public Limite getLimite() {
        return limite;
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

    @Override
    public String toString() {
        return "Deuda{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", limite=" + limite +
                ", montoTotal=" + montoTotal +
                ", descripcion='" + descripcion + '\'' +
                ", fechas=" + fechas +
                '}';
    }

    public static void main(String[] args) {
        DeudaId deudaId = DeudaId.of(java.util.UUID.randomUUID());
        Limite limite = Limite.of("1000.0");

        LocalDate fechaManualCreacion = LocalDate.now().plusDays(20);
        LocalDate fechaManualVencimiento = LocalDate.now().plusDays(30);
        FechasDeuda fechasDeudaManual = FechasDeuda.of(fechaManualCreacion, fechaManualVencimiento);
        Deuda deuda = new Deuda(deudaId, "Deuda Ejemplo", limite, 500.0, "Descripción de la Deuda Ejemplo", fechasDeudaManual);
        System.out.println(deuda);
    }
}
