package com.matheus_vicente.javagas.infra.dtos;

import com.matheus_vicente.javagas.domain.entities.TipoVaga;

public record VagaResponseDTO(
    String id,
    String codigo,
    TipoVaga tipo,
    boolean disponivel
) {}
