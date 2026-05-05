package com.example.api.service;

import com.example.api.dto.EtudiantDTO;
import com.example.api.entity.Departement;
import com.example.api.entity.Etudiant;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.mapper.EtudiantMapper;
import com.example.api.repository.DepartementRepository;
import com.example.api.repository.EtudiantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private EtudiantMapper mapper;

    @InjectMocks
    private EtudiantService service;

    @Test
    void shouldReturnAllEtudiants() {
        Etudiant entity = new Etudiant();
        EtudiantDTO dto = new EtudiantDTO();

        when(etudiantRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        List<EtudiantDTO> result = service.findAll();

        assertThat(result).hasSize(1).containsExactly(dto);
        verify(etudiantRepository).findAll();
        verify(mapper).toDTO(entity);
    }

    @Test
    void shouldReturnEtudiantsByAnneeInscription() {
        int annee = 2024;
        Etudiant entity = new Etudiant();
        EtudiantDTO dto = new EtudiantDTO();

        when(etudiantRepository.findByAnneePremiereInscription(annee)).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        List<EtudiantDTO> result = service.findByAnnee(annee);

        assertThat(result).hasSize(1).containsExactly(dto);
        verify(etudiantRepository).findByAnneePremiereInscription(annee);
        verify(mapper).toDTO(entity);
    }

    @Test
    void shouldReturnEtudiantByIdWhenExists() {
        Long id = 1L;
        Etudiant entity = new Etudiant();
        EtudiantDTO dto = new EtudiantDTO();

        when(etudiantRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        EtudiantDTO result = service.findById(id);

        assertThat(result).isSameAs(dto);
        verify(etudiantRepository).findById(id);
        verify(mapper).toDTO(entity);
    }

    @Test
    void shouldThrowWhenEtudiantNotFoundById() {
        Long id = 99L;

        when(etudiantRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Etudiant")
                .hasMessageContaining(String.valueOf(id));
        verify(etudiantRepository).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void shouldCreateEtudiantWithDepartement() {
        EtudiantDTO inputDto = new EtudiantDTO();
        inputDto.setDepartementId(10L);
        Departement departement = new Departement(10L, "Informatique");
        Etudiant mappedEntity = new Etudiant();
        Etudiant savedEntity = new Etudiant();
        EtudiantDTO savedDto = new EtudiantDTO();

        when(departementRepository.findById(10L)).thenReturn(Optional.of(departement));
        when(mapper.toEntity(inputDto, departement)).thenReturn(mappedEntity);
        when(etudiantRepository.save(mappedEntity)).thenReturn(savedEntity);
        when(mapper.toDTO(savedEntity)).thenReturn(savedDto);

        EtudiantDTO result = service.save(inputDto);

        assertThat(result).isSameAs(savedDto);
        verify(departementRepository).findById(10L);
        verify(mapper).toEntity(inputDto, departement);
        verify(etudiantRepository).save(mappedEntity);
        verify(mapper).toDTO(savedEntity);
    }

    @Test
    void shouldCreateEtudiantWithoutDepartementWhenDepartementIdIsNull() {
        EtudiantDTO inputDto = new EtudiantDTO();
        inputDto.setDepartementId(null);
        Etudiant mappedEntity = new Etudiant();
        Etudiant savedEntity = new Etudiant();
        EtudiantDTO savedDto = new EtudiantDTO();

        when(mapper.toEntity(inputDto, null)).thenReturn(mappedEntity);
        when(etudiantRepository.save(mappedEntity)).thenReturn(savedEntity);
        when(mapper.toDTO(savedEntity)).thenReturn(savedDto);

        EtudiantDTO result = service.save(inputDto);

        assertThat(result).isSameAs(savedDto);
        verify(mapper).toEntity(inputDto, null);
        verify(etudiantRepository).save(mappedEntity);
        verify(mapper).toDTO(savedEntity);
        verifyNoInteractions(departementRepository);
    }

    @Test
    void shouldThrowWhenCreatingEtudiantWithUnknownDepartement() {
        EtudiantDTO inputDto = new EtudiantDTO();
        inputDto.setDepartementId(404L);

        when(departementRepository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.save(inputDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Departement")
                .hasMessageContaining("404");
        verify(departementRepository).findById(404L);
        verifyNoInteractions(mapper);
        verify(etudiantRepository, never()).save(org.mockito.ArgumentMatchers.any(Etudiant.class));
    }

    @Test
    void shouldUpdateEtudiantWhenExists() {
        Long id = 5L;
        EtudiantDTO inputDto = new EtudiantDTO();
        inputDto.setDepartementId(10L);
        Etudiant existing = new Etudiant();
        Departement departement = new Departement(10L, "Informatique");
        Etudiant mappedEntity = new Etudiant();
        Etudiant savedEntity = new Etudiant();
        EtudiantDTO savedDto = new EtudiantDTO();

        when(etudiantRepository.findById(id)).thenReturn(Optional.of(existing));
        when(departementRepository.findById(10L)).thenReturn(Optional.of(departement));
        when(mapper.toEntity(inputDto, departement)).thenReturn(mappedEntity);
        when(etudiantRepository.save(mappedEntity)).thenReturn(savedEntity);
        when(mapper.toDTO(savedEntity)).thenReturn(savedDto);

        EtudiantDTO result = service.update(id, inputDto);

        assertThat(result).isSameAs(savedDto);
        assertThat(inputDto.getId()).isEqualTo(id);
        verify(etudiantRepository).findById(id);
        verify(departementRepository).findById(10L);
        verify(mapper).toEntity(inputDto, departement);
        verify(etudiantRepository).save(mappedEntity);
        verify(mapper).toDTO(savedEntity);
    }

    @Test
    void shouldThrowWhenUpdatingUnknownEtudiant() {
        Long id = 999L;
        EtudiantDTO inputDto = new EtudiantDTO();
        inputDto.setDepartementId(10L);

        when(etudiantRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, inputDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Etudiant")
                .hasMessageContaining("999");
        verify(etudiantRepository).findById(id);
        verifyNoInteractions(departementRepository);
        verifyNoInteractions(mapper);
    }

    @Test
    void shouldDeleteEtudiantWhenExists() {
        Long id = 1L;
        when(etudiantRepository.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(etudiantRepository).existsById(id);
        verify(etudiantRepository).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeletingUnknownEtudiant() {
        Long id = 404L;
        when(etudiantRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Etudiant")
                .hasMessageContaining("404");
        verify(etudiantRepository).existsById(id);
        verify(etudiantRepository, never()).deleteById(anyLong());
    }
}
