package com.matheus_vicente.javagas.domain.entities.values_objects.tarifa;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.matheus_vicente.javagas.domain.exceptions.DomainException;

public class Tarifa {
    private TipoTarifa tipo;
    private BigDecimal valor;
    private BigDecimal valorAdicional;

    private Tarifa(TipoTarifa tipo, BigDecimal valor) {
        this.validarValor(valor);

        this.validarTipo(tipo);
        
        this.tipo = tipo;
        this.valor = valor.setScale(2, RoundingMode.HALF_UP);
    }
    
    private Tarifa(TipoTarifa tipo, BigDecimal valor, BigDecimal valorAdicional) {
        this.validarValor(valor);

        this.validarTipo(tipo);

        this.tipo = tipo;
        this.valor = valor.setScale(2, RoundingMode.HALF_UP);
        this.valorAdicional = valorAdicional.setScale(2, RoundingMode.HALF_UP);
    }

    private void validarValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("O valor da tarifa não pode ser negativo ou nulo");
        }
    }
    
    private void validarTipo(TipoTarifa tipo) {
        if (tipo == null) {
            throw new DomainException("O tipo da tarifa não pode ser ou nulo");
        }
    }

    public static Tarifa fromDiaria(BigDecimal valor) {
        return new Tarifa(TipoTarifa.DIARIA, valor);
    }

    public static Tarifa fromMensal(BigDecimal valor) {
        return new Tarifa(TipoTarifa.MENSAL, valor);
    }

    public static Tarifa fromHoraAdicional(BigDecimal valor, BigDecimal valorAdicional) {
        if (valor == null || valorAdicional.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("O valor adicional deve ser informado para este tipo de tarifa");
        }

        return new Tarifa(TipoTarifa.PRIMEIRA_HORA_MAIS_HORA_ADICIONAL, valor, valorAdicional);
    }

    public TipoTarifa getTipo() {
        return tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getValorAdicional() {
        if (!tipo.equals(TipoTarifa.PRIMEIRA_HORA_MAIS_HORA_ADICIONAL)) {
            throw new DomainException("Tarifa precisa ser do tipo: " + TipoTarifa.PRIMEIRA_HORA_MAIS_HORA_ADICIONAL.name());
        }

        return valorAdicional;
    }
}
