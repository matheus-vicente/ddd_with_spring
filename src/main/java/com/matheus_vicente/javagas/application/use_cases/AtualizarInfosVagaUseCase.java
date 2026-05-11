package com.matheus_vicente.javagas.application.use_cases;

import java.util.UUID;

import com.matheus_vicente.javagas.domain.entities.TipoVaga;
import com.matheus_vicente.javagas.domain.entities.Vaga;
import com.matheus_vicente.javagas.domain.exceptions.entities.CodigoEmUsoException;
import com.matheus_vicente.javagas.domain.exceptions.entities.VagaNaoEncontradaException;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;
import com.matheus_vicente.javagas.infra.dtos.InfosVagaDTO;

public class AtualizarInfosVagaUseCase {
    private VagasRepository repository;

    public AtualizarInfosVagaUseCase(VagasRepository repository) {
        this.repository = repository;
    }

    public Vaga execute(String id, InfosVagaDTO vagaDTO) {
        boolean vagaComMesmoCodigo = this.repository.buscarPorCodigo(vagaDTO.codigo()).isPresent();

        if (vagaComMesmoCodigo) {
            throw new CodigoEmUsoException(vagaDTO.codigo());
        }

        Vaga vagaParaAtualizar = this.repository.buscarPorId(UUID.fromString(id)).orElseThrow(
            () -> new VagaNaoEncontradaException()
        );

        vagaParaAtualizar.atualizarInfos(
            vagaDTO.codigo(),
            TipoVaga.valueOf(vagaDTO.tipo().toUpperCase())
        );

        Vaga vaga = this.repository.salvar(vagaParaAtualizar);

        return vaga;
    }
}
