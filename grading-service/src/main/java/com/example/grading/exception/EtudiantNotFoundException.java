package com.example.grading.exception;

public class EtudiantNotFoundException extends RuntimeException {
    public EtudiantNotFoundException(Long studentId) {
        super("Étudiant introuvable avec l'ID : " + studentId);
    }
}
