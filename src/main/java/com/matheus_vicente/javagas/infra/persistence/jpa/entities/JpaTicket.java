package com.matheus_vicente.javagas.infra.persistence.jpa.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


import com.matheus_vicente.javagas.domain.entities.ticket.StatusTicket;
import com.matheus_vicente.javagas.domain.entities.ticket.Ticket;
import com.matheus_vicente.javagas.domain.entities.values_objects.tarifa.TipoTarifa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tickets")
public class JpaTicket {
    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String codigo;

    private UUID vagaId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTicket status;
    
    @Column(nullable = false)
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTarifa tarifaTipo;

    @Column(nullable = false)
    private BigDecimal tarifaValor;

    @Column
    private BigDecimal tarifaValorAdicional;

    @Column
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @Column
    private LocalDateTime dataDeSaida;

    protected JpaTicket() {}

    private JpaTicket(
        UUID id,
        String codigo,
        UUID vagaId,
        StatusTicket status,
        String placa,
        TipoTarifa tarifaTipo,
        BigDecimal tarifaValor,
        BigDecimal tarifaValorAdicional,
        BigDecimal valor,
        LocalDateTime criadoEm,
        LocalDateTime dataDeSaida
    ) {
        this.id = id;
        this.codigo = codigo;
        this.vagaId = vagaId;
        this.status = status;
        this.placa = placa;
        this.tarifaTipo = tarifaTipo;
        this.tarifaValor = tarifaValor;
        this.tarifaValorAdicional = tarifaValorAdicional;
        this.valor = valor;
        this.criadoEm = criadoEm;
        this.dataDeSaida = dataDeSaida;
    }

    public static JpaTicket fromDomain(Ticket ticket) {
        return new JpaTicket(
            ticket.getId(),
            ticket.getCodigo().codigo(),
            ticket.getVagaId(),
            ticket.getStatus(),
            ticket.getPlaca().placa(),
            ticket.getTarifa().getTipo(),
            ticket.getTarifa().getValor(),
            ticket.getTarifa().getValorAdicional(),
            ticket.getValor(),
            ticket.getCriadoEm(),
            ticket.getDataDeSaida()
        );
    }

    public Ticket toDomain() {
        return Ticket.rehydrate(
            id,
            codigo,
            vagaId,
            status,
            placa,
            tarifaTipo,
            tarifaValor,
            tarifaValorAdicional,
            valor,
            criadoEm,
            dataDeSaida
        );
    }

    public UUID getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public UUID getVagaId() {
        return vagaId;
    }

    public StatusTicket getStatus() {
        return status;
    }

    public String getPlaca() {
        return placa;
    }

    public TipoTarifa getTarifaTipo() {
        return tarifaTipo;
    }

    public BigDecimal getTarifaValor() {
        return tarifaValor;
    }

    public BigDecimal getTarifaValorAdicional() {
       return tarifaValorAdicional;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getDataDeSaida() {
        return dataDeSaida;
    }
}
