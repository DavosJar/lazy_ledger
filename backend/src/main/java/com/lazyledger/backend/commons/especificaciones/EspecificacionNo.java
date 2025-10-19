package com.lazyledger.backend.commons.especificaciones;

public class EspecificacionNo<T> extends EspecificacionAbstracta<T> {
    private final Especificacion<T> original;

    public EspecificacionNo(Especificacion<T> original) {
        this.original = original;
    }

    @Override
    public boolean esSatisfechaPor(T candidato) {
        return !original.esSatisfechaPor(candidato);
    }
}