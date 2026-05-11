package com.matheus_vicente.javagas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.matheus_vicente.javagas.application.use_cases.AtualizarInfosVagaUseCase;
import com.matheus_vicente.javagas.application.use_cases.CriarVagaUseCase;
import com.matheus_vicente.javagas.domain.entities.TipoVaga;
import com.matheus_vicente.javagas.domain.entities.Vaga;
import com.matheus_vicente.javagas.domain.exceptions.entities.CodigoEmUsoException;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;
import com.matheus_vicente.javagas.infra.dtos.InfosVagaDTO;
import com.matheus_vicente.javagas.repositories.InMemoryVagasRepository;

@SpringBootTest
class VagasUseCasesTestes {
    private VagasRepository repository;

    private CriarVagaUseCase criarVagaUseCase;
    private AtualizarInfosVagaUseCase atualizarInfosVagaUseCase;

    @BeforeEach
    void init() {
        repository = new InMemoryVagasRepository();

        criarVagaUseCase = new CriarVagaUseCase(repository);
        atualizarInfosVagaUseCase = new AtualizarInfosVagaUseCase(repository);
    }

	@Test
    @DisplayName("Deve ser possível criar uma vaga")
	void criarVaga() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        assertEquals("A01", vaga.getCodigo());
	}

    @Test
    @DisplayName("Deve retornar uma exceção ao tentar criar uma vaga com mesmo código")
    void criarVagaComMesmoCodigo() {
        criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        assertThrows(CodigoEmUsoException.class, () -> {
            criarVagaUseCase.execute(
                new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
            );
        });
    }

    @Test
    void editarVaga() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        assertEquals("A02", atualizarInfosVagaUseCase.execute(
            vaga.getId().toString(),
            new InfosVagaDTO("A02", TipoVaga.PADRAO.name())
        ).getCodigo());
    }
}
