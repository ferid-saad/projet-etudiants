package com.example.api.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class EtudiantTest {

    @Test
    void shouldCalculateAgeWhenBirthdayAlreadyPassedThisYear() {
        Etudiant etudiant = new Etudiant();
        etudiant.setDateNaissance(LocalDate.now().minusYears(20).minusDays(1));

        assertThat(etudiant.age()).isEqualTo(20);
    }

    @Test
    void shouldCalculateAgeWhenBirthdayNotPassedYetThisYear() {
        Etudiant etudiant = new Etudiant();
        etudiant.setDateNaissance(LocalDate.now().minusYears(20).plusDays(1));

        assertThat(etudiant.age()).isEqualTo(19);
    }
}
