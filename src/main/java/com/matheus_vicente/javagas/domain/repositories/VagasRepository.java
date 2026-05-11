package com.matheus_vicente.javagas.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.matheus_vicente.javagas.domain.entities.Vaga;

public interface VagasRepository {
    Optional<Vaga> buscarPorId(UUID id);
    Optional<Vaga> buscarPorCodigo(String codigo);
    Vaga salvar(Vaga vaga);
    Optional<Vaga> editar(Vaga vaga);
    void deletar(UUID id);
}
