package com.example.bankaccount.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "comptes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String numeroCompte;
    
    @Column(nullable = false)
    private String proprietaire;
    
    @Column(nullable = false)
    private BigDecimal solde;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeCompte typeCompte;
    
    @Column(nullable = false)
    private LocalDateTime dateCreation;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
    
    public enum TypeCompte {
        COURANT, EPARGNE, PROFESSIONNEL
    }
}