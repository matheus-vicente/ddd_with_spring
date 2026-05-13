package com.matheus_vicente.javagas.application.dtos;

import com.matheus_vicente.javagas.domain.entities.vaga.TipoVaga;

public record VagaResponseDTO(
    String id,
    String codigo,
    TipoVaga tipo,
    boolean disponivel
) {}
