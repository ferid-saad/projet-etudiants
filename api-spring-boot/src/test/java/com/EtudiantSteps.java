package com.example.api;


import com.example.api.entity.Etudiant;
import io.cucumber.java.fr.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class EtudiantSteps {
    private Etudiant etudiant;
    private int ageCalcule;

    @Etantdonné("un étudiant avec la date de naissance {string}")
    public void un_etudiant_avec_la_date_de_naissance(String dateNaissance) {
        etudiant = new Etudiant();
        etudiant.setDateNaissance(LocalDate.parse(dateNaissance));
    }

    @Quand("on calcule son âge")
    public void on_calcule_son_age() {
        ageCalcule = etudiant.age();
    }

    @Alors("l'âge retourné doit être {int}")
    public void l_age_retourne_doit_etre(Integer ageAttendu) {
        assertEquals(ageAttendu, ageCalcule);
    }
}
