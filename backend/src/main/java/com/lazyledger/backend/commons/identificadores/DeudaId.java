package com.lazyledger.backend.commons.identificadores;

import java.util.UUID;

public record DeudaId(UUID id) {
    
    public static DeudaId of(UUID id){
        return new DeudaId(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
