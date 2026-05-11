package com.matheus_vicente.javagas.application.use_cases.vaga;

import java.util.UUID;

import com.matheus_vicente.javagas.domain.entities.Vaga;
import com.matheus_vicente.javagas.domain.exceptions.entities.VagaNaoEncontradaException;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;

public class OcuparVagaUseCase {
    private VagasRepository repository;

    public OcuparVagaUseCase(VagasRepository repository) {
        this.repository = repository;
    }

    public Vaga execute(String id) {
        Vaga vagaParaEditar = this.repository.buscarPorId(UUID.fromString(id)).orElseThrow(
            () -> new VagaNaoEncontradaException()
        );

        vagaParaEditar.ocupar();

        Vaga vaga = this.repository.salvar(vagaParaEditar);

        return vaga;
    }
}
