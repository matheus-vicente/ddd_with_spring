package com.matheus_vicente.javagas.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.matheus_vicente.javagas.domain.entities.ticket.Ticket;
import com.matheus_vicente.javagas.domain.repositories.TicketsRepository;
import com.matheus_vicente.javagas.domain.shared.Pagina;
import com.matheus_vicente.javagas.domain.shared.Paginavel;

public class InMemoryTicketsRepository implements TicketsRepository {
    List<Ticket> tickets = new ArrayList<>();

    @Override
    public Optional<Ticket> buscarPorId(UUID id) {
        Optional<Ticket> ticket = this.tickets.stream().filter(
            (item) -> item.getId().equals(id)
        ).findFirst();

        return ticket;
    }

    @Override
    public Optional<Ticket> buscarPorCodigo(String codigo) {
        Optional<Ticket> ticket = this.tickets.stream().filter(
            (item) -> item.getCodigo().equals(codigo)
        ).findFirst();

        return ticket;
    }

    @Override
    public Pagina<Ticket> listar(Paginavel paginavel) {
        int total = tickets.size();
        int primeiroIndex = paginavel.getOffset();

        if (primeiroIndex >= total) {
            return new Pagina<Ticket>(
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

        List<Ticket> ticketsSublist = tickets.subList(primeiroIndex, ultimoIndex);

        return new Pagina<Ticket>(
            ticketsSublist,
            total,
            paginavel.pagina(),
            paginavel.tamanhoDaPagina()
        );
    }

    @Override
    public Ticket salvar(Ticket ticket) {
        this.tickets.removeIf(
            (item) -> item.getId().equals(ticket.getId())
        );

        this.tickets.add(ticket);

        Optional<Ticket> ticketParaRetornar = this.tickets.stream().filter(
            (item) -> item.getId().equals(ticket.getId())
        ).findFirst();

        return ticketParaRetornar.get();
    }
}
