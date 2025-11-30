package com.example.bankaccount.mapper;

import com.example.bankaccount.dto.CompteDTO;
import com.example.bankaccount.entity.Compte;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CompteMapper {
    CompteMapper INSTANCE = Mappers.getMapper(CompteMapper.class);
    
    CompteDTO toDTO(Compte compte);
    
    Compte toEntity(CompteDTO compteDTO);
}