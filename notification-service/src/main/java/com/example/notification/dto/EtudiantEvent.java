package com.example.notification.dto;

import java.time.LocalDateTime;

public class EtudiantEvent {
    private Long etudiantId;
    private String nom;
    private String email;
    private LocalDateTime timestamp;

    public EtudiantEvent() {
    }

    public EtudiantEvent(Long etudiantId, String nom, String email, LocalDateTime timestamp) {
        this.etudiantId = etudiantId;
        this.nom = nom;
        this.email = email;
        this.timestamp = timestamp;
    }

    public Long getEtudiantId() { return etudiantId; }
    public void setEtudiantId(Long etudiantId) { this.etudiantId = etudiantId; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
