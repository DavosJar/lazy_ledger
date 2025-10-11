package com.lazyledger.backend.commons.identificadores;

import java.util.UUID;

public record ClienteId(UUID id) {

    public static ClienteId of(UUID id) {
        return new ClienteId(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
