package com.lazyledger.backend.commons.especificaciones;

public class EspecificacionO<T> extends EspecificacionAbstracta<T> {
    private final Especificacion<T> izquierda;
    private final Especificacion<T> derecha;

    public EspecificacionO(Especificacion<T> izquierda, Especificacion<T> derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    @Override
    public boolean esSatisfechaPor(T candidato) {
        return izquierda.esSatisfechaPor(candidato) || derecha.esSatisfechaPor(candidato);
    }
}