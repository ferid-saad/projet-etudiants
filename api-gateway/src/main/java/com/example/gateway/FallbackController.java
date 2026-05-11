package com.example.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/etudiants")
    public ResponseEntity<Map<String, Object>> etudiantsFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "timestamp", LocalDateTime.now().toString(),
                        "status", 503,
                        "message", "etudiant-service est temporairement indisponible.",
                        "service", "etudiant-service"
                ));
    }

    @GetMapping("/notes")
    public ResponseEntity<Map<String, Object>> notesFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "timestamp", LocalDateTime.now().toString(),
                        "status", 503,
                        "message", "grading-service est temporairement indisponible.",
                        "service", "grading-service"
                ));
    }
}
