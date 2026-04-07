package com.example.api;

import com.example.api.entity.Etudiant;
import com.example.api.repository.EtudiantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EtudiantRepository repository;

    public DataInitializer(EtudiantRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.saveAll(List.of(
                new Etudiant("AB123456", "Ahmed Benali",       LocalDate.of(2001, 3, 15)),
                new Etudiant("CD789012", "Fatima Zahra Alami", LocalDate.of(2002, 7, 22)),
                new Etudiant("EF345678", "Mohamed Chakir",     LocalDate.of(2000, 11, 8)),
                new Etudiant("GH901234", "Sara Mansouri",      LocalDate.of(2003, 1, 30)),
                new Etudiant("IJ567890", "Youssef Kadiri",     LocalDate.of(2001, 9, 14))
            ));
            System.out.println("✅ Données initiales chargées avec succès !");
        }
    }
}
