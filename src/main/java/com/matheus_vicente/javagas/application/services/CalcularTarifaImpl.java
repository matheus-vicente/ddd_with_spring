package com.matheus_vicente.javagas.application.services;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.matheus_vicente.javagas.domain.entities.ticket.Ticket;
import com.matheus_vicente.javagas.domain.entities.values_objects.tarifa.Tarifa;
import com.matheus_vicente.javagas.domain.repositories.CalcularTarifa;

@Service
public class CalcularTarifaImpl implements CalcularTarifa {
    @Override
    public BigDecimal calcular(Ticket ticket, LocalDateTime saida) {
        Duration permanencia = Duration.between(ticket.getCriadoEm(), saida);

        Tarifa tarifa = ticket.getTarifa();

        return switch (tarifa.getTipo()) {
            case DIARIA -> tarifa.getValor();
            case MENSAL -> tarifa.getValor();
            case PRIMEIRA_HORA_MAIS_HORA_ADICIONAL -> calcularPorHora(
                permanencia,
                tarifa.getValor(),
                tarifa.getValorAdicional()
            );
        };
    }

    private BigDecimal calcularPorHora(Duration permanencia, BigDecimal valorPrimeiraHora, BigDecimal valorAdicional) {
        int horas = permanencia.toHoursPart();

        if (horas < 1) {
            int minutos = permanencia.toMinutesPart();

            if (minutos < 15) {
                return BigDecimal.ZERO;
            }

            return valorPrimeiraHora;
        }

        return valorPrimeiraHora.add(
            BigDecimal.valueOf(horas - 1).multiply(valorAdicional)
        );
    }
}
