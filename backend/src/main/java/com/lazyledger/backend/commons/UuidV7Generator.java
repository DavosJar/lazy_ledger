package com.lazyledger.backend.commons;

import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementación de IdGenerator que genera UUID v7 secuenciales usando uuid-creator.
 */
@Service
public class UuidV7Generator implements IdGenerator {

    @Override
    public UUID nextId() {
        return UuidCreator.getTimeOrderedEpoch();
    }
}