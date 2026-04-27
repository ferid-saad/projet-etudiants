package com.example.api.service;

import com.example.api.dto.DepartementDTO;
import com.example.api.entity.Departement;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.mapper.DepartementMapper;
import com.example.api.repository.DepartementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartementService {

    private final DepartementRepository departementRepository;
    private final DepartementMapper mapper;

    public DepartementService(DepartementRepository departementRepository,
                              DepartementMapper mapper) {
        this.departementRepository = departementRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<DepartementDTO> findAll() {
        return departementRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public DepartementDTO findById(Long id) {
        Departement d = departementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departement", id));
        return mapper.toDTO(d);
    }

    public DepartementDTO save(DepartementDTO dto) {
        Departement d = mapper.toEntity(dto);
        return mapper.toDTO(departementRepository.save(d));
    }

    public DepartementDTO update(Long id, DepartementDTO dto) {
        departementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departement", id));
        dto.setId(id);
        return mapper.toDTO(departementRepository.save(mapper.toEntity(dto)));
    }

    public void delete(Long id) {
        if (!departementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Departement", id);
        }
        departementRepository.deleteById(id);
    }
}