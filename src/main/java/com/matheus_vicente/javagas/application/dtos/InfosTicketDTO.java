package com.matheus_vicente.javagas.application.dtos;

import java.math.BigDecimal;

import com.matheus_vicente.javagas.domain.entities.values_objects.tarifa.TipoTarifa;

public record InfosTicketDTO(
    String placa,
    TipoTarifa tipo,
    BigDecimal valor,
    BigDecimal valorAdicional
) {}