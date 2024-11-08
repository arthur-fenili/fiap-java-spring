package com.example.SinisterBusterDefinitive.controller;

import com.example.SinisterBusterDefinitive.dto.ConsultaResponseDTO;
import com.example.SinisterBusterDefinitive.dto.DentistaRequestDTO;
import com.example.SinisterBusterDefinitive.dto.DentistaResponseDTO;
import com.example.SinisterBusterDefinitive.model.Consulta;
import com.example.SinisterBusterDefinitive.model.Dentista;
import com.example.SinisterBusterDefinitive.repository.ConsultaRepository;
import com.example.SinisterBusterDefinitive.repository.DentistaRepository;
import com.example.SinisterBusterDefinitive.service.ConsultaMapper;
import com.example.SinisterBusterDefinitive.service.DentistaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/dentistas", produces = "application/json")
public class DentistaController {
    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private DentistaMapper dentistaMapper;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaMapper consultaMapper;

    // Método para inserir dentista via Procedure
    @PostMapping("/procedure")
    public ResponseEntity<String> inserirDentistaProcedure(@RequestBody Dentista dentista) {
        dentistaMapper.inserirDentista(dentista);
        return ResponseEntity.ok("Dentista inserido via procedure com sucesso.");
    }

    // Método para atualizar dentista via Procedure
    @PutMapping("/procedure/{id}")
    public ResponseEntity<String> atualizarDentistaProcedure(@PathVariable Long id, @RequestBody Dentista dentista) {
        dentista.setIdDentista(id);
        dentistaMapper.atualizarDentista(dentista);
        return ResponseEntity.ok("Dentista atualizado via procedure com sucesso.");
    }

    // Método para deletar dentista via Procedure
    @DeleteMapping("/procedure/{id}")
    public ResponseEntity<String> deletarDentistaProcedure(@PathVariable Long id) {
        dentistaMapper.deletarDentista(id);
        return ResponseEntity.ok("Dentista deletado via procedure com sucesso.");
    }

    // Método original para criar dentista
    @Operation(summary = "Cria um dentista e grava no banco.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dentista cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Atributos informados são inválidos.",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<DentistaResponseDTO> createDentista(@Valid @RequestBody DentistaRequestDTO dentistaRequestDTO) {
        Dentista dentista = dentistaMapper.requestToDentista(dentistaRequestDTO);
        Dentista dentistaCriado = dentistaRepository.save(dentista);
        DentistaResponseDTO dentistaResponseDTO = dentistaMapper.dentistaToResponse(dentistaCriado);
        return new ResponseEntity<>(dentistaResponseDTO, HttpStatus.CREATED);
    }

    // Método original para listar todos os dentistas
    @Operation(summary = "Lista todos os dentistas do banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dentistas encontrados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhum dentista encontrado.",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping
    public ResponseEntity<List<DentistaResponseDTO>> readDentistas() {
        Iterable<Dentista> dentistas = dentistaRepository.findAll();
        List<DentistaResponseDTO> dentistasResponse = new ArrayList<>();
        dentistas.forEach(dentista -> dentistasResponse.add(dentistaMapper.dentistaToResponse(dentista)));
        return new ResponseEntity<>(dentistasResponse, HttpStatus.OK);
    }

    // Método original para buscar um dentista específico por ID
    @Operation(summary = "Busca um dentista pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dentista encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhum dentista encontrado com esse ID.",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public ResponseEntity<DentistaResponseDTO> getDentistaById(@PathVariable Long id) {
        return dentistaRepository.findById(id)
                .map(dentista -> ResponseEntity.ok(dentistaMapper.dentistaToResponse(dentista)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Método original para buscar todas as consultas de um dentista específico
    @Operation(summary = "Busca todas as consultas associadas a um dentista específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultas encontradas com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhuma consulta encontrada para esse dentista.",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}/consultas")
    public ResponseEntity<List<ConsultaResponseDTO>> getConsultasPorDentista(@PathVariable Long id) {
        List<Consulta> consultas = consultaRepository.findByDentistaIdDentista(id);
        List<ConsultaResponseDTO> consultasResponse = consultas.stream()
                .map(consultaMapper::consultaToResponse)
                .toList();
        return ResponseEntity.ok(consultasResponse);
    }

    // Método original para atualizar um dentista
    @Operation(summary = "Atualiza informações de um dentista.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dentista atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Dentista não encontrado.",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/{id}")
    public ResponseEntity<DentistaResponseDTO> updateDentista(@PathVariable Long id, @Valid @RequestBody DentistaRequestDTO dentistaRequestDTO) {
        return dentistaRepository.findById(id).map(dentista -> {
            dentista.setNomeDentista(dentistaRequestDTO.nomeDentista());
            dentista.setCro(dentistaRequestDTO.cro());
            dentista.setEspecialidade(dentistaRequestDTO.especialidade());
            Dentista dentistaAtualizado = dentistaRepository.save(dentista);
            return ResponseEntity.ok(dentistaMapper.dentistaToResponse(dentistaAtualizado));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Método original para deletar um dentista
    @Operation(summary = "Deleta um dentista pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dentista deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Dentista não encontrado.",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDentista(@PathVariable Long id) {
        return dentistaRepository.findById(id).map(dentista -> {
            dentistaRepository.delete(dentista);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
