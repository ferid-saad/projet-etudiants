package com.example.api.service;

import com.example.api.dto.DepartementDTO;
import com.example.api.entity.Departement;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.mapper.DepartementMapper;
import com.example.api.repository.DepartementRepository;
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
class DepartementServiceTest {

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private DepartementMapper mapper;

    @InjectMocks
    private DepartementService service;

    @Test
    void shouldReturnAllDepartements() {
        Departement entity = new Departement();
        DepartementDTO dto = new DepartementDTO();

        when(departementRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        List<DepartementDTO> result = service.findAll();

        assertThat(result).hasSize(1).containsExactly(dto);
        verify(departementRepository).findAll();
        verify(mapper).toDTO(entity);
    }

    @Test
    void shouldReturnDepartementByIdWhenExists() {
        Long id = 7L;
        Departement entity = new Departement();
        DepartementDTO dto = new DepartementDTO();

        when(departementRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(entity)).thenReturn(dto);

        DepartementDTO result = service.findById(id);

        assertThat(result).isSameAs(dto);
        verify(departementRepository).findById(id);
        verify(mapper).toDTO(entity);
    }

    @Test
    void shouldThrowWhenDepartementNotFoundById() {
        Long id = 404L;
        when(departementRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Departement")
                .hasMessageContaining("404");
        verify(departementRepository).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void shouldCreateDepartement() {
        DepartementDTO inputDto = new DepartementDTO();
        Departement mappedEntity = new Departement();
        Departement savedEntity = new Departement();
        DepartementDTO savedDto = new DepartementDTO();

        when(mapper.toEntity(inputDto)).thenReturn(mappedEntity);
        when(departementRepository.save(mappedEntity)).thenReturn(savedEntity);
        when(mapper.toDTO(savedEntity)).thenReturn(savedDto);

        DepartementDTO result = service.save(inputDto);

        assertThat(result).isSameAs(savedDto);
        verify(mapper).toEntity(inputDto);
        verify(departementRepository).save(mappedEntity);
        verify(mapper).toDTO(savedEntity);
    }

    @Test
    void shouldUpdateDepartementWhenExists() {
        Long id = 3L;
        DepartementDTO inputDto = new DepartementDTO();
        Departement existing = new Departement();
        Departement mappedEntity = new Departement();
        Departement savedEntity = new Departement();
        DepartementDTO savedDto = new DepartementDTO();

        when(departementRepository.findById(id)).thenReturn(Optional.of(existing));
        when(mapper.toEntity(inputDto)).thenReturn(mappedEntity);
        when(departementRepository.save(mappedEntity)).thenReturn(savedEntity);
        when(mapper.toDTO(savedEntity)).thenReturn(savedDto);

        DepartementDTO result = service.update(id, inputDto);

        assertThat(result).isSameAs(savedDto);
        assertThat(inputDto.getId()).isEqualTo(id);
        verify(departementRepository).findById(id);
        verify(mapper).toEntity(inputDto);
        verify(departementRepository).save(mappedEntity);
        verify(mapper).toDTO(savedEntity);
    }

    @Test
    void shouldThrowWhenUpdatingUnknownDepartement() {
        Long id = 999L;
        DepartementDTO inputDto = new DepartementDTO();

        when(departementRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(id, inputDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Departement")
                .hasMessageContaining("999");
        verify(departementRepository).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void shouldDeleteDepartementWhenExists() {
        Long id = 11L;
        when(departementRepository.existsById(id)).thenReturn(true);

        service.delete(id);

        verify(departementRepository).existsById(id);
        verify(departementRepository).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeletingUnknownDepartement() {
        Long id = 55L;
        when(departementRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Departement")
                .hasMessageContaining("55");
        verify(departementRepository).existsById(id);
        verify(departementRepository, never()).deleteById(anyLong());
    }
}
