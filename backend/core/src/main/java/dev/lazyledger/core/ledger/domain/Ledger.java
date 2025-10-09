package dev.lazyledger.core.ledger.domain;

import dev.lazyledger.core.commons.identifiers.LedgerId;

public class Ledger {
    private final LedgerId ledger;
    private String name;
    private String description;

    private Ledger(LedgerId ledger, String name, String description) {
        this.ledger = ledger;
        this.name = name;
        this.description = description;
    }

    public static Ledger create(LedgerId ledgerId, String name, String description) {
        return new Ledger(ledgerId, name, description);
    }
    public static Ledger rehydrate(LedgerId ledgerId, String name, String description) {
        return new Ledger(ledgerId, name, description);
    }

    public LedgerId getLedger() {
        return ledger;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

}
