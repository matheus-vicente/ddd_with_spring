package com.matheus_vicente.javagas.domain.entities;

import java.util.UUID;

import com.matheus_vicente.javagas.domain.exceptions.DomainException;

public class Vaga {
    private final UUID id;
    private String codigo;
    private TipoVaga tipo;
    private boolean disponivel;

    private Vaga(
        UUID id,
        String codigo,
        TipoVaga tipo,
        boolean disponivel
    ) {
        if (id == null) {
            throw new DomainException("O campo ID não pode ser nulo");
        }

        if (codigo == null || codigo.isEmpty()) {
            throw new DomainException("O campo CÓDIGO não pode ser nulo");
        }

        
        this.id = id;
        this.codigo = codigo;
        this.tipo = (tipo == null) ? TipoVaga.PADRAO : tipo;
        this.disponivel = disponivel;
    }

    public static Vaga create(String codigo, TipoVaga tipo) {
        UUID id = UUID.randomUUID();

        return new Vaga(id, codigo, tipo, true);
    }

    public void atualizarInfos(String codigo, TipoVaga tipo) {
        if (codigo == null || codigo.isEmpty()) {
            throw new DomainException("O campo CÓDIGO não pode ser nulo");
        }

        if (tipo != null) {
            this.tipo = tipo;
        }

        this.codigo = codigo;
    }

    public void ocupar() {
        if (!this.disponivel) {
            throw new DomainException("A vaga já está ocupada");
        }

        this.disponivel = false;
    }

    public void liberar() {
        if (this.disponivel) {
            throw new DomainException("A vaga já está disponível");
        }

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
