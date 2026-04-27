package com.example.api.dto;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class EtudiantDTO {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Long id;
    private String cin;
    private String nom;
    private String dateNaissance;  // String au lieu de LocalDate
    private String email;
    private int anneePremiereInscription;
    private Long departementId;
    private String departementNom;
    private Integer age;

    public EtudiantDTO() {}

    public EtudiantDTO(Long id, String cin, String nom, LocalDate dateNaissance,
                       String email, int anneePremiereInscription,
                       Long departementId, String departementNom, Integer age) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.dateNaissance = dateNaissance != null ? dateNaissance.format(FORMATTER) : null;
        this.email = email;
        this.anneePremiereInscription = anneePremiereInscription;
        this.departementId = departementId;
        this.departementNom = departementNom;
        // Recalcul de l'âge si non fourni
        this.age = (age != null) ? age :
                (dateNaissance != null ? Period.between(dateNaissance, LocalDate.now()).getYears() : null);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance != null ? dateNaissance.format(FORMATTER) : null;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAnneePremiereInscription() { return anneePremiereInscription; }
    public void setAnneePremiereInscription(int anneePremiereInscription) {
        this.anneePremiereInscription = anneePremiereInscription;
    }

    public Long getDepartementId() { return departementId; }
    public void setDepartementId(Long departementId) { this.departementId = departementId; }

    public String getDepartementNom() { return departementNom; }
    public void setDepartementNom(String departementNom) { this.departementNom = departementNom; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
}