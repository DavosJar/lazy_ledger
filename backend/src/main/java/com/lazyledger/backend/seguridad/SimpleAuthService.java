package com.lazyledger.backend.seguridad;

import org.springframework.stereotype.Service;

@Service
public class SimpleAuthService {
    // Simula autenticación: solo acepta usuario "admin" y password "1234"
    public boolean authenticate(String username, String password) {
        return "admin".equals(username) && "1234".equals(password);
    }
}
