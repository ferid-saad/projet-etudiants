package com.example.grading.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "L'identifiant de l'étudiant est obligatoire")
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @NotBlank(message = "La matière est obligatoire")
    @Size(max = 100, message = "La matière ne doit pas dépasser 100 caractères")
    @Column(nullable = false)
    private String matiere;

    @NotNull(message = "La valeur de la note est obligatoire")
    @DecimalMin(value = "0.0", message = "La note doit être >= 0")
    @DecimalMax(value = "20.0", message = "La note doit être <= 20")
    @Column(nullable = false)
    private Double valeur;

    public Note() {}

    public Note(Long id, Long studentId, String matiere, Double valeur) {
        this.id = id;
        this.studentId = studentId;
        this.matiere = matiere;
        this.valeur = valeur;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }
    public Double getValeur() { return valeur; }
    public void setValeur(Double valeur) { this.valeur = valeur; }
}