package com.lazyledger.backend.commons.identificadores;
import java.util.UUID;

public record TransaccionId(UUID id) {
    public static TransaccionId of(UUID id){
        return new TransaccionId(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
