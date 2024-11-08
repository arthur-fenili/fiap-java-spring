package com.example.SinisterBusterDefinitive.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "CH_DENTISTAS")
public class Dentista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDentista;

    @Column(name = "nome_dentista")
    private String nomeDentista;

    @Column(name = "cro")
    private String cro;

    @Column(name = "especialidade")
    private String especialidade;

    @Column(name = "email")
    private String email;


}


