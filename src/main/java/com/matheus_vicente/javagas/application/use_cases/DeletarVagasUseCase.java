package com.matheus_vicente.javagas.application.use_cases;

import java.util.UUID;

import com.matheus_vicente.javagas.domain.entities.vaga.Vaga;
import com.matheus_vicente.javagas.domain.exceptions.UseCaseException;
import com.matheus_vicente.javagas.domain.exceptions.entities.VagaNaoEncontradaException;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;

public class DeletarVagasUseCase {
    private VagasRepository repository;

    public DeletarVagasUseCase(VagasRepository repository) {
        this.repository = repository;
    }

    public void execute(String id) {
        Vaga vagaParaDeletar = this.repository.buscarPorId(UUID.fromString(id)).orElseThrow(
            () -> new VagaNaoEncontradaException()
        );

        if (!vagaParaDeletar.isDisponivel()) {
            throw new UseCaseException("Não é possível deletar uma vaga ocupada");
        }

        this.repository.deletar(vagaParaDeletar.getId());
    }
}
