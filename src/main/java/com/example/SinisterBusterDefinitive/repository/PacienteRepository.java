package com.example.SinisterBusterDefinitive.repository;

import com.example.SinisterBusterDefinitive.model.Paciente;
import com.example.SinisterBusterDefinitive.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Modifying
    @Query(value = "CALL inserir_paciente(:nome, :cpf, :dataNascimento, :planoSaude, :telefone, :email)", nativeQuery = true)
    void inserirPaciente(
            @Param("nome") String nome,
            @Param("cpf") String cpf,
            @Param("dataNascimento") String dataNascimento,
            @Param("planoSaude") Plano planoSaude,
            @Param("telefone") String telefone,
            @Param("email") String email
    );

    @Modifying
    @Query(value = "CALL atualizar_paciente(:idPaciente, :nome, :cpf, :dataNascimento, :planoSaude, :telefone, :email)", nativeQuery = true)
    void atualizarPaciente(
            @Param("idPaciente") Long idPaciente,
            @Param("nome") String nome,
            @Param("cpf") String cpf,
            @Param("dataNascimento") String dataNascimento,
            @Param("planoSaude") Plano planoSaude,
            @Param("telefone") String telefone,
            @Param("email") String email
    );

    @Modifying
    @Query(value = "CALL deletar_paciente(:idPaciente)", nativeQuery = true)
    void deletarPaciente(@Param("idPaciente") Long idPaciente);
}

