package com.lazyledger.backend.cliente.dominio;

import com.lazyledger.backend.commons.exceptions.ValidationException;

public record Email(String email) {

    public Email {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("El email no puede ser nulo o vacío");
        }
        email = email.trim();
        if (!isValidEmail(email)) {
            throw new ValidationException("El formato del email es inválido");
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