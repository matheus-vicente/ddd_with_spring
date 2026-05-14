package com.matheus_vicente.javagas.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.matheus_vicente.javagas.domain.entities.ticket.Ticket;

public record TicketResponseDTO(
    String id,
    String codigo,
    String vagaId,
    String placa,
    String tarifa,
    BigDecimal valor,
    LocalDateTime criadoEm,
    LocalDateTime dataDeSaida
) {
    public static TicketResponseDTO fromDomain(Ticket ticket) {
        return new TicketResponseDTO(
            ticket.getId().toString(),
            ticket.getCodigo().codigo(),
            ticket.getVagaId().toString(),
            ticket.getPlaca().placa(),
            ticket.getTarifa().getTipo().name(),
            ticket.getValor(),
            ticket.getCriadoEm(),
            ticket.getDataDeSaida()
        );
    }
}
