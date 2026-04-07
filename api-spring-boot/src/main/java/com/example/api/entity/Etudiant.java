package com.example.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "etudiants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cin;

    private String nom;

    @JsonFormat(pattern = "yyyy-MM-dd")   // ✅ Annotation déplacée ici
    private LocalDate dateNaissance;

    // ✅ Constructeur personnalisé (sans id, car généré automatiquement)
    public Etudiant(String cin, String nom, LocalDate dateNaissance) {
        this.cin = cin;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
    }

    // Méthode utilitaire pour calculer l'âge
    public int age() {
        return Period.between(this.dateNaissance, LocalDate.now()).getYears();
    }
}
