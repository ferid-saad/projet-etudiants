package com.example.api.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "etudiants")
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cin;
    private String nom;
    private LocalDate dateNaissance;

    // Constructeur vide requis par JPA
    public Etudiant() {}

    // Constructeur complet
    public Etudiant(Long id, String cin, String nom, LocalDate dateNaissance) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    // Méthode calculant l'âge
    public int age() {
        return Period.between(this.dateNaissance, LocalDate.now()).getYears();
    }
}
