package com.example.bankaccount.service;

import com.example.bankaccount.dto.CompteDTO;
import com.example.bankaccount.entity.Compte;
import com.example.bankaccount.mapper.CompteMapper;
import com.example.bankaccount.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;
    
    @Autowired
    private CompteMapper compteMapper;

    public CompteDTO createCompte(CompteDTO compteDTO) {
        Compte compte = compteMapper.toEntity(compteDTO);
        compte = compteRepository.save(compte);
        return compteMapper.toDTO(compte);
    }

    public List<CompteDTO> getAllComptes() {
        return compteRepository.findAll()
                .stream()
                .map(compteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CompteDTO> getCompteById(Long id) {
        return compteRepository.findById(id)
                .map(compteMapper::toDTO);
    }

    public Optional<CompteDTO> updateCompte(Long id, CompteDTO compteDTO) {
        return compteRepository.findById(id)
                .map(compte -> {
                    compte.setProprietaire(compteDTO.getProprietaire());
                    compte.setSolde(compteDTO.getSolde());
                    compte.setTypeCompte(Compte.TypeCompte.valueOf(compteDTO.getTypeCompte()));
                    return compteMapper.toDTO(compteRepository.save(compte));
                });
    }

    public boolean deleteCompte(Long id) {
        if (compteRepository.existsById(id)) {
            compteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<CompteDTO> faireDepot(Long id, BigDecimal montant) {
        return compteRepository.findById(id)
                .map(compte -> {
                    if (montant.compareTo(BigDecimal.ZERO) > 0) {
                        compte.setSolde(compte.getSolde().add(montant));
                        return compteMapper.toDTO(compteRepository.save(compte));
                    }
                    return null;
                });
    }

    public Optional<CompteDTO> faireRetrait(Long id, BigDecimal montant) {
        return compteRepository.findById(id)
                .map(compte -> {
                    if (montant.compareTo(BigDecimal.ZERO) > 0 && 
                        compte.getSolde().compareTo(montant) >= 0) {
                        compte.setSolde(compte.getSolde().subtract(montant));
                        return compteMapper.toDTO(compteRepository.save(compte));
                    }
                    return null;
                });
    }
}