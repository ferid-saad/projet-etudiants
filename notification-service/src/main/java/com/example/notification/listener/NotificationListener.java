package com.example.notification.listener;

import com.example.notification.dto.EtudiantEvent;
import com.example.notification.dto.NoteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);

    @KafkaListener(topics = "etudiant-created", groupId = "notification-group")
    public void onEtudiantCreated(EtudiantEvent event) {
        log.info("📧 [NOTIFICATION] Nouvel étudiant inscrit : {} (ID: {}). Email de bienvenue simulé envoyé à {}",
                event.getNom(), event.getEtudiantId(), event.getEmail());
    }

    @KafkaListener(topics = "note-created", groupId = "notification-group")
    public void onNoteCreated(NoteEvent event) {
        log.info("📧 [NOTIFICATION] Nouvelle note enregistrée pour l'étudiant ID {} — Matière : {}, Valeur : {}",
                event.getStudentId(), event.getMatiere(), event.getValeur());
    }
}
