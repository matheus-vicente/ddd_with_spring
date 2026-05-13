package com.matheus_vicente.javagas.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.matheus_vicente.javagas.domain.entities.vaga.Vaga;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;
import com.matheus_vicente.javagas.domain.shared.Pagina;
import com.matheus_vicente.javagas.domain.shared.Paginavel;

public class InMemoryVagasRepository implements VagasRepository {
    private List<Vaga> vagas = new ArrayList<>();

    @Override
    public Pagina<Vaga> listar(Paginavel paginavel) {
        int total = vagas.size();
        int primeiroIndex = paginavel.getOffset();

        if (primeiroIndex >= total) {
            return new Pagina<Vaga>(
                new ArrayList<>(),
                0,
                paginavel.pagina(),
                paginavel.tamanhoDaPagina()
            );
        }

        int ultimoIndex = 0;

        if (total < paginavel.tamanhoDaPagina()) {
            ultimoIndex = total;
        } else {
            ultimoIndex = Math.min(primeiroIndex + paginavel.tamanhoDaPagina(), primeiroIndex);
        }

        List<Vaga> vagasSublist = vagas.subList(primeiroIndex, ultimoIndex);

        return new Pagina<Vaga>(
            vagasSublist,
            total,
            paginavel.pagina(),
            paginavel.tamanhoDaPagina()
        );
    }

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
