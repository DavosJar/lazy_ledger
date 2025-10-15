package com.lazyledger.backend.commons.identificadores;

import java.util.UUID;

public record LedgerId(UUID value) {

    public static LedgerId of(UUID id) {
        return new LedgerId(id);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}