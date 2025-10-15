package com.lazyledger.backend.commons.identificadores;

import java.util.UUID;

public record ObjetivoId(UUID value) {
    public static ObjetivoId of(UUID id) {
        return new ObjetivoId(id);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
