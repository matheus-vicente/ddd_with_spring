package com.matheus_vicente.javagas.domain.exceptions.entities;

public class VagaNaoEncontradaException extends RuntimeException {
    public VagaNaoEncontradaException() {
        super("Vaga não encontrada");
    }
}
