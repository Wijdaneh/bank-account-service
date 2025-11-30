package com.example.bankaccount.repository;

import com.example.bankaccount.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    Optional<Compte> findByNumeroCompte(String numeroCompte);
    List<Compte> findByProprietaire(String proprietaire);
    List<Compte> findByTypeCompte(Compte.TypeCompte typeCompte);
    boolean existsByNumeroCompte(String numeroCompte);
}

@RepositoryRestResource(path = "comptes", collectionResourceRel = "comptes")
interface CompteRestRepository extends PagingAndSortingRepository<Compte, Long> {
    
    @RestResource(path = "by-proprietaire")
    List<Compte> findByProprietaire(String proprietaire);
}