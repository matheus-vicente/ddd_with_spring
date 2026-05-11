package com.matheus_vicente.javagas.domain.exceptions.entities;

public class CodigoEmUsoException extends RuntimeException {
    public CodigoEmUsoException(String codigo) {
        super("Código " + codigo + " em uso");
    }
}
