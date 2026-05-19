package com.example.api.kafka;

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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long etudiantId;
        private String nom;
        private String email;
        private LocalDateTime timestamp;

        public Builder etudiantId(Long etudiantId) { this.etudiantId = etudiantId; return this; }
        public Builder nom(String nom) { this.nom = nom; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public EtudiantEvent build() {
            return new EtudiantEvent(etudiantId, nom, email, timestamp);
        }
    }
}
