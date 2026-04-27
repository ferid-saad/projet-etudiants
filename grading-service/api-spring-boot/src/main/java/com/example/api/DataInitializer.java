package com.example.api;

import com.example.api.entity.Departement;
import com.example.api.entity.Etudiant;
import com.example.api.repository.DepartementRepository;
import com.example.api.repository.EtudiantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EtudiantRepository etudiantRepository;
    private final DepartementRepository departementRepository;

    public DataInitializer(EtudiantRepository etudiantRepository,
                           DepartementRepository departementRepository) {
        this.etudiantRepository = etudiantRepository;
        this.departementRepository = departementRepository;
    }

    @Override
    public void run(String... args) {

        if (departementRepository.count() == 0) {
            departementRepository.saveAll(List.of(
                new Departement(null, "Informatique"),
                new Departement(null, "Mathematiques"),
                new Departement(null, "Physique")
            ));
        }

        if (etudiantRepository.count() == 0) {
            Departement info  = departementRepository.findAll().get(0);
            Departement maths = departementRepository.findAll().get(1);
            Departement phys  = departementRepository.findAll().get(2);

            etudiantRepository.saveAll(List.of(
                new Etudiant(null, "AB123456", "Ahmed Benali",
                             LocalDate.of(2001, 3, 15),
                             "ahmed.benali@email.com", 2020, info),
                new Etudiant(null, "CD789012", "Fatima Zahra Alami",
                             LocalDate.of(2002, 7, 22),
                             "fatima.alami@email.com", 2021, maths),
                new Etudiant(null, "EF345678", "Mohamed Chakir",
                             LocalDate.of(2000, 11, 8),
                             "mohamed.chakir@email.com", 2019, info),
                new Etudiant(null, "GH901234", "Sara Mansouri",
                             LocalDate.of(2003, 1, 30),
                             "sara.mansouri@email.com", 2022, phys),
                new Etudiant(null, "IJ567890", "Youssef Kadiri",
                             LocalDate.of(2001, 9, 14),
                             "youssef.kadiri@email.com", 2020, maths)
            ));
            System.out.println("Donnees initiales chargees avec succes !");
        }
    }
}