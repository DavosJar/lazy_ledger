package com.lazyledger.backend.moduloLedger.deuda.dominio;

public record Limite(String limite) {
    public static Limite of(String limite) {
        // Aquí podrías agregar validaciones adicionales para el formato del límite
        return new Limite(limite);
    }

    private boolean isValidLimite(String limite) {
        // validacion formato (DD/MM/YYYY)
        String limiteRegex = "^\\d{2}/\\d{2}/\\d{4}$";
        return limite != null && limite.matches(limiteRegex);
    }

    @Override
    public String toString() {
        return limite;
    }
    
}
