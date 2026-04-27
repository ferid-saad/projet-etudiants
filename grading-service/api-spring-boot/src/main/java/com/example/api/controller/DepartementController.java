package com.example.api.controller;

import com.example.api.dto.DepartementDTO;
import com.example.api.service.DepartementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departements")
@Tag(name = "Departements", description = "CRUD complet pour la gestion des departements")
public class DepartementController {

    private final DepartementService service;

    public DepartementController(DepartementService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lister tous les departements")
    @ApiResponse(responseCode = "200", description = "Liste recuperee avec succes")
    public ResponseEntity<List<DepartementDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer un departement par ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Departement trouve"),
        @ApiResponse(responseCode = "404", description = "Departement introuvable")
    })
    public ResponseEntity<DepartementDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "Creer un departement")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Departement cree avec succes"),
        @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    public ResponseEntity<DepartementDTO> create(@RequestBody DepartementDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre a jour un departement")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Departement mis a jour"),
        @ApiResponse(responseCode = "404", description = "Departement introuvable")
    })
    public ResponseEntity<DepartementDTO> update(@PathVariable Long id,
                                                  @RequestBody DepartementDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un departement")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Departement supprime"),
        @ApiResponse(responseCode = "404", description = "Departement introuvable")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}