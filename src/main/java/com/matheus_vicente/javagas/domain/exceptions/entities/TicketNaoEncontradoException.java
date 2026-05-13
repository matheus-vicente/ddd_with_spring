package com.matheus_vicente.javagas.domain.exceptions.entities;

public class TicketNaoEncontradoException extends RuntimeException {
    public TicketNaoEncontradoException() {
        super("Ticket não encontrado");
    }  
}
