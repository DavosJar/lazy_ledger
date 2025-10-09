package dev.lazyledger.core.commons.identifiers;

import java.util.UUID;


public record LedgerId(UUID value) {
    public static LedgerId fromString(String id) {
        return new LedgerId(UUID.fromString(id));
    }

}
