package com.example.bankaccount.projection;

import com.example.bankaccount.entity.Compte;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "compteSummary", types = Compte.class)
public interface CompteSummary {
    String getNumeroCompte();
    String getProprietaire();
    Compte.TypeCompte getTypeCompte();
}