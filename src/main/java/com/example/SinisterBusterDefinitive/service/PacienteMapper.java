package com.example.SinisterBusterDefinitive.service;

import com.example.SinisterBusterDefinitive.dto.PacienteRequestDTO;
import com.example.SinisterBusterDefinitive.dto.PacienteResponseDTO;
import com.example.SinisterBusterDefinitive.model.Paciente;
import com.example.SinisterBusterDefinitive.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PacienteMapper {

    @Autowired
    private PacienteRepository pacienteRepository;


    // Procedures do banco de dados
    @Transactional
    public void inserirPaciente(Paciente paciente) {
        pacienteRepository.inserirPaciente(
                paciente.getNome(), paciente.getCpf(),
                paciente.getDataNascimento(), paciente.getPlanoSaude(),
                paciente.getTelefone(), paciente.getEmail()
        );
    }

    @Transactional
    public void atualizarPaciente(Paciente paciente) {
        pacienteRepository.atualizarPaciente(
                paciente.getIdPaciente(), paciente.getNome(), paciente.getCpf(),
                paciente.getDataNascimento(), paciente.getPlanoSaude(),
                paciente.getTelefone(), paciente.getEmail()
        );
    }

    @Transactional
    public void deletarPaciente(Long idPaciente) {
        pacienteRepository.deletarPaciente(idPaciente);
    }



    public Paciente requestToPaciente(PacienteRequestDTO pacienteRequestDTO){
        Paciente paciente = new Paciente();
        paciente.setNome(pacienteRequestDTO.nome());
        paciente.setCpf(pacienteRequestDTO.cpf());
        paciente.setDataNascimento(pacienteRequestDTO.dataNascimento());
        paciente.setPlanoSaude(pacienteRequestDTO.planoSaude());
        paciente.setTelefone(pacienteRequestDTO.telefone());
        paciente.setEmail(pacienteRequestDTO.email());
        return paciente;
    }

    public PacienteResponseDTO pacienteToResponse(Paciente paciente){
        return new PacienteResponseDTO(
                paciente.getIdPaciente(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getDataNascimento(),
                paciente.getPlanoSaude(),
                paciente.getTelefone(),
                paciente.getEmail()
        );
    }
}
