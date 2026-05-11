package com.matheus_vicente.javagas.domain.repositories;

import java.util.UUID;

import com.matheus_vicente.javagas.domain.entities.Vaga;

public interface VagasRepository {
    Vaga buscarPorId(UUID id);
    Vaga buscarPorCodigo(String codigo);
    Vaga salvar(Vaga vaga);
    Vaga editar(Vaga vaga);
    void deletar(UUID id);
}
