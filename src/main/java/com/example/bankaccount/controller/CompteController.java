package com.example.bankaccount.controller;

import com.example.bankaccount.entity.Compte;
import com.example.bankaccount.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    @Autowired
    private CompteRepository compteRepository;

    @GetMapping
    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id) {
        Optional<Compte> compte = compteRepository.findById(id);
        return compte.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/numero/{numero}")
    public ResponseEntity<Compte> getCompteByNumero(@PathVariable String numero) {
        Optional<Compte> compte = compteRepository.findByNumeroCompte(numero);
        return compte.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Compte createCompte(@RequestBody Compte compte) {
        return compteRepository.save(compte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compte> updateCompte(@PathVariable Long id, @RequestBody Compte compteDetails) {
        Optional<Compte> optionalCompte = compteRepository.findById(id);
        if (optionalCompte.isPresent()) {
            Compte compte = optionalCompte.get();
            compte.setProprietaire(compteDetails.getProprietaire());
            compte.setSolde(compteDetails.getSolde());
            compte.setTypeCompte(compteDetails.getTypeCompte());
            return ResponseEntity.ok(compteRepository.save(compte));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        if (compteRepository.existsById(id)) {
            compteRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/depot")
    public ResponseEntity<Compte> faireDepot(@PathVariable Long id, @RequestParam BigDecimal montant) {
        Optional<Compte> optionalCompte = compteRepository.findById(id);
        if (optionalCompte.isPresent() && montant.compareTo(BigDecimal.ZERO) > 0) {
            Compte compte = optionalCompte.get();
            compte.setSolde(compte.getSolde().add(montant));
            return ResponseEntity.ok(compteRepository.save(compte));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/retrait")
    public ResponseEntity<Compte> faireRetrait(@PathVariable Long id, @RequestParam BigDecimal montant) {
        Optional<Compte> optionalCompte = compteRepository.findById(id);
        if (optionalCompte.isPresent() && montant.compareTo(BigDecimal.ZERO) > 0) {
            Compte compte = optionalCompte.get();
            if (compte.getSolde().compareTo(montant) >= 0) {
                compte.setSolde(compte.getSolde().subtract(montant));
                return ResponseEntity.ok(compteRepository.save(compte));
            }
        }
        return ResponseEntity.badRequest().build();
    }
}