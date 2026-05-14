package com.matheus_vicente.javagas.application.use_cases;

import java.util.UUID;

import com.matheus_vicente.javagas.application.dtos.InfosVagaDTO;
import com.matheus_vicente.javagas.domain.entities.vaga.TipoVaga;
import com.matheus_vicente.javagas.domain.entities.vaga.Vaga;
import com.matheus_vicente.javagas.domain.exceptions.entities.CodigoEmUsoException;
import com.matheus_vicente.javagas.domain.exceptions.entities.VagaNaoEncontradaException;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;

public class AtualizarInfosVagaUseCase {
    private VagasRepository repository;

    public AtualizarInfosVagaUseCase(VagasRepository repository) {
        this.repository = repository;
    }

    public Vaga execute(String id, InfosVagaDTO vagaDTO) {
        Vaga vagaParaAtualizar = this.repository.buscarPorId(UUID.fromString(id)).orElseThrow(
            () -> new VagaNaoEncontradaException()
        );

        if (!vagaParaAtualizar.getCodigo().equals(vagaDTO.codigo())) {
            boolean vagaComMesmoCodigo = this.repository.buscarPorCodigo(vagaDTO.codigo()).isPresent();
    
            if (vagaComMesmoCodigo) {
                throw new CodigoEmUsoException(vagaDTO.codigo());
            }
        }

        vagaParaAtualizar.atualizarInfos(
            vagaDTO.codigo(),
            TipoVaga.valueOf(vagaDTO.tipo().toUpperCase())
        );

        Vaga vaga = this.repository.salvar(vagaParaAtualizar);

        return vaga;
    }
}
