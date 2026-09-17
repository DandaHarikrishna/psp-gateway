package com.nman.apiagent.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "vpa")
public class Vpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String vpaId;

    @Column(nullable = false, unique = true)
    private String vpa;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private boolean primaryVpa;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getVpaId() {
        return vpaId;
    }

    public void setVpaId(String vpaId) {
        this.vpaId = vpaId;
    }

    public String getVpa() {
        return vpa;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isPrimaryVpa() {
        return primaryVpa;
    }

    public void setPrimaryVpa(boolean primaryVpa) {
        this.primaryVpa = primaryVpa;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
