package com.example.bankaccount.controller;

import com.example.bankaccount.dto.CompteDTO;
import com.example.bankaccount.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/comptes")
public class CompteControllerV2 {

    @Autowired
    private CompteService compteService;

    @GetMapping
    public List<CompteDTO> getAllComptes() {
        return compteService.getAllComptes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompteDTO> getCompteById(@PathVariable Long id) {
        return compteService.getCompteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CompteDTO createCompte(@RequestBody CompteDTO compteDTO) {
        return compteService.createCompte(compteDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompteDTO> updateCompte(@PathVariable Long id, @RequestBody CompteDTO compteDTO) {
        return compteService.updateCompte(id, compteDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        return compteService.deleteCompte(id) ? 
                ResponseEntity.ok().build() : 
                ResponseEntity.notFound().build();
    }
}