package com.matheus_vicente.javagas.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.matheus_vicente.javagas.domain.entities.Vaga;
import com.matheus_vicente.javagas.domain.shared.Pagina;
import com.matheus_vicente.javagas.domain.shared.Paginavel;

public interface VagasRepository {
    Pagina<Vaga> listar(Paginavel paginavel);
    Optional<Vaga> buscarPorId(UUID id);
    Optional<Vaga> buscarPorCodigo(String codigo);
    Vaga salvar(Vaga vaga);
    void deletar(UUID id);
}
