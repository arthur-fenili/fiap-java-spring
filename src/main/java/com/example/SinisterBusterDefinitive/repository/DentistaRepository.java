package com.example.SinisterBusterDefinitive.repository;

import com.example.SinisterBusterDefinitive.model.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistaRepository extends JpaRepository<Dentista, Long> {

    @Modifying
    @Query(value = "CALL inserir_dentista(:nomeDentista, :cro, :especialidade, :email)", nativeQuery = true)
    void inserirDentista(
            @Param("nomeDentista") String nomeDentista,
            @Param("cro") String cro,
            @Param("especialidade") String especialidade,
            @Param("email") String email
    );

    @Modifying
    @Query(value = "CALL atualizar_dentista(:idDentista, :nomeDentista, :cro, :especialidade, :email)", nativeQuery = true)
    void atualizarDentista(
            @Param("idDentista") Long idDentista,
            @Param("nomeDentista") String nomeDentista,
            @Param("cro") String cro,
            @Param("especialidade") String especialidade,
            @Param("email") String email
    );

    @Modifying
    @Query(value = "CALL deletar_dentista(:idDentista)", nativeQuery = true)
    void deletarDentista(@Param("idDentista") Long idDentista);
}
