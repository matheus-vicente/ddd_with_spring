package com.matheus_vicente.javagas.domain.exceptions;

public class CodigoEmUsoException extends RuntimeException {
    public CodigoEmUsoException(String codigo) {
        super("Código " + codigo + " em uso");
    }
}
