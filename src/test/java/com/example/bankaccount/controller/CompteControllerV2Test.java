package com.example.bankaccount.controller;

import com.example.bankaccount.dto.CompteDTO;
import com.example.bankaccount.service.CompteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CompteControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompteService compteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllComptesV2() throws Exception {
        // Given
        CompteDTO dto1 = new CompteDTO();
        dto1.setId(1L);
        dto1.setNumeroCompte("V2TEST001");
        dto1.setProprietaire("V2 User 1");

        CompteDTO dto2 = new CompteDTO();
        dto2.setId(2L);
        dto2.setNumeroCompte("V2TEST002");
        dto2.setProprietaire("V2 User 2");

        when(compteService.getAllComptes()).thenReturn(Arrays.asList(dto1, dto2));

        // When & Then
        mockMvc.perform(get("/api/v2/comptes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroCompte").value("V2TEST001"))
                .andExpect(jsonPath("$[1].numeroCompte").value("V2TEST002"));
    }

    @Test
    void shouldGetCompteByIdV2() throws Exception {
        // Given
        CompteDTO dto = new CompteDTO();
        dto.setId(1L);
        dto.setNumeroCompte("V2TEST003");
        dto.setProprietaire("V2 User 3");
        dto.setSolde(new BigDecimal("3000.00"));
        dto.setTypeCompte("COURANT");

        when(compteService.getCompteById(1L)).thenReturn(Optional.of(dto));

        // When & Then
        mockMvc.perform(get("/api/v2/comptes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCompte").value("V2TEST003"))
                .andExpect(jsonPath("$.proprietaire").value("V2 User 3"));
    }

    @Test
    void shouldCreateCompteV2() throws Exception {
        // Given
        CompteDTO inputDTO = new CompteDTO();
        inputDTO.setNumeroCompte("V2TEST004");
        inputDTO.setProprietaire("New User");
        inputDTO.setSolde(new BigDecimal("4000.00"));
        inputDTO.setTypeCompte("EPARGNE");

        CompteDTO outputDTO = new CompteDTO();
        outputDTO.setId(1L);
        outputDTO.setNumeroCompte("V2TEST004");
        outputDTO.setProprietaire("New User");
        outputDTO.setSolde(new BigDecimal("4000.00"));
        outputDTO.setTypeCompte("EPARGNE");

        when(compteService.createCompte(any(CompteDTO.class))).thenReturn(outputDTO);

        // When & Then
        mockMvc.perform(post("/api/v2/comptes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.numeroCompte").value("V2TEST004"));
    }

    @Test
    void shouldUpdateCompteV2() throws Exception {
        // Given
        CompteDTO inputDTO = new CompteDTO();
        inputDTO.setProprietaire("Updated User");
        inputDTO.setSolde(new BigDecimal("5000.00"));
        inputDTO.setTypeCompte("PROFESSIONNEL");

        CompteDTO outputDTO = new CompteDTO();
        outputDTO.setId(1L);
        outputDTO.setNumeroCompte("V2TEST005");
        outputDTO.setProprietaire("Updated User");
        outputDTO.setSolde(new BigDecimal("5000.00"));
        outputDTO.setTypeCompte("PROFESSIONNEL");

        when(compteService.updateCompte(anyLong(), any(CompteDTO.class))).thenReturn(Optional.of(outputDTO));

        // When & Then
        mockMvc.perform(put("/api/v2/comptes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.proprietaire").value("Updated User"));
    }

    @Test
    void shouldDeleteCompteV2() throws Exception {
        // Given
        when(compteService.deleteCompte(1L)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/api/v2/comptes/1"))
                .andExpect(status().isOk());
    }
}