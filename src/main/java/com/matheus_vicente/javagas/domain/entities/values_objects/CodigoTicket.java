package com.matheus_vicente.javagas.domain.entities.values_objects;

import com.matheus_vicente.javagas.domain.exceptions.DomainException;

public record CodigoTicket(String codigo) {
    public CodigoTicket {
        if (codigo == null || codigo.isBlank()) {
            throw new DomainException("O código do ticket não pode ser vazio.");
        }
    }

    public boolean isEmpty() {
        return codigo.isEmpty();
    }

    public boolean equals(String anString) {
        return codigo.equals(anString);
    }
}