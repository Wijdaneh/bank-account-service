package com.example.bankaccount.repository;

import com.example.bankaccount.entity.Compte;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CompteRepositoryTest {

    @Autowired
    private CompteRepository compteRepository;

    @Test
    void shouldSaveCompte() {
        // Given
        Compte compte = new Compte();
        compte.setNumeroCompte("TEST001");
        compte.setProprietaire("Test User");
        compte.setSolde(new BigDecimal("1000.00"));
        compte.setTypeCompte(Compte.TypeCompte.COURANT);

        // When
        Compte saved = compteRepository.save(compte);

        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getNumeroCompte()).isEqualTo("TEST001");
    }

    @Test
    void shouldFindCompteByNumero() {
        // Given
        Compte compte = new Compte();
        compte.setNumeroCompte("TEST002");
        compte.setProprietaire("Test User");
        compte.setSolde(new BigDecimal("2000.00"));
        compte.setTypeCompte(Compte.TypeCompte.EPARGNE);
        compteRepository.save(compte);

        // When
        Optional<Compte> found = compteRepository.findByNumeroCompte("TEST002");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getProprietaire()).isEqualTo("Test User");
    }

    @Test
    void shouldFindComptesByProprietaire() {
        // Given
        Compte compte1 = new Compte();
        compte1.setNumeroCompte("TEST003");
        compte1.setProprietaire("John Doe");
        compte1.setSolde(new BigDecimal("1500.00"));
        compte1.setTypeCompte(Compte.TypeCompte.COURANT);

        Compte compte2 = new Compte();
        compte2.setNumeroCompte("TEST004");
        compte2.setProprietaire("John Doe");
        compte2.setSolde(new BigDecimal("3000.00"));
        compte2.setTypeCompte(Compte.TypeCompte.EPARGNE);

        compteRepository.save(compte1);
        compteRepository.save(compte2);

        // When
        List<Compte> comptes = compteRepository.findByProprietaire("John Doe");

        // Then
        assertThat(comptes).hasSize(2);
    }

    @Test
    void shouldCheckIfNumeroCompteExists() {
        // Given
        Compte compte = new Compte();
        compte.setNumeroCompte("TEST005");
        compte.setProprietaire("Test User");
        compte.setSolde(new BigDecimal("1000.00"));
        compte.setTypeCompte(Compte.TypeCompte.COURANT);
        compteRepository.save(compte);

        // When
        boolean exists = compteRepository.existsByNumeroCompte("TEST005");

        // Then
        assertThat(exists).isTrue();
    }
}