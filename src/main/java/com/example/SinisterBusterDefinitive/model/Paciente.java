package com.example.SinisterBusterDefinitive.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "CH_PACIENTES")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaciente;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "data_nascimento")
    private String dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "plano_saude")
    private Plano planoSaude;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

}