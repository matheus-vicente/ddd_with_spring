package com.matheus_vicente.javagas.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.matheus_vicente.javagas.domain.entities.Vaga;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;

public class InMemoryVagasRepository implements VagasRepository {
    private List<Vaga> vagas = new ArrayList<>();

    @Override
    public Optional<Vaga> buscarPorId(UUID id) {
        Optional<Vaga> vaga = this.vagas.stream().filter(
            (item) -> item.getId().equals(id)
        ).findFirst();

        return vaga;
    }

    @Override
    public Optional<Vaga> buscarPorCodigo(String codigo) {
        Optional<Vaga> vaga = this.vagas.stream().filter(
            (item) -> item.getCodigo().equals(codigo)
        ).findFirst();

        return vaga;
    }

    @Override
    public Vaga salvar(Vaga vaga) {
        this.vagas.removeIf(
            (item) -> item.getId().equals(vaga.getId())
        );

        this.vagas.add(vaga);

        Optional<Vaga> vagaRetorno = this.vagas.stream().filter(
            (item) -> item.getId().equals(vaga.getId())
        ).findFirst();

        return vagaRetorno.get();
    }

    @Override
    public void deletar(UUID id) {
        this.vagas.removeIf(
            (item) -> item.getId().equals(id)
        );
    }
}
