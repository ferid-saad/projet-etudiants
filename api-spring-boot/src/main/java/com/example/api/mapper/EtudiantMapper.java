package com.example.api.mapper;

import com.example.api.dto.EtudiantDTO;
import com.example.api.entity.Departement;
import com.example.api.entity.Etudiant;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class EtudiantMapper {

    public EtudiantDTO toDTO(Etudiant e) {
        if (e == null) return null;
        return new EtudiantDTO(
                e.getId(),
                e.getCin(),
                e.getNom(),
                e.getDateNaissance(),       // LocalDate → géré par le constructeur DTO
                e.getEmail(),
                e.getAnneePremiereInscription(),
                e.getDepartement() != null ? e.getDepartement().getId() : null,
                e.getDepartement() != null ? e.getDepartement().getNom() : null,
                e.age()
        );
    }

    public Etudiant toEntity(EtudiantDTO dto, Departement departement) {
        if (dto == null) return null;
        return new Etudiant(
                dto.getId(),
                dto.getCin(),
                dto.getNom(),
                dto.getDateNaissance() != null ? LocalDate.parse(dto.getDateNaissance()) : null, // String → LocalDate
                dto.getEmail(),
                dto.getAnneePremiereInscription(),
                departement
        );
    }
}