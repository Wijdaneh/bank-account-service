package com.example.bankaccount.controller;

import com.example.bankaccount.entity.Compte;
import com.example.bankaccount.repository.CompteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CompteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCompte() throws Exception {
        // Given
        Compte compte = new Compte();
        compte.setNumeroCompte("TEST123");
        compte.setProprietaire("Test User");
        compte.setSolde(new BigDecimal("1000.00"));
        compte.setTypeCompte(Compte.TypeCompte.COURANT);

        // When & Then
        mockMvc.perform(post("/api/comptes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compte)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCompte").value("TEST123"))
                .andExpect(jsonPath("$.proprietaire").value("Test User"));
    }

    @Test
    void shouldGetAllComptes() throws Exception {
        // Given
        Compte compte = new Compte();
        compte.setNumeroCompte("TEST456");
        compte.setProprietaire("Test User 2");
        compte.setSolde(new BigDecimal("2000.00"));
        compte.setTypeCompte(Compte.TypeCompte.EPARGNE);
        compteRepository.save(compte);

        // When & Then
        mockMvc.perform(get("/api/comptes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldGetCompteById() throws Exception {
        // Given
        Compte compte = new Compte();
        compte.setNumeroCompte("TEST789");
        compte.setProprietaire("Test User 3");
        compte.setSolde(new BigDecimal("3000.00"));
        compte.setTypeCompte(Compte.TypeCompte.PROFESSIONNEL);
        Compte saved = compteRepository.save(compte);

        // When & Then
        mockMvc.perform(get("/api/comptes/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCompte").value("TEST789"))
                .andExpect(jsonPath("$.proprietaire").value("Test User 3"));
    }

    @Test
    void shouldReturnNotFoundForNonExistentCompte() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/comptes/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateCompte() throws Exception {
        // Given
        Compte compte = new Compte();
        compte.setNumeroCompte("TEST999");
        compte.setProprietaire("Original Name");
        compte.setSolde(new BigDecimal("4000.00"));
        compte.setTypeCompte(Compte.TypeCompte.COURANT);
        Compte saved = compteRepository.save(compte);

        Compte updatedCompte = new Compte();
        updatedCompte.setProprietaire("Updated Name");
        updatedCompte.setSolde(new BigDecimal("5000.00"));
        updatedCompte.setTypeCompte(Compte.TypeCompte.EPARGNE);

        // When & Then
        mockMvc.perform(put("/api/comptes/{id}", saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCompte)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.proprietaire").value("Updated Name"));
    }

    @Test
    void shouldDeleteCompte() throws Exception {
        // Given
        Compte compte = new Compte();
        compte.setNumeroCompte("DELETE001");
        compte.setProprietaire("To Delete");
        compte.setSolde(new BigDecimal("1000.00"));
        compte.setTypeCompte(Compte.TypeCompte.COURANT);
        Compte saved = compteRepository.save(compte);

        // When & Then
        mockMvc.perform(delete("/api/comptes/{id}", saved.getId()))
                .andExpect(status().isOk());

        // Verify deletion
        mockMvc.perform(get("/api/comptes/{id}", saved.getId()))
                .andExpect(status().isNotFound());
    }
}