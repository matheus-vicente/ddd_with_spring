package com.matheus_vicente.javagas.infra.persistence.jpa.repositories.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.matheus_vicente.javagas.domain.entities.ticket.Ticket;
import com.matheus_vicente.javagas.domain.repositories.TicketsRepository;
import com.matheus_vicente.javagas.domain.shared.Pagina;
import com.matheus_vicente.javagas.domain.shared.Paginavel;
import com.matheus_vicente.javagas.infra.persistence.jpa.entities.JpaTicket;
import com.matheus_vicente.javagas.infra.persistence.jpa.repositories.JpaTicketsRepository;

@Repository
public class JpaTicketsRepositoryAdapter implements TicketsRepository {
    private JpaTicketsRepository jpaTicketsRepository;

    public JpaTicketsRepositoryAdapter(JpaTicketsRepository jpaTicketsRepository) {
        this.jpaTicketsRepository = jpaTicketsRepository;
    }

    @Override
    public Optional<Ticket> buscarPorId(UUID id) {
        return this.jpaTicketsRepository.findById(id).map(
            JpaTicket::toDomain
        );
    }

    @Override
    public Optional<Ticket> buscarPorCodigo(String codigo) {
        return this.jpaTicketsRepository.findByCodigo(codigo).map(
            JpaTicket::toDomain
        );
    }

    @Override
    public Ticket salvar(Ticket ticket) {
        return this.jpaTicketsRepository.save(
            JpaTicket.fromDomain(ticket)
        ).toDomain();
    }

    @Override
    public Pagina<Ticket> listar(Paginavel paginavel) {
        Pageable pageable = PageRequest.of(
            paginavel.pagina(), 
            paginavel.tamanhoDaPagina()
        );

        Page<JpaTicket> jpaTicket = this.jpaTicketsRepository.findAll(pageable);

        List<Ticket> tickets = jpaTicket.getContent().stream().map(
            JpaTicket::toDomain
        ).toList();

        return new Pagina<>(
            tickets,
            jpaTicket.getSize(),
            paginavel.pagina(),
            paginavel.tamanhoDaPagina()
        );
    }
}
