package com.example.SinisterBusterDefinitive.dto;

public record DentistaResponseDTO(
        Long idDentista,
        String nomeDentista,
        String cro,
        String especialidade,
        String email
) {}
