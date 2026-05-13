package com.matheus_vicente.javagas;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.matheus_vicente.javagas.application.dtos.InfosTicketDTO;
import com.matheus_vicente.javagas.application.dtos.InfosVagaDTO;
import com.matheus_vicente.javagas.application.services.CalcularTarifaImpl;
import com.matheus_vicente.javagas.application.services.GeradorDeCodigoTicketImpl;
import com.matheus_vicente.javagas.application.use_cases.AtualizarInfosVagaUseCase;
import com.matheus_vicente.javagas.application.use_cases.CancelarTicketUseCase;
import com.matheus_vicente.javagas.application.use_cases.CriarVagaUseCase;
import com.matheus_vicente.javagas.application.use_cases.DeletarVagasUseCase;
import com.matheus_vicente.javagas.application.use_cases.FecharTicketUseCase;
import com.matheus_vicente.javagas.application.use_cases.GerarTicketUseCase;
import com.matheus_vicente.javagas.application.use_cases.ListarVagasUseCase;
import com.matheus_vicente.javagas.domain.entities.ticket.StatusTicket;
import com.matheus_vicente.javagas.domain.entities.ticket.Ticket;
import com.matheus_vicente.javagas.domain.entities.vaga.TipoVaga;
import com.matheus_vicente.javagas.domain.entities.vaga.Vaga;
import com.matheus_vicente.javagas.domain.entities.values_objects.tarifa.TipoTarifa;
import com.matheus_vicente.javagas.domain.exceptions.DomainException;
import com.matheus_vicente.javagas.domain.exceptions.UseCaseException;
import com.matheus_vicente.javagas.domain.exceptions.entities.CodigoEmUsoException;
import com.matheus_vicente.javagas.domain.exceptions.entities.VagaNaoEncontradaException;
import com.matheus_vicente.javagas.domain.repositories.CalcularTarifa;
import com.matheus_vicente.javagas.domain.repositories.GeradorDeCodigoTicket;
import com.matheus_vicente.javagas.domain.repositories.TicketsRepository;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;
import com.matheus_vicente.javagas.domain.shared.Paginavel;
import com.matheus_vicente.javagas.repositories.InMemoryTicketsRepository;
import com.matheus_vicente.javagas.repositories.InMemoryVagasRepository;

@SpringBootTest
class UseCasesTestes {
    private TicketsRepository ticketsRepository;    
    private VagasRepository vagasRepository;
    private GeradorDeCodigoTicket gerador;
    private CalcularTarifa calcularTarifa;

    private CriarVagaUseCase criarVagaUseCase;
    private AtualizarInfosVagaUseCase atualizarInfosVagaUseCase;
    private DeletarVagasUseCase deletarVagasUseCase;
    private ListarVagasUseCase listarVagasUseCase;

    private GerarTicketUseCase gerarTicketUseCase;
    private CancelarTicketUseCase cancelarTicketUseCase;
    private FecharTicketUseCase fecharTicketUseCase;

    @BeforeEach
    void init() {
        vagasRepository = new InMemoryVagasRepository();
        ticketsRepository = new InMemoryTicketsRepository();

        gerador = new GeradorDeCodigoTicketImpl();
        calcularTarifa = new CalcularTarifaImpl();

        criarVagaUseCase = new CriarVagaUseCase(vagasRepository);
        atualizarInfosVagaUseCase = new AtualizarInfosVagaUseCase(vagasRepository);
        deletarVagasUseCase = new DeletarVagasUseCase(vagasRepository);
        listarVagasUseCase = new ListarVagasUseCase(vagasRepository);
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

        assertTrue(vagasRepository.buscarPorId(vaga.getId()).isPresent());

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
    @DisplayName("Deve ser possível deletar uma vaga")
    void deletarVaga() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        assertTrue(vagasRepository.buscarPorId(vaga.getId()).isPresent());

        deletarVagasUseCase.execute(vaga.getId().toString());

        assertFalse(vagasRepository.buscarPorId(vaga.getId()).isPresent());
    }

    @Test
    @DisplayName("Não deve ser possível deletar uma vaga com ID inválido")
    void deletarVagaComIdInexistente() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        assertTrue(vagasRepository.buscarPorId(vaga.getId()).isPresent());

        assertThrows(VagaNaoEncontradaException.class, () -> deletarVagasUseCase.execute(UUID.randomUUID().toString()));
    }

    @Test
    @DisplayName("Deve ser possível listar todas as vagas")
    void listarVagas() {
        criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        criarVagaUseCase.execute(
            new InfosVagaDTO("A02", TipoVaga.PADRAO.name())
        );

        criarVagaUseCase.execute(
            new InfosVagaDTO("A03", TipoVaga.PADRAO.name())
        );

        criarVagaUseCase.execute(
            new InfosVagaDTO("A04", TipoVaga.PADRAO.name())
        );

        List<Vaga> vagas = listarVagasUseCase.execute(Paginavel.primeira()).getItens();

        assertEquals(4, vagas.size());
    }

    @Test
    @DisplayName("Deve ser possível gerar um Ticket com Status PENDENTE e ocupar a vaga corespondente ao Ticket")
    void gerarTicket() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        gerarTicketUseCase = new GerarTicketUseCase(
            vagasRepository,
            ticketsRepository,
            gerador,
            Clock.fixed(Instant.now(), ZoneId.systemDefault())
        );

        Ticket ticket = gerarTicketUseCase.execute(
            vaga.getId().toString(),
            new InfosTicketDTO(
                "ABC-1234",
                TipoTarifa.DIARIA,
                new BigDecimal(50),
                null
            )
        );

        assertEquals(StatusTicket.PENDENTE, ticket.getStatus());
        assertFalse(vagasRepository.buscarPorId(vaga.getId()).get().isDisponivel());
    }

    @Test
    @DisplayName("Não deve ser possível gerar um Ticket para uma vaga inválida")
    void gerarTicketParaVagaInvalida() {
        Instant instanteDataDeEntrada = Instant.parse("2026-05-12T12:00:00Z");
        Clock clockDataDeEntrada = Clock.fixed(instanteDataDeEntrada, ZoneId.systemDefault());

        gerarTicketUseCase = new GerarTicketUseCase(
            vagasRepository,
            ticketsRepository,
            gerador,
            clockDataDeEntrada
        );

        assertThrows(IllegalArgumentException.class, () -> gerarTicketUseCase.execute(
            "a123",
            new InfosTicketDTO(
                "ABC-1234",
                TipoTarifa.DIARIA,
                new BigDecimal(50),
                null
            )
        ));

        assertThrows(VagaNaoEncontradaException.class, () -> gerarTicketUseCase.execute(
            UUID.randomUUID().toString(),
            new InfosTicketDTO(
                "ABC-1234",
                TipoTarifa.DIARIA,
                new BigDecimal(50),
                null
            )
        ));
    }

    @Test
    @DisplayName("Não deve ser possível gerar um Ticket para uma vaga que já está ocupada")
    void gerarTicketParaVagaOcupada() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        Instant instanteDataDeEntrada = Instant.parse("2026-05-12T12:00:00Z");
        Clock clockDataDeEntrada = Clock.fixed(instanteDataDeEntrada, ZoneId.systemDefault());

        gerarTicketUseCase = new GerarTicketUseCase(
            vagasRepository,
            ticketsRepository,
            gerador,
            clockDataDeEntrada
        );

        gerarTicketUseCase.execute(
            vaga.getId().toString(),
            new InfosTicketDTO(
                "ABC-1234",
                TipoTarifa.DIARIA,
                new BigDecimal(50),
                null
            )
        );

        assertFalse(vagasRepository.buscarPorId(vaga.getId()).get().isDisponivel());

        assertThrows(DomainException.class, () -> gerarTicketUseCase.execute(
            vaga.getId().toString(),
            new InfosTicketDTO(
                "ABC-2A34",
                TipoTarifa.DIARIA,
                new BigDecimal(50),
                null
            )
        ));
    }

    @Test
    @DisplayName("Não deve ser possível deletar uma vaga que está ocupada")
    void deletarVagaOcupada() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        Instant instanteDataDeEntrada = Instant.parse("2026-05-12T12:00:00Z");
        Clock clockDataDeEntrada = Clock.fixed(instanteDataDeEntrada, ZoneId.systemDefault());

        gerarTicketUseCase = new GerarTicketUseCase(
            vagasRepository,
            ticketsRepository,
            gerador,
            clockDataDeEntrada
        );

        assertTrue(vagasRepository.buscarPorId(vaga.getId()).isPresent());

        gerarTicketUseCase.execute(
            vaga.getId().toString(),
            new InfosTicketDTO(
                "ABC-1234",
                TipoTarifa.DIARIA,
                new BigDecimal(50),
                null
            )
        );

        assertThrows(UseCaseException.class, () -> deletarVagasUseCase.execute(vaga.getId().toString()));
    }

    @Test
    @DisplayName("Deve ser possível cancelar um Ticket")
    void cancelarTicket() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        Instant instanteDataDeEntrada = Instant.parse("2026-05-12T12:00:00Z");
        Clock clockDataDeEntrada = Clock.fixed(instanteDataDeEntrada, ZoneId.systemDefault());

        gerarTicketUseCase = new GerarTicketUseCase(
            vagasRepository,
            ticketsRepository,
            gerador,
            clockDataDeEntrada
        );

        Ticket ticket = gerarTicketUseCase.execute(
            vaga.getId().toString(),
            new InfosTicketDTO(
                "ABC-1234",
                TipoTarifa.DIARIA,
                new BigDecimal(50),
                null
            )
        );

        Instant instanteDataDeCancelamento = Instant.parse("2026-05-12T12:00:00Z");
        Clock clockDataDeCancelamento = Clock.fixed(instanteDataDeCancelamento, ZoneId.systemDefault());

        cancelarTicketUseCase = new CancelarTicketUseCase(
            vagasRepository,
            ticketsRepository,
            clockDataDeCancelamento
        );

        Ticket ticketCancelado = cancelarTicketUseCase.execute(ticket.getId().toString());

        assertEquals(StatusTicket.CANCELADO, ticketCancelado.getStatus());
        assertTrue(vagasRepository.buscarPorId(vaga.getId()).get().isDisponivel());
    }

    @Test
    @DisplayName("Deve ser possível fechar um Ticket onde o status se torna PAGO e desocupar a vaga correspondente")
    void fecharTicket() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        Instant instanteDataDeEntrada = Instant.parse("2026-05-12T12:00:00Z");
        Clock clockDataDeEntrada = Clock.fixed(instanteDataDeEntrada, ZoneId.systemDefault());

        gerarTicketUseCase = new GerarTicketUseCase(
            vagasRepository,
            ticketsRepository,
            gerador,
            clockDataDeEntrada
        );

        Ticket ticket = gerarTicketUseCase.execute(
            vaga.getId().toString(),
            new InfosTicketDTO(
                "ABC-1234",
                TipoTarifa.DIARIA,
                new BigDecimal(50),
                null
            )
        );

        Instant instanteDataDeSaida = Instant.parse("2026-05-12T12:00:00Z");
        Clock clockDataDeSaida = Clock.fixed(instanteDataDeSaida, ZoneId.systemDefault());

        fecharTicketUseCase = new FecharTicketUseCase(
            vagasRepository,
            ticketsRepository,
            calcularTarifa,
            clockDataDeSaida
        );

        Ticket ticketPago = fecharTicketUseCase.execute(ticket.getId().toString());

        assertEquals(StatusTicket.PAGO, ticketPago.getStatus());
        assertTrue(vagasRepository.buscarPorId(vaga.getId()).get().isDisponivel());
    }

    @Test
    @DisplayName("Deve ser possível fechar um Ticket com valor correto, para todas as tarifas")
    void fecharTicketEmDiferentesTarifas() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        assertAll(
            "Tarifa Mensal",
            () -> {
                Instant instanteDataDeEntrada = Instant.parse("2026-05-12T12:00:00Z");
                Clock clockDataDeEntrada = Clock.fixed(instanteDataDeEntrada, ZoneId.systemDefault());

                gerarTicketUseCase = new GerarTicketUseCase(
                    vagasRepository,
                    ticketsRepository,
                    gerador,
                    clockDataDeEntrada
                );

                Ticket ticket = gerarTicketUseCase.execute(
                    vaga.getId().toString(),
                    new InfosTicketDTO(
                        "ABC-1234",
                        TipoTarifa.MENSAL,
                        new BigDecimal(50),
                        null
                    )
                );

                Instant instanteDataDeSaida = Instant.parse("2026-05-12T18:00:00Z");
                Clock clockDataDeSaida = Clock.fixed(instanteDataDeSaida, ZoneId.systemDefault());

                fecharTicketUseCase = new FecharTicketUseCase(
                    vagasRepository,
                    ticketsRepository,
                    calcularTarifa,
                    clockDataDeSaida
                );

                Ticket ticketPago = fecharTicketUseCase.execute(ticket.getId().toString());

                assertEquals(StatusTicket.PAGO, ticketPago.getStatus());
                assertEquals(
                    new BigDecimal(50).setScale(2, RoundingMode.HALF_UP),
                    ticketPago.getValor()
                );
            }
        );

        assertAll(
            "Tarifa Primeira Hora Mais Hora Adicional",
            () -> {
                Instant instanteDataDeEntrada = Instant.parse("2026-05-12T12:00:00Z");
                Clock clockDataDeEntrada = Clock.fixed(instanteDataDeEntrada, ZoneId.systemDefault());

                gerarTicketUseCase = new GerarTicketUseCase(
                    vagasRepository,
                    ticketsRepository,
                    gerador,
                    clockDataDeEntrada
                );

                Ticket ticket = gerarTicketUseCase.execute(
                    vaga.getId().toString(),
                    new InfosTicketDTO(
                        "ABC-1234",
                        TipoTarifa.PRIMEIRA_HORA_MAIS_HORA_ADICIONAL,
                        new BigDecimal(10),
                        new BigDecimal(2)
                    )
                );

                Instant instanteDataDeSaida = Instant.parse("2026-05-12T18:30:00Z");
                Clock clockDataDeSaida = Clock.fixed(instanteDataDeSaida, ZoneId.systemDefault());

                fecharTicketUseCase = new FecharTicketUseCase(
                    vagasRepository,
                    ticketsRepository,
                    calcularTarifa,
                    clockDataDeSaida
                );

                Ticket ticketPago = fecharTicketUseCase.execute(ticket.getId().toString());

                assertEquals(StatusTicket.PAGO, ticketPago.getStatus());
                assertEquals(
                    new BigDecimal(20).setScale(2, RoundingMode.HALF_UP),
                    ticketPago.getValor()
                );
            }
        );

        assertAll(
            "Tarifa Primeira Hora Mais Hora Adicional",
            () -> {
                Instant instanteDataDeEntrada = Instant.parse("2026-05-12T12:00:00Z");
                Clock clockDataDeEntrada = Clock.fixed(instanteDataDeEntrada, ZoneId.systemDefault());

                gerarTicketUseCase = new GerarTicketUseCase(
                    vagasRepository,
                    ticketsRepository,
                    gerador,
                    clockDataDeEntrada
                );

                Ticket ticket = gerarTicketUseCase.execute(
                    vaga.getId().toString(),
                    new InfosTicketDTO(
                        "ABC-1234",
                        TipoTarifa.PRIMEIRA_HORA_MAIS_HORA_ADICIONAL,
                        new BigDecimal(10),
                        new BigDecimal(2)
                    )
                );

                Instant instanteDataDeSaida = Instant.parse("2026-05-12T12:30:00Z");
                Clock clockDataDeSaida = Clock.fixed(instanteDataDeSaida, ZoneId.systemDefault());

                fecharTicketUseCase = new FecharTicketUseCase(
                    vagasRepository,
                    ticketsRepository,
                    calcularTarifa,
                    clockDataDeSaida
                );

                Ticket ticketPago = fecharTicketUseCase.execute(ticket.getId().toString());

                assertEquals(StatusTicket.PAGO, ticketPago.getStatus());
                assertEquals(
                    new BigDecimal(10).setScale(2, RoundingMode.HALF_UP),
                    ticketPago.getValor()
                );
            }
        );

        assertAll(
            "Tarifa gratuita",
            () -> {
                Instant instanteDataDeEntrada = Instant.parse("2026-05-12T12:00:00Z");
                Clock clockDataDeEntrada = Clock.fixed(instanteDataDeEntrada, ZoneId.systemDefault());

                gerarTicketUseCase = new GerarTicketUseCase(
                    vagasRepository,
                    ticketsRepository,
                    gerador,
                    clockDataDeEntrada
                );

                Ticket ticket = gerarTicketUseCase.execute(
                    vaga.getId().toString(),
                    new InfosTicketDTO(
                        "ABC-1234",
                        TipoTarifa.PRIMEIRA_HORA_MAIS_HORA_ADICIONAL,
                        new BigDecimal(10),
                        new BigDecimal(2)
                    )
                );

                Instant instanteDataDeSaida = Instant.parse("2026-05-12T12:14:00Z");
                Clock clockDataDeSaida = Clock.fixed(instanteDataDeSaida, ZoneId.systemDefault());

                fecharTicketUseCase = new FecharTicketUseCase(
                    vagasRepository,
                    ticketsRepository,
                    calcularTarifa,
                    clockDataDeSaida
                );

                Ticket ticketPago = fecharTicketUseCase.execute(ticket.getId().toString());

                assertEquals(StatusTicket.PAGO, ticketPago.getStatus());
                assertEquals(
                    BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP),
                    ticketPago.getValor()
                );
            }
        );
    }

    @Test
    @DisplayName("Não deve ser possível fechar um Ticket que não esteja pendente")
    void fecharTicketPago() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        Instant instanteDataDeEntrada = Instant.parse("2026-05-12T12:00:00Z");
        Clock clockDataDeEntrada = Clock.fixed(instanteDataDeEntrada, ZoneId.systemDefault());

        gerarTicketUseCase = new GerarTicketUseCase(
            vagasRepository,
            ticketsRepository,
            gerador,
            clockDataDeEntrada
        );

        Ticket ticket = gerarTicketUseCase.execute(
            vaga.getId().toString(),
            new InfosTicketDTO(
                "ABC-1234",
                TipoTarifa.MENSAL,
                new BigDecimal(50),
                null
            )
        );

        Instant instanteDataDeSaida = Instant.parse("2026-05-12T18:00:00Z");
        Clock clockDataDeSaida = Clock.fixed(instanteDataDeSaida, ZoneId.systemDefault());

        fecharTicketUseCase = new FecharTicketUseCase(
            vagasRepository,
            ticketsRepository,
            calcularTarifa,
            clockDataDeSaida
        );

        fecharTicketUseCase.execute(ticket.getId().toString());

        assertThrows(DomainException.class, () -> fecharTicketUseCase.execute(ticket.getId().toString()));
    }

    @Test
    @DisplayName("Não deve ser possível cancelar um Ticket que já está pago ou cancelado")
    void cancelarTicketPagoCancelado() {
        Vaga vaga = criarVagaUseCase.execute(
            new InfosVagaDTO("A01", TipoVaga.PADRAO.name())
        );

        Instant instanteDataDeEntrada = Instant.parse("2026-05-12T12:00:00Z");
        Clock clockDataDeEntrada = Clock.fixed(instanteDataDeEntrada, ZoneId.systemDefault());

        gerarTicketUseCase = new GerarTicketUseCase(
            vagasRepository,
            ticketsRepository,
            gerador,
            clockDataDeEntrada
        );

        Instant instanteDataDeCancelamento = Instant.parse("2026-05-12T12:00:00Z");
        Clock clockDataDeCancelamento = Clock.fixed(instanteDataDeCancelamento, ZoneId.systemDefault());

        fecharTicketUseCase = new FecharTicketUseCase(
            vagasRepository,
            ticketsRepository,
            calcularTarifa,
            clockDataDeCancelamento
        );

        cancelarTicketUseCase = new CancelarTicketUseCase(
            vagasRepository,
            ticketsRepository,
            clockDataDeCancelamento
        );

        assertAll("Ticket PAGO", () -> {
            Ticket ticket = gerarTicketUseCase.execute(
                vaga.getId().toString(),
                new InfosTicketDTO(
                    "ABC-1234",
                    TipoTarifa.DIARIA,
                    new BigDecimal(50),
                    null
                )
            );

            Ticket ticketFechado = fecharTicketUseCase.execute(ticket.getId().toString());

            assertThrows(DomainException.class, () -> cancelarTicketUseCase.execute(ticketFechado.getId().toString()));
        });

        assertAll("Ticket CANCELADO", () -> {
            Ticket ticket = gerarTicketUseCase.execute(
                vaga.getId().toString(),
                new InfosTicketDTO(
                    "ABC-1234",
                    TipoTarifa.DIARIA,
                    new BigDecimal(50),
                    null
                )
            );

            Ticket ticketCancelado = cancelarTicketUseCase.execute(ticket.getId().toString());

            assertThrows(DomainException.class, () -> cancelarTicketUseCase.execute(ticketCancelado.getId().toString()));
        });
    }
}
