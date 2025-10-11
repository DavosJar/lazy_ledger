package com.lazyledger.backend.cliente.dominio;

public record Email(String email) {
    public static Email of(String email) {
        // Aquí podrías agregar validaciones adicionales para el formato del email
        return new Email(email);
    }
    //validacion completa de formato email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    @Override
    public String toString() {
        return email;
    }
}