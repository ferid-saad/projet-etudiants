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

    @Column(unique = true, nullable = false)
    private String cin;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private LocalDate dateNaissance;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private int anneePremiereInscription;

    // Q7 — Relation ManyToOne vers Departement
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id")
    private Departement departement;

    // Constructeur vide requis par JPA
    public Etudiant() {}

    // Constructeur complet
    public Etudiant(Long id, String cin, String nom, LocalDate dateNaissance,
                    String email, int anneePremiereInscription, Departement departement) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.anneePremiereInscription = anneePremiereInscription;
        this.departement = departement;
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

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAnneePremiereInscription() { return anneePremiereInscription; }
    public void setAnneePremiereInscription(int anneePremiereInscription) {
        this.anneePremiereInscription = anneePremiereInscription;
    }

    public Departement getDepartement() { return departement; }
    public void setDepartement(Departement departement) { this.departement = departement; }

    // Q2 — Calcule dynamiquement l'âge de l'étudiant
    public int age() {
        return Period.between(this.dateNaissance, LocalDate.now()).getYears();
    }
}