package com.lazyledger.backend.commons.especificaciones;

public class EspecificacionY<T> extends EspecificacionAbstracta<T> {
    private final Especificacion<T> izquierda;
    private final Especificacion<T> derecha;

    public EspecificacionY(Especificacion<T> izquierda, Especificacion<T> derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    @Override
    public boolean esSatisfechaPor(T candidato) {
        return izquierda.esSatisfechaPor(candidato) && derecha.esSatisfechaPor(candidato);
    }
}