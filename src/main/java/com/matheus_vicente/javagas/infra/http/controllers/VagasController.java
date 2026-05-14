package com.matheus_vicente.javagas.infra.http.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.matheus_vicente.javagas.application.dtos.InfosVagaDTO;
import com.matheus_vicente.javagas.application.dtos.VagaResponseDTO;
import com.matheus_vicente.javagas.application.use_cases.AtualizarInfosVagaUseCase;
import com.matheus_vicente.javagas.application.use_cases.CriarVagaUseCase;
import com.matheus_vicente.javagas.application.use_cases.DeletarVagasUseCase;
import com.matheus_vicente.javagas.application.use_cases.ListarVagasUseCase;
import com.matheus_vicente.javagas.domain.entities.vaga.Vaga;
import com.matheus_vicente.javagas.domain.shared.Pagina;
import com.matheus_vicente.javagas.domain.shared.Paginavel;

@RestController
@RequestMapping("v1/vagas")
public class VagasController {
    private CriarVagaUseCase criarVagaUseCase;
    private ListarVagasUseCase listarVagasUseCase;
    private AtualizarInfosVagaUseCase atualizarInfosVagaUseCase;
    private DeletarVagasUseCase deletarVagasUseCase;

    public VagasController(
        CriarVagaUseCase criarVagaUseCase,
        ListarVagasUseCase listarVagasUseCase,
        AtualizarInfosVagaUseCase atualizarInfosVagaUseCase,
        DeletarVagasUseCase deletarVagasUseCase
    ) {
        this.criarVagaUseCase = criarVagaUseCase;
        this.listarVagasUseCase = listarVagasUseCase;
        this.atualizarInfosVagaUseCase = atualizarInfosVagaUseCase;
        this.deletarVagasUseCase = deletarVagasUseCase;
    }

    @PostMapping("")
    public ResponseEntity<VagaResponseDTO> criarVaga(@RequestBody InfosVagaDTO vagaDTO) {
        Vaga vaga = this.criarVagaUseCase.execute(vagaDTO);

        return ResponseEntity.ok(VagaResponseDTO.fromDomain(vaga));
    }

    @GetMapping("")
    public ResponseEntity<Pagina<VagaResponseDTO>> listarVagas(Paginavel paginavel) {
        Pagina<Vaga> vagas = this.listarVagasUseCase.execute(paginavel);

        List<VagaResponseDTO> vagasResponse = vagas.getItens().stream().map(
            VagaResponseDTO::fromDomain
        ).toList();

        return ResponseEntity.ok(
            new Pagina<>(
                vagasResponse,
                vagas.getTotal(),
                vagas.getNumeroDaPagina(),
                vagas.getTamanhoDaPagina()
            )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<VagaResponseDTO> atualizarVaga(@PathVariable String id, @RequestBody InfosVagaDTO vagaDTO) {
        Vaga vaga = this.atualizarInfosVagaUseCase.execute(id, vagaDTO);

        return ResponseEntity.ok(VagaResponseDTO.fromDomain(vaga));
    }

    @DeleteMapping("/{id}")
    public void getMethodName(@PathVariable String id) {
        this.deletarVagasUseCase.execute(id);
    }
}
