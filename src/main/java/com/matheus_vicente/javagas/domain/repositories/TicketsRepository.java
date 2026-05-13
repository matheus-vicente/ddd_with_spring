package com.matheus_vicente.javagas.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.matheus_vicente.javagas.domain.entities.ticket.Ticket;
import com.matheus_vicente.javagas.domain.shared.Pagina;
import com.matheus_vicente.javagas.domain.shared.Paginavel;

public interface TicketsRepository {
    Pagina<Ticket> listar(Paginavel paginavel);
    Optional<Ticket> buscarPorId(UUID id);
    Optional<Ticket> buscarPorCodigo(String codigo);
    Ticket salvar(Ticket ticket);
}
