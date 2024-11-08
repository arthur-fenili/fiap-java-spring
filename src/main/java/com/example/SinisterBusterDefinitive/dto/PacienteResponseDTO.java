package com.example.SinisterBusterDefinitive.dto;

import com.example.SinisterBusterDefinitive.model.Plano;

public record PacienteResponseDTO(
        Long idPaciente,
        String nome,
        String cpf,
        String dataNascimento,
        Plano planoSaude,
        String telefone,
        String email
) {}
