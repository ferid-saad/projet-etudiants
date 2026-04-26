package com.example.grading.controller;

import com.example.grading.dto.NoteDTO;
import com.example.grading.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@Tag(name = "Notes", description = "CRUD des notes étudiantes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    @Operation(summary = "Lister toutes les notes")
    public ResponseEntity<List<NoteDTO>> getAll() {
        return ResponseEntity.ok(noteService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une note par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note trouvée"),
            @ApiResponse(responseCode = "404", description = "Note introuvable")
    })
    public ResponseEntity<NoteDTO> getById(
            @Parameter(description = "ID de la note") @PathVariable Long id) {
        return ResponseEntity.ok(noteService.findById(id));
    }

    @GetMapping("/etudiant/{studentId}")
    @Operation(summary = "Obtenir toutes les notes d'un étudiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notes retournées"),
            @ApiResponse(responseCode = "404", description = "Étudiant introuvable")
    })
    public ResponseEntity<List<NoteDTO>> getByStudentId(
            @Parameter(description = "ID de l'étudiant") @PathVariable Long studentId) {
        return ResponseEntity.ok(noteService.findByStudentId(studentId));
    }

    @GetMapping("/matiere/{matiere}")
    @Operation(summary = "Obtenir toutes les notes d'une matière")
    public ResponseEntity<List<NoteDTO>> getByMatiere(
            @Parameter(description = "Nom de la matière") @PathVariable String matiere) {
        return ResponseEntity.ok(noteService.findByMatiere(matiere));
    }

    @PostMapping
    @Operation(summary = "Créer une note")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Note créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Étudiant introuvable")
    })
    public ResponseEntity<NoteDTO> create(@Valid @RequestBody NoteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une note")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note modifiée"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Note ou étudiant introuvable")
    })
    public ResponseEntity<NoteDTO> update(
            @Parameter(description = "ID de la note") @PathVariable Long id,
            @Valid @RequestBody NoteDTO dto) {
        return ResponseEntity.ok(noteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une note")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Note supprimée"),
            @ApiResponse(responseCode = "404", description = "Note introuvable")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la note") @PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}