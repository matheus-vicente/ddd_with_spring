package com.matheus_vicente.javagas.infra.persistence.jpa.repositories.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.matheus_vicente.javagas.domain.entities.vaga.Vaga;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;
import com.matheus_vicente.javagas.domain.shared.Pagina;
import com.matheus_vicente.javagas.domain.shared.Paginavel;
import com.matheus_vicente.javagas.infra.persistence.jpa.entities.JpaVaga;
import com.matheus_vicente.javagas.infra.persistence.jpa.repositories.JpaVagasRepository;

@Repository
public class JpaVagasRepositoryAdapter implements VagasRepository {
    private JpaVagasRepository jpaVagasRepository;

    public JpaVagasRepositoryAdapter(JpaVagasRepository jpaVagasRepository) {
        this.jpaVagasRepository = jpaVagasRepository;
    }

    @Override
    public Optional<Vaga> buscarPorId(UUID id) {
        return this.jpaVagasRepository.findById(id).map(
            JpaVaga::toDomain
        );
    }

    @Override
    public Optional<Vaga> buscarPorCodigo(String codigo) {
        return this.jpaVagasRepository.findByCodigo(codigo).map(
            JpaVaga::toDomain
        );
    }

    @Override
    public Vaga salvar(Vaga vaga) {
        return this.jpaVagasRepository.save(
            JpaVaga.fromDomain(vaga)
        ).toDomain();
    }

    @Override
    public Pagina<Vaga> listar(Paginavel paginavel) {
        Pageable pageable = PageRequest.of(
            paginavel.pagina(), 
            paginavel.tamanhoDaPagina()
        );

        Page<JpaVaga> jpaVagas = this.jpaVagasRepository.findAll(pageable);

        List<Vaga> vagas = jpaVagas.getContent().stream().map(
            JpaVaga::toDomain
        ).toList();

        return new Pagina<>(
            vagas,
            (int) jpaVagas.getTotalElements(),
            paginavel.pagina(),
            paginavel.tamanhoDaPagina()
        );
    }

    @Override
    public void deletar(UUID id) {
        this.jpaVagasRepository.deleteById(id);
    }
}
