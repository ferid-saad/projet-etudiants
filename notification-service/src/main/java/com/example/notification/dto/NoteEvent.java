package com.example.notification.dto;

import java.time.LocalDateTime;

public class NoteEvent {
    private Long studentId;
    private String matiere;
    private Double valeur;
    private LocalDateTime timestamp;

    public NoteEvent() {
    }

    public NoteEvent(Long studentId, String matiere, Double valeur, LocalDateTime timestamp) {
        this.studentId = studentId;
        this.matiere = matiere;
        this.valeur = valeur;
        this.timestamp = timestamp;
    }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }
    public Double getValeur() { return valeur; }
    public void setValeur(Double valeur) { this.valeur = valeur; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
