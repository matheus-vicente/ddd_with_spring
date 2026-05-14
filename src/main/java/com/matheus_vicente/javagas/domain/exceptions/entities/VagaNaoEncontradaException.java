package com.matheus_vicente.javagas.domain.exceptions.entities;

import com.matheus_vicente.javagas.domain.exceptions.EntidadeNaoEncontradaException;

public class VagaNaoEncontradaException extends EntidadeNaoEncontradaException {
    public VagaNaoEncontradaException() {
        super("Vaga não encontrada");
    }
}
