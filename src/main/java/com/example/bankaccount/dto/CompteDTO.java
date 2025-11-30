package com.example.bankaccount.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CompteDTO {
    private Long id;
    private String numeroCompte;
    private String proprietaire;
    private BigDecimal solde;
    private String typeCompte;
    private LocalDateTime dateCreation;
}