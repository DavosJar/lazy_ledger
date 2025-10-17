package com.lazyledger.backend.moduloSeguridad.infrastructure.persistance.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserDbo {

    @Id
    private UUID id;

    @Column(nullable = true)  // Will be set when customer profile is created
    private UUID customerId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = true)
    private Instant lastLoginAt;

    @Column(nullable = false)
    private boolean enabled = true;

    // Default constructor
    public UserDbo() {}

    // Constructor for registration (no customer yet)
    public UserDbo(UUID id, String username, String password, Instant createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.enabled = true;
    }

    // Constructor with customer relationship
    public UserDbo(UUID id, UUID customerId, String username, String password, Instant createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.enabled = true;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Instant lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}