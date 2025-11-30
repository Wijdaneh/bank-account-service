package com.example.bankaccount.controller;

import com.example.bankaccount.entity.Compte;
import com.example.bankaccount.repository.CompteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureGraphQlTester
class CompteGraphQLControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private CompteRepository compteRepository;

    @Test
    void shouldGetAllComptes() {
        // Given
        Compte compte = new Compte();
        compte.setNumeroCompte("GRAPHQL001");
        compte.setProprietaire("GraphQL User");
        compte.setSolde(new BigDecimal("1500.00"));
        compte.setTypeCompte(Compte.TypeCompte.COURANT);
        compteRepository.save(compte);

        // When & Then
        graphQlTester.document("{ comptes { numeroCompte proprietaire } }")
                .execute()
                .path("comptes")
                .entityList(Compte.class)
                .hasSizeGreaterThan(0);
    }

    @Test
    void shouldGetCompteById() {
        // Given
        Compte compte = new Compte();
        compte.setNumeroCompte("GRAPHQL002");
        compte.setProprietaire("GraphQL User 2");
        compte.setSolde(new BigDecimal("2500.00"));
        compte.setTypeCompte(Compte.TypeCompte.EPARGNE);
        Compte saved = compteRepository.save(compte);

        // When & Then
        graphQlTester.document("{ compte(id: " + saved.getId() + ") { numeroCompte proprietaire } }")
                .execute()
                .path("compte")
                .entity(Compte.class)
                .satisfies(c -> {
                    assert c.getNumeroCompte().equals("GRAPHQL002");
                    assert c.getProprietaire().equals("GraphQL User 2");
                });
    }
}