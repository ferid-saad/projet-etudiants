package com.example.grading.mapper;

import com.example.grading.dto.NoteDTO;
import com.example.grading.entity.Note;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@Component
public class NoteMapper {

    public NoteDTO toDTO(Note note) {
        if (note == null) return null;
        return new NoteDTO(
                note.getId(),
                note.getStudentId(),
                note.getMatiere(),
                note.getValeur()
        );
    }

    public Note toEntity(NoteDTO dto) {
        if (dto == null) return null;
        return new Note(
                dto.getId(),
                dto.getStudentId(),
                dto.getMatiere(),
                dto.getValeur()
        );
    }
}
