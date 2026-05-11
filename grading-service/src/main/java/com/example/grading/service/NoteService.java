package com.example.grading.service;

import com.example.grading.dto.NoteDTO;
import com.example.grading.entity.Note;
import com.example.grading.exception.EtudiantNotFoundException;
import com.example.grading.exception.NoteNotFoundException;
import com.example.grading.mapper.NoteMapper;
import com.example.grading.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final RestTemplate restTemplate;

    @Value("${etudiant.service.url}")
    private String etudiantServiceUrl;

    public NoteService(NoteRepository noteRepository,
                       NoteMapper noteMapper,
                       RestTemplate restTemplate) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
        this.restTemplate = restTemplate;
    }

    private void verifierEtudiantExiste(Long studentId) {
        try {
            restTemplate.getForObject(
                    etudiantServiceUrl + "/api/etudiants/" + studentId,
                    Object.class
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new EtudiantNotFoundException(studentId);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Impossible de joindre le micro-service étudiants : " + e.getMessage()
            );
        }
    }

    @Transactional(readOnly = true)
    public List<NoteDTO> findAll() {
        return noteRepository.findAll()
                .stream()
                .map(noteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NoteDTO findById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
        return noteMapper.toDTO(note);
    }

    @Transactional(readOnly = true)
    public List<NoteDTO> findByStudentId(Long studentId) {
        verifierEtudiantExiste(studentId);
        return noteRepository.findByStudentId(studentId)
                .stream()
                .map(noteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NoteDTO> findByMatiere(String matiere) {
        return noteRepository.findByMatiere(matiere)
                .stream()
                .map(noteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public NoteDTO create(NoteDTO dto) {
        verifierEtudiantExiste(dto.getStudentId());
        Note note = noteMapper.toEntity(dto);
        note.setId(null);
        return noteMapper.toDTO(noteRepository.save(note));
    }

    public NoteDTO update(Long id, NoteDTO dto) {
        noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
        verifierEtudiantExiste(dto.getStudentId());
        Note note = noteMapper.toEntity(dto);
        note.setId(id);
        return noteMapper.toDTO(noteRepository.save(note));
    }

    public void delete(Long id) {
        noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
        noteRepository.deleteById(id);
    }
}