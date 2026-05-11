package com.matheus_vicente.javagas.application.use_cases;

import com.matheus_vicente.javagas.domain.entities.Vaga;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;
import com.matheus_vicente.javagas.infra.dtos.CriarVagaDTO;

public class CriarVagaUseCase {
    private VagasRepository repository;

    public CriarVagaUseCase(VagasRepository repository) {
        this.repository = repository;
    }

    public Vaga execute(CriarVagaDTO vagaDTO) {
        return null;
    }
}
