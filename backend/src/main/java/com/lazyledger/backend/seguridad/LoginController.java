package com.lazyledger.backend.seguridad;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private LoginAuditService auditService;
    @Autowired
    private SimpleAuthService authService;

    @PostMapping
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        String ip = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        if (authService.authenticate(username, password)) {
            auditService.reset(username);
            return ResponseEntity.ok("Login OK");
        } else {
            auditService.auditFail(username, ip, userAgent);
            int fails = auditService.getFails(username);
            return ResponseEntity.status(401).body("Login FAIL. Intentos fallidos: " + fails);
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        if (xf != null && !xf.isEmpty()) {
            return xf.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}
