package com.matheus_vicente.javagas.domain.repositories;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.matheus_vicente.javagas.domain.entities.ticket.Ticket;

public interface CalcularTarifa {
    BigDecimal calcular(Ticket ticket, LocalDateTime saida);
}
