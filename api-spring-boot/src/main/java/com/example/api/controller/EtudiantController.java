package com.example.api.controller;

import com.example.api.dto.EtudiantDTO;
import com.example.api.service.EtudiantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    private final EtudiantService service;

    public EtudiantController(EtudiantService service) {
        this.service = service;
    }

    // GET /api/etudiants ou GET /api/etudiants?annee=2022
    @GetMapping
    public ResponseEntity<List<EtudiantDTO>> findAll(
            @RequestParam(required = false) Integer annee) {
        if (annee != null) {
            return ResponseEntity.ok(service.findByAnnee(annee));
        }
        return ResponseEntity.ok(service.findAll());
    }

    // GET /api/etudiants/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EtudiantDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // POST /api/etudiants -> 201 Created
    @PostMapping
    public ResponseEntity<EtudiantDTO> create(@RequestBody EtudiantDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    // PUT /api/etudiants/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EtudiantDTO> update(@PathVariable Long id,
                                              @RequestBody EtudiantDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // DELETE /api/etudiants/{id} -> 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}