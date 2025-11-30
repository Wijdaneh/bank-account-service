package com.example.bankaccount.controller;

import com.example.bankaccount.entity.Compte;
import com.example.bankaccount.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CompteGraphQLController {

    @Autowired
    private CompteService compteService;

    @QueryMapping
    public List<Compte> comptes() {
        return compteService.getAllComptes().stream()
                .map(dto -> {
                    Compte compte = new Compte();
                    compte.setId(dto.getId());
                    compte.setNumeroCompte(dto.getNumeroCompte());
                    compte.setProprietaire(dto.getProprietaire());
                    compte.setSolde(dto.getSolde());
                    compte.setTypeCompte(Compte.TypeCompte.valueOf(dto.getTypeCompte()));
                    compte.setDateCreation(dto.getDateCreation());
                    return compte;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    @QueryMapping
    public Compte compte(@Argument Long id) {
        return compteService.getCompteById(id)
                .map(dto -> {
                    Compte compte = new Compte();
                    compte.setId(dto.getId());
                    compte.setNumeroCompte(dto.getNumeroCompte());
                    compte.setProprietaire(dto.getProprietaire());
                    compte.setSolde(dto.getSolde());
                    compte.setTypeCompte(Compte.TypeCompte.valueOf(dto.getTypeCompte()));
                    compte.setDateCreation(dto.getDateCreation());
                    return compte;
                })
                .orElse(null);
    }
}

record CompteInput(String numeroCompte, String proprietaire, Float solde, String typeCompte) {}