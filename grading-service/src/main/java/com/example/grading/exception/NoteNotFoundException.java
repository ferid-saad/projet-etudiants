package com.example.grading.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(Long noteId) {
        super("Note introuvable avec l'ID : " + noteId);
    }
}
