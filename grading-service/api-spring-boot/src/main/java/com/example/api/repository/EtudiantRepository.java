package com.example.api.repository;

import com.example.api.entity.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    // Q9 - Filtre par anneePremiereInscription -> GET /api/etudiants?annee=2022
    List<Etudiant> findByAnneePremiereInscription(int annee);
}