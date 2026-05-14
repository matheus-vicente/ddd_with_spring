package com.matheus_vicente.javagas.domain.exceptions.entities;

import com.matheus_vicente.javagas.domain.exceptions.DomainException;

public class CodigoEmUsoException extends DomainException {
    public CodigoEmUsoException(String codigo) {
        super("Código " + codigo + " em uso");
    }
}
