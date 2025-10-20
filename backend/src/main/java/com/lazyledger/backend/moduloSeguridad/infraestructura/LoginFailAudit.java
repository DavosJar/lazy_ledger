package com.lazyledger.backend.moduloSeguridad.infraestructura;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "login_fail_audit")
public class LoginFailAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String ip;
    private String userAgent;
    private LocalDateTime timestamp;
    private int failCount;

    public LoginFailAudit() {}
    public LoginFailAudit(String username, String ip, String userAgent, LocalDateTime timestamp, int failCount) {
        this.username = username;
        this.ip = ip;
        this.userAgent = userAgent;
        this.timestamp = timestamp;
        this.failCount = failCount;
    }
    // Getters y setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getIp() { return ip; }
    public String getUserAgent() { return userAgent; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getFailCount() { return failCount; }
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setIp(String ip) { this.ip = ip; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setFailCount(int failCount) { this.failCount = failCount; }
}
