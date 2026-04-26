package com.example.api.service;

import com.example.api.dto.EtudiantDTO;
import com.example.api.entity.Departement;
import com.example.api.entity.Etudiant;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.mapper.EtudiantMapper;
import com.example.api.repository.DepartementRepository;
import com.example.api.repository.EtudiantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;

@Service
@Transactional
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final DepartementRepository departementRepository;
    private final EtudiantMapper mapper;

    public EtudiantService(EtudiantRepository etudiantRepository,
                           DepartementRepository departementRepository,
                           EtudiantMapper mapper) {
        this.etudiantRepository = etudiantRepository;
        this.departementRepository = departementRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<EtudiantDTO> findAll() {
        return etudiantRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    // Q9 - Filtre par annee
    @Transactional(readOnly = true)
    @Cacheable(value = "etudiants")
    public List<EtudiantDTO> findByAnnee(int annee) {
        return etudiantRepository.findByAnneePremiereInscription(annee)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public EtudiantDTO findById(Long id) {
        Etudiant e = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant", id));
        return mapper.toDTO(e);
    }
    @CacheEvict(value = "etudiants", allEntries = true)
    public EtudiantDTO save(EtudiantDTO dto) {
        Departement dep = resolveDepartement(dto.getDepartementId());
        Etudiant e = mapper.toEntity(dto, dep);
        return mapper.toDTO(etudiantRepository.save(e));
    }

    public EtudiantDTO update(Long id, EtudiantDTO dto) {
        etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant", id));
        Departement dep = resolveDepartement(dto.getDepartementId());
        dto.setId(id);
        Etudiant e = mapper.toEntity(dto, dep);
        return mapper.toDTO(etudiantRepository.save(e));
    }

    public void delete(Long id) {
        if (!etudiantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Etudiant", id);
        }
        etudiantRepository.deleteById(id);
    }

    private Departement resolveDepartement(Long departementId) {
        if (departementId == null) return null;
        return departementRepository.findById(departementId)
                .orElseThrow(() -> new ResourceNotFoundException("Departement", departementId));
    }
}