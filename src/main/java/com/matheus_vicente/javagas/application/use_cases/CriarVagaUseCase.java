package com.matheus_vicente.javagas.application.use_cases;

import com.matheus_vicente.javagas.application.dtos.InfosVagaDTO;
import com.matheus_vicente.javagas.domain.entities.vaga.TipoVaga;
import com.matheus_vicente.javagas.domain.entities.vaga.Vaga;
import com.matheus_vicente.javagas.domain.exceptions.entities.CodigoEmUsoException;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;

public class CriarVagaUseCase {
    private VagasRepository repository;

    public CriarVagaUseCase(VagasRepository repository) {
        this.repository = repository;
    }

    public Vaga execute(InfosVagaDTO vagaDTO) {
        boolean vagaComMesmoCodigo = this.repository.buscarPorCodigo(vagaDTO.codigo()).isPresent();

        if (vagaComMesmoCodigo) {
            throw new CodigoEmUsoException(vagaDTO.codigo());
        }

        Vaga vaga = Vaga.create(
            vagaDTO.codigo(),
            TipoVaga.valueOf(
                vagaDTO.tipo().toUpperCase()
            )
        );

        Vaga vagaRetorno = repository.salvar(vaga);

        return vagaRetorno;
    }
}
