package com.matheus_vicente.javagas.repositories;

import java.util.UUID;

import com.matheus_vicente.javagas.domain.entities.Vaga;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;

public class InMemoryVagasRepository implements VagasRepository {
    @Override
    public Vaga buscarPorId(UUID id) {
        return null;
    }

    @Override
    public Vaga buscarPorCodigo(String codigo) {
        return null;
    }

    @Override
    public Vaga salvar(Vaga vaga) {
        return null;
    }

    @Override
    public Vaga editar(Vaga vaga) {
        return null;
    }

    @Override
    public void deletar(UUID id) {
    }
}
