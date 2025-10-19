package com.lazyledger.backend.commons.especificaciones;

public interface Especificacion<T> {
    boolean esSatisfechaPor(T candidato);

    default Especificacion<T> y(Especificacion<T> otra) {
        return new EspecificacionY<>(this, otra);
    }

    default Especificacion<T> o(Especificacion<T> otra) {
        return new EspecificacionO<>(this, otra);
    }

    default Especificacion<T> no() {
        return new EspecificacionNo<>(this);
    }
}