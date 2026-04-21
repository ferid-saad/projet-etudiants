package com.example.api.controller;

import com.example.api.dto.EtudiantDTO;
import com.example.api.service.EtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
@Tag(name = "Etudiants", description = "CRUD complet pour la gestion des etudiants")
public class EtudiantController {

    private final EtudiantService service;

    public EtudiantController(EtudiantService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lister tous les etudiants",
               description = "Retourne tous les etudiants. Filtrable par annee avec ?annee=2022")
    @ApiResponse(responseCode = "200", description = "Liste recuperee avec succes")
    public ResponseEntity<List<EtudiantDTO>> findAll(
            @RequestParam(required = false) Integer annee) {
        if (annee != null) {
            return ResponseEntity.ok(service.findByAnnee(annee));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer un etudiant par ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Etudiant trouve"),
        @ApiResponse(responseCode = "404", description = "Etudiant introuvable")
    })
    public ResponseEntity<EtudiantDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Creer un etudiant")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Etudiant cree avec succes"),
        @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    public ResponseEntity<EtudiantDTO> create(@RequestBody EtudiantDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre a jour un etudiant")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Etudiant mis a jour"),
        @ApiResponse(responseCode = "404", description = "Etudiant introuvable")
    })
    public ResponseEntity<EtudiantDTO> update(@PathVariable Long id,
                                              @RequestBody EtudiantDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un etudiant")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Etudiant supprime"),
        @ApiResponse(responseCode = "404", description = "Etudiant introuvable")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}