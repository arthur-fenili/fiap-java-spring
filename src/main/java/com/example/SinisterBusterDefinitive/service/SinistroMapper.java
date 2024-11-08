package com.example.SinisterBusterDefinitive.service;

import com.example.SinisterBusterDefinitive.dto.DentistaResponseDTO;
import com.example.SinisterBusterDefinitive.dto.PacienteResponseDTO;
import com.example.SinisterBusterDefinitive.dto.SinistroRequestDTO;
import com.example.SinisterBusterDefinitive.dto.SinistroResponseDTO;
import com.example.SinisterBusterDefinitive.model.Dentista;
import com.example.SinisterBusterDefinitive.model.Paciente;
import com.example.SinisterBusterDefinitive.model.Sinistro;
import org.springframework.stereotype.Service;

@Service
public class SinistroMapper {

    // Converter de SinistroRequestDTO para Sinistro (entidade)
    public Sinistro requestToSinistro(SinistroRequestDTO sinistroRequestDTO, Dentista dentista, Paciente paciente) {
        Sinistro sinistro = new Sinistro();
        sinistro.setTipoSinistro(sinistroRequestDTO.tipoSinistro());
        sinistro.setValorSinistro(sinistroRequestDTO.valorSinistro());
        sinistro.setDataSinistro(sinistroRequestDTO.dataSinistro());
        sinistro.setDentista(dentista);
        sinistro.setPaciente(paciente);
        return sinistro;
    }

    // Converter Sinistro (entidade) para SinistroResponseDTO
    public SinistroResponseDTO sinistroToResponse(Sinistro sinistro) {
        return new SinistroResponseDTO(
                sinistro.getIdSinistro(),
                sinistro.getTipoSinistro(),
                sinistro.getValorSinistro(),
                sinistro.getDataSinistro(),
                new DentistaResponseDTO(
                        sinistro.getDentista().getIdDentista(),
                        sinistro.getDentista().getNomeDentista(),
                        sinistro.getDentista().getCro(),
                        sinistro.getDentista().getEspecialidade(),
                        sinistro.getDentista().getEmail()
                ),
                new PacienteResponseDTO(
                        sinistro.getPaciente().getIdPaciente(),
                        sinistro.getPaciente().getNome(),
                        sinistro.getPaciente().getCpf(),
                        sinistro.getPaciente().getDataNascimento(),
                        sinistro.getPaciente().getPlanoSaude(),
                        sinistro.getPaciente().getTelefone(),
                        sinistro.getPaciente().getEmail()
                )
        );
    }
}
