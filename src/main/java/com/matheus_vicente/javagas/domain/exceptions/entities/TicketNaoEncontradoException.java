package com.matheus_vicente.javagas.domain.exceptions.entities;

import com.matheus_vicente.javagas.domain.exceptions.EntidadeNaoEncontradaException;

public class TicketNaoEncontradoException extends EntidadeNaoEncontradaException {
    public TicketNaoEncontradoException() {
        super("Ticket não encontrado");
    }  
}
