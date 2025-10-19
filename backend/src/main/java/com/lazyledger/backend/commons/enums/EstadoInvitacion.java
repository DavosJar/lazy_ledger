package com.lazyledger.backend.commons.enums;

/**
 * Estados posibles de una invitación a un ledger.
 */
public enum EstadoInvitacion {
    /**
     * Invitación enviada, esperando respuesta del invitado.
     */
    PENDIENTE,
    
    /**
     * Invitación aceptada por el invitado.
     * Se crea el MiembroLedger correspondiente.
     */
    ACEPTADA,
    
    /**
     * Invitación rechazada por el invitado.
     * Puede ser reenviada.
     * Se elimina automáticamente después de 7 días.
     */
    RECHAZADA
}
