package com.example.bankaccount.service;

import com.example.bankaccount.dto.CompteDTO;
import com.example.bankaccount.entity.Compte;
import com.example.bankaccount.mapper.CompteMapper;
import com.example.bankaccount.repository.CompteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompteServiceTest {

    @Mock
    private CompteRepository compteRepository;

    @Mock
    private CompteMapper compteMapper;

    @InjectMocks
    private CompteService compteService;

    @Test
    void shouldCreateCompte() {
        // Given
        CompteDTO compteDTO = new CompteDTO();
        compteDTO.setNumeroCompte("TEST001");
        compteDTO.setProprietaire("Test User");
        compteDTO.setSolde(new BigDecimal("1000.00"));
        compteDTO.setTypeCompte("COURANT");

        Compte compte = new Compte();
        compte.setId(1L);
        compte.setNumeroCompte("TEST001");
        compte.setProprietaire("Test User");
        compte.setSolde(new BigDecimal("1000.00"));
        compte.setTypeCompte(Compte.TypeCompte.COURANT);

        when(compteMapper.toEntity(compteDTO)).thenReturn(compte);
        when(compteRepository.save(compte)).thenReturn(compte);
        when(compteMapper.toDTO(compte)).thenReturn(compteDTO);

        // When
        CompteDTO result = compteService.createCompte(compteDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getNumeroCompte()).isEqualTo("TEST001");
        verify(compteRepository, times(1)).save(compte);
    }

    @Test
    void shouldGetAllComptes() {
        // Given
        Compte compte1 = new Compte();
        compte1.setId(1L);
        compte1.setNumeroCompte("TEST001");

        Compte compte2 = new Compte();
        compte2.setId(2L);
        compte2.setNumeroCompte("TEST002");

        CompteDTO dto1 = new CompteDTO();
        dto1.setId(1L);
        dto1.setNumeroCompte("TEST001");

        CompteDTO dto2 = new CompteDTO();
        dto2.setId(2L);
        dto2.setNumeroCompte("TEST002");

        when(compteRepository.findAll()).thenReturn(Arrays.asList(compte1, compte2));
        when(compteMapper.toDTO(compte1)).thenReturn(dto1);
        when(compteMapper.toDTO(compte2)).thenReturn(dto2);

        // When
        List<CompteDTO> result = compteService.getAllComptes();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNumeroCompte()).isEqualTo("TEST001");
        assertThat(result.get(1).getNumeroCompte()).isEqualTo("TEST002");
    }

    @Test
    void shouldGetCompteById() {
        // Given
        Compte compte = new Compte();
        compte.setId(1L);
        compte.setNumeroCompte("TEST001");

        CompteDTO compteDTO = new CompteDTO();
        compteDTO.setId(1L);
        compteDTO.setNumeroCompte("TEST001");

        when(compteRepository.findById(1L)).thenReturn(Optional.of(compte));
        when(compteMapper.toDTO(compte)).thenReturn(compteDTO);

        // When
        Optional<CompteDTO> result = compteService.getCompteById(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getNumeroCompte()).isEqualTo("TEST001");
    }

    @Test
    void shouldDeleteCompte() {
        // Given
        when(compteRepository.existsById(1L)).thenReturn(true);

        // When
        boolean result = compteService.deleteCompte(1L);

        // Then
        assertThat(result).isTrue();
        verify(compteRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldNotDeleteNonExistentCompte() {
        // Given
        when(compteRepository.existsById(1L)).thenReturn(false);

        // When
        boolean result = compteService.deleteCompte(1L);

        // Then
        assertThat(result).isFalse();
        verify(compteRepository, never()).deleteById(1L);
    }
}