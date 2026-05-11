package com.matheus_vicente.javagas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.matheus_vicente.javagas.application.use_cases.AtualizarInfosVagaUseCase;
import com.matheus_vicente.javagas.application.use_cases.CriarVagaUseCase;
import com.matheus_vicente.javagas.application.use_cases.DeletarVagasUseCase;
import com.matheus_vicente.javagas.application.use_cases.LiberarVagaUseCase;
import com.matheus_vicente.javagas.application.use_cases.OcuparVagaUseCase;
import com.matheus_vicente.javagas.domain.entities.TipoVaga;
import com.matheus_vicente.javagas.domain.entities.Vaga;
import com.matheus_vicente.javagas.domain.exceptions.UseCaseException;
import com.matheus_vicente.javagas.domain.exceptions.entities.CodigoEmUsoException;
import com.matheus_vicente.javagas.domain.exceptions.entities.VagaNaoEncontradaException;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;
import com.matheus_vicente.javagas.infra.dtos.InfosVagaDTO;
import com.matheus_vicente.javagas.repositories.InMemoryVagasRepository;

@SpringBootTest
class VagasUseCasesTestes {
    private VagasRepository repository;

    private CriarVagaUseCase criarVagaUseCase;
    private AtualizarInfosVagaUseCase atualizarInfosVagaUseCase;
    private OcuparVagaUseCase ocuparVagaUseCase;
    private LiberarVagaUseCase liberarVagaUseCase;
    private DeletarVagasUseCase deletarVagasUseCase;

    @BeforeEach
    void init() {
        repository = new InMemoryVagasRepository();

        criarVagaUseCase = new CriarVagaUseCase(repository);
        atualizarInfosVagaUseCase = new AtualizarInfosVagaUseCase(repository);
        ocuparVagaUseCase = new OcuparVagaUseCase(repository);
        liberarVagaUseCase = new LiberarVagaUseCase(repository);
        deletarVagasUseCase = new DeletarVagasUseCase(repository);
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
    @DisplayName("Deve ser possível atualizar uma vaga")
    void atualizarVaga() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        assertEquals("A02", atualizarInfosVagaUseCase.execute(
            vaga.getId().toString(),
            new InfosVagaDTO("A02", TipoVaga.PADRAO.name())
        ).getCodigo());
    }

    @Test
    @DisplayName("Não deve ser possível atualizar uma vaga caso não haja um ID válido")
    void atualizarVagaComIdInexistente() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        assertTrue(repository.buscarPorId(vaga.getId()).isPresent());

        assertThrows(VagaNaoEncontradaException.class, () -> atualizarInfosVagaUseCase.execute(
            UUID.randomUUID().toString(),
            new InfosVagaDTO("A02", TipoVaga.PADRAO.name())
        ));
    }

    @Test
    @DisplayName("Não deve ser possível atualizar uma vaga com mesmo nome")
    void atualizarVagaComMesmoNome() {
        criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A02", TipoVaga.PADRAO.name())
        );

        assertThrows(CodigoEmUsoException.class, () -> atualizarInfosVagaUseCase.execute(
            vaga.getId().toString(),
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        ));
    }

    @Test
    @DisplayName("Deve ser possível atualizar a disponibilidade de uma vaga")
    void atualizarDisponibilidade() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        assertFalse(ocuparVagaUseCase.execute(vaga.getId().toString()).isDisponivel());

        assertTrue(liberarVagaUseCase.execute(vaga.getId().toString()).isDisponivel());
    }

    @Test
    @DisplayName("Deve ser possível deletar uma vaga")
    void deletarVaga() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        assertTrue(repository.buscarPorId(vaga.getId()).isPresent());

        deletarVagasUseCase.execute(vaga.getId().toString());

        assertFalse(repository.buscarPorId(vaga.getId()).isPresent());
    }

    @Test
    @DisplayName("Não deve ser possível deletar uma vaga com ID inválido")
    void deletarVagaComIdInexistente() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        assertTrue(repository.buscarPorId(vaga.getId()).isPresent());

        assertThrows(VagaNaoEncontradaException.class, () -> deletarVagasUseCase.execute(UUID.randomUUID().toString()));
    }

    @Test
    @DisplayName("Não deve ser possível deletar uma vaga que está ocupada")
    void deletarVagaOcupada() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        assertTrue(repository.buscarPorId(vaga.getId()).isPresent());

        ocuparVagaUseCase.execute(vaga.getId().toString());

        assertThrows(UseCaseException.class, () -> deletarVagasUseCase.execute(vaga.getId().toString()));
    }
}
