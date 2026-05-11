package com.matheus_vicente.javagas.domain.shared;

import com.matheus_vicente.javagas.domain.exceptions.DomainException;

public record Paginavel(
    int pagina,
    int tamanhoDaPagina
) {
    public Paginavel {
        if (pagina < 0) {
            throw new DomainException("O número da página deve ser maior que 0");
        }

        if (tamanhoDaPagina < 0) {
            throw new DomainException("O tamanho da página deve ser maior que 0");
        }

        if (tamanhoDaPagina > 100) {
            throw new DomainException("O tamanho máximo da página é 100");
        }
    }

    public static Paginavel primeira() {
        return new Paginavel(0, 10);
    }

    public int getOffset() {
        return this.pagina * this.tamanhoDaPagina;
    }
}
