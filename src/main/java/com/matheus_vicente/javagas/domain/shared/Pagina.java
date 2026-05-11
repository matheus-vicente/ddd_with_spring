package com.matheus_vicente.javagas.domain.shared;

import java.util.List;

public class Pagina<T> {
    private final List<T> itens;
    private final int total;
    private final int numeroDaPagina;
    private final int tamanhoDaPagina;

    public Pagina(List<T> itens, int total, int numeroDaPagina, int tamanhoDaPagina) {
        this.itens = itens;
        this.total = total;
        this.numeroDaPagina = numeroDaPagina;
        this.tamanhoDaPagina = tamanhoDaPagina;
    }

    public List<T> getItens() {
        return itens;
    }

    public int getTotal() {
        return total;
    }

    public int getNumeroDaPagina() {
        return numeroDaPagina;
    }

    public int getTamanhoDaPagina() {
        return tamanhoDaPagina;
    }
}
