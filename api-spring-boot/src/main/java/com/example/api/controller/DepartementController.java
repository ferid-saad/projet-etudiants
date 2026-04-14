package com.example.api.controller;

import com.example.api.dto.DepartementDTO;
import com.example.api.service.DepartementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departements")
public class DepartementController {

    private final DepartementService service;

    public DepartementController(DepartementService service) {
        this.service = service;
    }

    // GET /api/departements
    @GetMapping
    public ResponseEntity<List<DepartementDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // GET /api/departements/{id}
    @GetMapping("/{id}")
    public ResponseEntity<DepartementDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // POST /api/departements -> 201 Created
    @PostMapping
    public ResponseEntity<DepartementDTO> create(@RequestBody DepartementDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    // PUT /api/departements/{id}
    @PutMapping("/{id}")
    public ResponseEntity<DepartementDTO> update(@PathVariable Long id,
                                                  @RequestBody DepartementDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // DELETE /api/departements/{id} -> 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}