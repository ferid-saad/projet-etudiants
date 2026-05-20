package com.example.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "departements")
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    // Constructeur vide requis par JPA
    public Departement() {}

    public Departement(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}