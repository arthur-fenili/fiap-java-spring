package com.example.SinisterBusterDefinitive.controller;

import com.example.SinisterBusterDefinitive.dto.ConsultaResponseDTO;
import com.example.SinisterBusterDefinitive.dto.PacienteRequestDTO;
import com.example.SinisterBusterDefinitive.dto.PacienteResponseDTO;
import com.example.SinisterBusterDefinitive.model.Consulta;
import com.example.SinisterBusterDefinitive.model.Paciente;
import com.example.SinisterBusterDefinitive.repository.ConsultaRepository;
import com.example.SinisterBusterDefinitive.repository.PacienteRepository;
import com.example.SinisterBusterDefinitive.service.ConsultaMapper;
import com.example.SinisterBusterDefinitive.service.PacienteMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/pacientes", produces = "application/json")
@Tag(name = "api-pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private PacienteMapper pacienteMapper;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private ConsultaMapper consultaMapper;


    // Método para inserirPaciente via Procedure
    @PostMapping("/procedure")
    public ResponseEntity<String> inserirPacienteProcedure(@RequestBody Paciente paciente) {
        pacienteMapper.inserirPaciente(paciente);
        return ResponseEntity.ok("Paciente inserido via procedure com sucesso.");
    }

    // Método para atualizarPaciente via Procedure
    @PutMapping("/procedure/{id}")
    public ResponseEntity<String> atualizarPacienteProcedure(@PathVariable Long id, @RequestBody Paciente paciente) {
        paciente.setIdPaciente(id);
        pacienteMapper.atualizarPaciente(paciente);
        return ResponseEntity.ok("Paciente atualizado via procedure com sucesso.");
    }

    // Método para deletarPaciente via Procedure
    @DeleteMapping("/procedure/{id}")
    public ResponseEntity<String> deletarPacienteProcedure(@PathVariable Long id) {
        pacienteMapper.deletarPaciente(id);
        return ResponseEntity.ok("Paciente deletado via procedure com sucesso.");
    }

    // Método original para criar paciente
    @Operation(summary = "Cria um paciente e grava no banco.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Atributos informados são inválidos.",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> createPaciente(@Valid @RequestBody PacienteRequestDTO pacienteRequestDTO) {
        Paciente paciente = pacienteMapper.requestToPaciente(pacienteRequestDTO);
        Paciente pacienteCriado = pacienteRepository.save(paciente);
        PacienteResponseDTO pacienteResponseDTO = pacienteMapper.pacienteToResponse(pacienteCriado);
        return new ResponseEntity<>(pacienteResponseDTO, HttpStatus.CREATED);
    }

    // Método original para buscar todos os pacientes
    @Operation(summary = "Busca todos os pacientes cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado com sucesso."),
            @ApiResponse(responseCode = "204", description = "Nenhum paciente encontrado."),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado.",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PacienteResponseDTO>>> getAllPacientes() {
        Iterable<Paciente> pacientes = pacienteRepository.findAll();

        List<EntityModel<PacienteResponseDTO>> pacientesResources = new ArrayList<>();

        pacientes.forEach(paciente -> {
            PacienteResponseDTO pacienteResponse = pacienteMapper.pacienteToResponse(paciente);

            EntityModel<PacienteResponseDTO> pacienteResource = EntityModel.of(pacienteResponse);
            pacienteResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).getPacienteById(paciente.getIdPaciente())).withSelfRel());
            pacienteResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).updatePaciente(paciente.getIdPaciente(), null)).withRel("editar"));
            pacienteResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).deletePaciente(paciente.getIdPaciente())).withRel("deletar"));

            pacientesResources.add(pacienteResource);
        });

        Link linkToCreatePaciente = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).createPaciente(null)).withRel("criarPaciente");

        CollectionModel<EntityModel<PacienteResponseDTO>> collectionModel = CollectionModel.of(pacientesResources);
        collectionModel.add(linkToCreatePaciente);

        return ResponseEntity.ok(collectionModel);
    }

    // Método original para buscar um paciente específico por ID
    @Operation(summary = "Busca um paciente pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado.",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PacienteResponseDTO>> getPacienteById(@PathVariable Long id) {
        return pacienteRepository.findById(id).map(paciente -> {
            PacienteResponseDTO pacienteResponse = pacienteMapper.pacienteToResponse(paciente);

            EntityModel<PacienteResponseDTO> resource = EntityModel.of(pacienteResponse);
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).getPacienteById(id)).withSelfRel());
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).updatePaciente(id, null)).withRel("editar"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).deletePaciente(id)).withRel("deletar"));
            resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).getConsultasPorPaciente(id)).withRel("consultas"));

            return ResponseEntity.ok(resource);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Método original para listar todas as consultas de um paciente específico
    @Operation(summary = "Busca todas as consultas de um paciente pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultas encontradas com sucesso."),
            @ApiResponse(responseCode = "404", description = "Consultas não encontradas.",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}/consultas")
    public ResponseEntity<List<EntityModel<ConsultaResponseDTO>>> getConsultasPorPaciente(@PathVariable Long id) {
        List<Consulta> consultas = consultaRepository.findByPacienteIdPaciente(id);

        List<EntityModel<ConsultaResponseDTO>> consultaResources = consultas.stream()
                .map(consulta -> {
                    ConsultaResponseDTO consultaResponse = consultaMapper.consultaToResponse(consulta);
                    EntityModel<ConsultaResponseDTO> resource = EntityModel.of(consultaResponse);
                    resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).getConsultasPorPaciente(id)).withSelfRel());
                    return resource;
                }).toList();

        return ResponseEntity.ok(consultaResources);
    }

    // Método original para atualizar um paciente
    @Operation(summary = "Atualiza um paciente pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado.",
                    content = @Content(schema = @Schema()))
    })
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> updatePaciente(@PathVariable Long id, @Valid @RequestBody PacienteRequestDTO pacienteRequestDTO) {
        return pacienteRepository.findById(id).map(paciente -> {
            paciente.setNome(pacienteRequestDTO.nome());
            paciente.setCpf(pacienteRequestDTO.cpf());
            paciente.setDataNascimento(pacienteRequestDTO.dataNascimento());
            paciente.setPlanoSaude(pacienteRequestDTO.planoSaude());
            paciente.setTelefone(pacienteRequestDTO.telefone());
            paciente.setEmail(pacienteRequestDTO.email());
            Paciente pacienteAtualizado = pacienteRepository.save(paciente);
            return ResponseEntity.ok(pacienteMapper.pacienteToResponse(pacienteAtualizado));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Método original para deletar um paciente
    @Operation(summary = "Deleta um paciente pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado.",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaciente(@PathVariable Long id) {
        return pacienteRepository.findById(id).map(paciente -> {
            pacienteRepository.delete(paciente);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
