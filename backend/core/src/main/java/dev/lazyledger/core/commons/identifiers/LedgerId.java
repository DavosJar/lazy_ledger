package dev.lazyledger.core.commons.identifiers;

import java.util.UUID;


public record IdLedger(UUID value) {
    public static IdLedger fromString(String id) {
        return new IdLedger(UUID.fromString(id));
    }

}
