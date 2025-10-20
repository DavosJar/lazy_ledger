package com.lazyledger.backend.seguridad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAuditService {
    private static final Logger log = LoggerFactory.getLogger(LoginAuditService.class);
    private final ConcurrentHashMap<String, Integer> failedAttempts = new ConcurrentHashMap<>();

    public void auditFail(String username, String ip, String userAgent) {
        int count = failedAttempts.merge(username, 1, Integer::sum);
        log.warn("LOGIN FAIL | user: {} | ip: {} | agent: {} | time: {} | failCount: {}", username, ip, userAgent, LocalDateTime.now(), count);
    }

    public void reset(String username) {
        failedAttempts.remove(username);
    }

    public int getFails(String username) {
        return failedAttempts.getOrDefault(username, 0);
    }
}
