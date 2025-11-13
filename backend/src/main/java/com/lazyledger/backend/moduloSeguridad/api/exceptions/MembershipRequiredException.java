package com.lazyledger.backend.moduloSeguridad.api.exceptions;

public class MembershipRequiredException extends SecurityException {
    public MembershipRequiredException(String message) {
        super(message);
    }
}