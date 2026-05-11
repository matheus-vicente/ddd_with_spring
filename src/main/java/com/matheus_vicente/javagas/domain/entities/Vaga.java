package com.matheus_vicente.javagas.domain.entities;

import java.util.UUID;

public class Vaga {
    private UUID id;
    private String codigo;
    private TipoVaga tipo;
    private boolean disponivel;

    private Vaga(
        UUID id,
        String codigo,
        TipoVaga tipo,
        boolean disponivel
    ) {
        this.id = id;
        this.codigo = codigo;
        this.tipo = tipo;
        this.disponivel = disponivel;
    }

    public static Vaga create(String codigo, TipoVaga tipo) {
        UUID id = UUID.randomUUID();

        return new Vaga(id, codigo, tipo, true);
    }

    public void ocupar() {
        this.disponivel = false;
    }

    public void liberar() {
        this.disponivel = true;
    }

    public UUID getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public TipoVaga getTipo() {
        return tipo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
}
