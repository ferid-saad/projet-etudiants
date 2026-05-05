package com.example.api.mapper;

import com.example.api.dto.DepartementDTO;
import com.example.api.entity.Departement;
import org.springframework.stereotype.Component;

@Component
public class DepartementMapper {

    public DepartementDTO toDTO(Departement d) {
        if (d == null) return null;
        return new DepartementDTO(d.getId(), d.getNom());
    }

    public Departement toEntity(DepartementDTO dto) {
        if (dto == null) return null;
        return new Departement(dto.getId(), dto.getNom());
    }
}