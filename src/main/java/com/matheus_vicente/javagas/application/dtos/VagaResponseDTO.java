package com.matheus_vicente.javagas.application.dtos;

import com.matheus_vicente.javagas.domain.entities.vaga.Vaga;

public record VagaResponseDTO(
    String id,
    String codigo,
    String tipo,
    boolean disponivel
) {
    public static VagaResponseDTO fromDomain(Vaga vaga) {
        return new VagaResponseDTO(
            vaga.getId().toString(),
            vaga.getCodigo(),
            vaga.getTipo().name(),
            vaga.isDisponivel()
        );
    }
}
