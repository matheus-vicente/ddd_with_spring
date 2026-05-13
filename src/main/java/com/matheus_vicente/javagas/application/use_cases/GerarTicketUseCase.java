package com.matheus_vicente.javagas.application.use_cases;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import com.matheus_vicente.javagas.application.dtos.InfosTicketDTO;
import com.matheus_vicente.javagas.domain.entities.ticket.Ticket;
import com.matheus_vicente.javagas.domain.entities.vaga.Vaga;
import com.matheus_vicente.javagas.domain.exceptions.entities.VagaNaoEncontradaException;
import com.matheus_vicente.javagas.domain.repositories.GeradorDeCodigoTicket;
import com.matheus_vicente.javagas.domain.repositories.TicketsRepository;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;

public class GerarTicketUseCase {
    private VagasRepository vagasRepository;
    private TicketsRepository ticketsRepository;
    private GeradorDeCodigoTicket gerador;
    private final Clock clock;
    
    public GerarTicketUseCase(
        VagasRepository vagasRepository,
        TicketsRepository ticketsRepository,
        GeradorDeCodigoTicket gerador,
        Clock clock
    ) {
        this.vagasRepository = vagasRepository;
        this.ticketsRepository = ticketsRepository;
        this.gerador = gerador;
        this.clock = clock;
    }

    public Ticket execute(String vagaId, InfosTicketDTO ticketDTO) {
        UUID vagaIdValido;

        try {
            vagaIdValido = UUID.fromString(vagaId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Informe um ID válido");
        }

        Vaga vaga = this.vagasRepository.buscarPorId(vagaIdValido).orElseThrow(
            () -> new VagaNaoEncontradaException()
        );

        vaga.ocupar();

        Ticket ticket = Ticket.create(
            gerador,
            vaga.getId(),
            ticketDTO.placa(),
            ticketDTO.tipo(),
            ticketDTO.valor(),
            ticketDTO.valorAdicional(),
            LocalDateTime.now(clock)
        );

        this.vagasRepository.salvar(vaga);
        Ticket ticketSalvo = this.ticketsRepository.salvar(ticket);

        return ticketSalvo;
    }
}
