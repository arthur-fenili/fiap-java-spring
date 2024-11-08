package com.example.SinisterBusterDefinitive.dto;

import com.example.SinisterBusterDefinitive.model.Plano;


public record PacienteRequestDTO(
        String nome,
        String cpf,
        String dataNascimento,
        Plano planoSaude,
        String telefone,
        String email

) {
    @Override
    public String nome() {
        return nome;
    }

    @Override
    public String cpf() {
        return cpf;
    }

    @Override
    public String dataNascimento() {
        return dataNascimento;
    }

    @Override
    public Plano planoSaude() {
        return planoSaude;
    }

    @Override
    public String telefone() {
        return telefone;
    }

    @Override
    public String email() {
        return email;
    }
}
