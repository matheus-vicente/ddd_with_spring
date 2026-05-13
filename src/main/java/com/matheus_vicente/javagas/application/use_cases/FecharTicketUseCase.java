package com.matheus_vicente.javagas.application.use_cases;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import com.matheus_vicente.javagas.domain.entities.ticket.Ticket;
import com.matheus_vicente.javagas.domain.entities.vaga.Vaga;
import com.matheus_vicente.javagas.domain.exceptions.entities.TicketNaoEncontradoException;
import com.matheus_vicente.javagas.domain.exceptions.entities.VagaNaoEncontradaException;
import com.matheus_vicente.javagas.domain.repositories.CalcularTarifa;
import com.matheus_vicente.javagas.domain.repositories.TicketsRepository;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;

public class FecharTicketUseCase {
    private VagasRepository vagasRepository;
    private TicketsRepository ticketsRepository;
    private CalcularTarifa calcularTarifa;
    private final Clock clock;
    
    public FecharTicketUseCase(
        VagasRepository vagasRepository,
        TicketsRepository ticketsRepository,
        CalcularTarifa calcularTarifa,
        Clock clock
    ) {
        this.vagasRepository = vagasRepository;
        this.ticketsRepository = ticketsRepository;
        this.calcularTarifa = calcularTarifa;
        this.clock = clock;
    }

    public Ticket execute(String id) {
        UUID idValido;

        try {
            idValido = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Informe um ID válido");
        }

        Ticket ticket = this.ticketsRepository.buscarPorId(idValido).orElseThrow(
            TicketNaoEncontradoException::new
        );

        Vaga vaga = this.vagasRepository.buscarPorId(ticket.getVagaId()).orElseThrow(
            VagaNaoEncontradaException::new
        );

        LocalDateTime dataDeSaida = LocalDateTime.now(clock);

        BigDecimal valor = calcularTarifa.calcular(ticket, dataDeSaida);

        ticket.pagar(valor, dataDeSaida);
        vaga.liberar();

        this.vagasRepository.salvar(vaga);
        Ticket ticketSalvo = this.ticketsRepository.salvar(ticket);

        return ticketSalvo;
    }
}
