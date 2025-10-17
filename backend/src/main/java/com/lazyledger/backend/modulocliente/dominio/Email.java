package com.lazyledger.backend.modulocliente.dominio;

import com.lazyledger.backend.commons.exceptions.ValidationException;

public record Email(String email) {

    public Email {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("El email no puede ser nulo o vacío");
        }
        email = email.trim();
        // Validación más permisiva para emails existentes
        if (!isValidEmail(email)) {
            // Permitir emails que al menos tengan @ y un dominio básico
            if (!email.contains("@") || email.split("@").length != 2) {
                throw new ValidationException("El formato del email es inválido");
            }
            // Log de warning pero permitir
            System.out.println("Warning: Email con formato no estándar detectado: " + email);
        }
    }
    public static Email of(String email) {
        return new Email(email);
    }
    //validacion robusta de email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }
    
    @Override
    public String toString() {
        return email;
    }
}