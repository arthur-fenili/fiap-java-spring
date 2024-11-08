package com.example.SinisterBusterDefinitive.service;

import com.example.SinisterBusterDefinitive.dto.ConsultaRequestDTO;
import com.example.SinisterBusterDefinitive.dto.ConsultaResponseDTO;
import com.example.SinisterBusterDefinitive.dto.DentistaResponseDTO;
import com.example.SinisterBusterDefinitive.dto.PacienteResponseDTO;
import com.example.SinisterBusterDefinitive.model.Consulta;
import com.example.SinisterBusterDefinitive.model.Paciente;
import com.example.SinisterBusterDefinitive.model.Dentista;
import org.springframework.stereotype.Service;

@Service
public class ConsultaMapper {

    // Converter de ConsultaRequestDTO para Consulta (entidade)
    public Consulta requestToConsulta(ConsultaRequestDTO dto, Paciente paciente, Dentista dentista) {
        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setDentista(dentista);
        consulta.setDataConsulta(dto.dataConsulta());
        consulta.setCustoConsulta(dto.custoConsulta());
        return consulta;
    }

    // Converter de Consulta (entidade) para ConsultaResponseDTO
    public ConsultaResponseDTO consultaToResponse(Consulta consulta) {
        PacienteResponseDTO pacienteDTO = new PacienteResponseDTO(
                consulta.getPaciente().getIdPaciente(),
                consulta.getPaciente().getNome(),
                consulta.getPaciente().getCpf(),
                consulta.getPaciente().getDataNascimento(),
                consulta.getPaciente().getPlanoSaude(),
                consulta.getPaciente().getTelefone(),
                consulta.getPaciente().getEmail()
        );

        DentistaResponseDTO dentistaDTO = new DentistaResponseDTO(
                consulta.getDentista().getIdDentista(),
                consulta.getDentista().getNomeDentista(),
                consulta.getDentista().getCro(),
                consulta.getDentista().getEspecialidade(),
                consulta.getDentista().getEmail()
        );

        return new ConsultaResponseDTO(
                consulta.getIdConsulta(),
                pacienteDTO,
                dentistaDTO,
                consulta.getDataConsulta(),
                consulta.getCustoConsulta()
        );
    }
}
