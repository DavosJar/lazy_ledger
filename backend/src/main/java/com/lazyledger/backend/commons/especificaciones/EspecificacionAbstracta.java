package com.lazyledger.backend.commons.especificaciones;

public abstract class EspecificacionAbstracta<T> implements Especificacion<T> {

    @Override
    public abstract boolean esSatisfechaPor(T candidato);

    @Override
    public Especificacion<T> y(Especificacion<T> otra) {
        return new EspecificacionY<>(this, otra);
    }

    @Override
    public Especificacion<T> o(Especificacion<T> otra) {
        return new EspecificacionO<>(this, otra);
    }

    @Override
    public Especificacion<T> no() {
        return new EspecificacionNo<>(this);
    }
}