package com.lazyledger.backend.commons;

import java.util.UUID;

/**
 * Generador de IDs únicos secuenciales usando UUID v7.
 * UUID v7 incluye timestamp para orden temporal.
 */
public interface IdGenerator {
    UUID nextId();
}