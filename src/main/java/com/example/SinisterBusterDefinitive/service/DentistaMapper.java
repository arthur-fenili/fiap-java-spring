package com.example.SinisterBusterDefinitive.service;

import com.example.SinisterBusterDefinitive.dto.DentistaRequestDTO;
import com.example.SinisterBusterDefinitive.dto.DentistaResponseDTO;
import com.example.SinisterBusterDefinitive.model.Dentista;
import com.example.SinisterBusterDefinitive.repository.DentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DentistaMapper {

    @Autowired
    private DentistaRepository dentistaRepository;

    @Transactional
    public void inserirDentista(Dentista dentista) {
        dentistaRepository.inserirDentista(
                dentista.getNomeDentista(), dentista.getCro(),
                dentista.getEspecialidade(), dentista.getEmail()
        );
    }

    @Transactional
    public void atualizarDentista(Dentista dentista) {
        dentistaRepository.atualizarDentista(
                dentista.getIdDentista(), dentista.getNomeDentista(), dentista.getCro(),
                dentista.getEspecialidade(), dentista.getEmail()
        );
    }

    @Transactional
    public void deletarDentista(Long idDentista) {
        dentistaRepository.deletarDentista(idDentista);
    }

    public Dentista requestToDentista(DentistaRequestDTO dentistaRequestDTO) {
        Dentista dentista = new Dentista();
        dentista.setNomeDentista(dentistaRequestDTO.nomeDentista());
        dentista.setCro(dentistaRequestDTO.cro());
        dentista.setEspecialidade(dentistaRequestDTO.especialidade());
        dentista.setEmail(dentistaRequestDTO.email());
        return dentista;
    }

    public DentistaResponseDTO dentistaToResponse(Dentista dentista) {
        return new DentistaResponseDTO(
            dentista.getIdDentista(),
            dentista.getNomeDentista(),
            dentista.getCro(),
            dentista.getEspecialidade(),
            dentista.getEmail()
        );
    }
}
