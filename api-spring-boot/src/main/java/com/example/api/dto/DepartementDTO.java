package com.example.api.dto;

public class DepartementDTO {

    private Long id;
    private String nom;

    public DepartementDTO() {}

    public DepartementDTO(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}