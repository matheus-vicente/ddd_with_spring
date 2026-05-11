package com.matheus_vicente.javagas.application.use_cases.vaga;

import com.matheus_vicente.javagas.domain.entities.Vaga;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;
import com.matheus_vicente.javagas.domain.shared.Pagina;
import com.matheus_vicente.javagas.domain.shared.Paginavel;

public class ListarVagasUseCase {
    private VagasRepository repository;

    public ListarVagasUseCase(VagasRepository repository) {
        this.repository = repository;
    }

    public Pagina<Vaga> execute(Paginavel paginavel) {
        return this.repository.listar(paginavel);
    }
}
