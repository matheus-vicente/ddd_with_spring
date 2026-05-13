package com.matheus_vicente.javagas.infra.persistence.jpa.entities;

import java.util.UUID;

import com.matheus_vicente.javagas.domain.entities.vaga.TipoVaga;
import com.matheus_vicente.javagas.domain.entities.vaga.Vaga;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vagas")
public class JpaVaga {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVaga tipo;

    @Column(nullable = false)
    private boolean disponivel;

    protected JpaVaga() {}

    private JpaVaga(
        UUID id,
        String codigo,
        TipoVaga tipo,
        boolean disponivel
    ) {
        this.id = id;
        this.codigo = codigo;
        this.tipo = tipo;
        this.disponivel = disponivel;
    }

    public static JpaVaga fromDomain(Vaga vaga) {
        return new JpaVaga(
            vaga.getId(),
            vaga.getCodigo(),
            vaga.getTipo(),
            vaga.isDisponivel()
        );
    }

    public Vaga toDomain() {
        return Vaga.rehydrate(
            this.id,
            this.codigo,
            this.tipo,
            this.disponivel
        );
    }

    public UUID getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public TipoVaga getTipo() {
        return tipo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
}
