package com.matheus_vicente.javagas.domain.entities.ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

import com.matheus_vicente.javagas.domain.entities.values_objects.CodigoTicket;
import com.matheus_vicente.javagas.domain.entities.values_objects.Placa;
import com.matheus_vicente.javagas.domain.entities.values_objects.tarifa.Tarifa;
import com.matheus_vicente.javagas.domain.exceptions.DomainException;
import com.matheus_vicente.javagas.domain.repositories.GeradorDeCodigoTicket;

public class Ticket {
    private final UUID id;
    private final CodigoTicket codigo;
    private UUID vagaId;
    private StatusTicket status;
    private Placa placa;
    private Tarifa tarifa;
    private BigDecimal valor;
    private LocalDateTime criadoEm;
    private LocalDateTime dataDeSaida;

    private Ticket(
        UUID id,
        CodigoTicket codigo,
        UUID vagaId,
        StatusTicket status,
        Placa placa,
        Tarifa tarifa,
        BigDecimal valor,
        LocalDateTime criadoEm,
        LocalDateTime dataDeSaida
    ) {
        if (id == null) {
            throw new DomainException("O campo ID não pode ser nulo");
        }

        if (codigo == null || codigo.isEmpty()) {
            throw new DomainException("O campo CÓDIGO não pode ser nulo");
        }

        if (vagaId == null) {
            throw new DomainException("O campo VAGA_ID não pode ser nulo");
        }

        if (status == null) {
            throw new DomainException("O campo STATUS não pode ser nulo");
        }

        if (placa == null || placa.isEmpty()) {
            throw new DomainException("O campo PLACA não pode ser nulo");
        }

        if (tarifa == null) {
            throw new DomainException("O campo TARIFA não pode ser nulo");
        }

        if (criadoEm == null) {
            throw new DomainException("O campo CRIADO_EM não pode ser nulo");
        }

        this.id = id;
        this.codigo = codigo;
        this.vagaId = vagaId;
        this.status = status;
        this.placa = placa;
        this.tarifa = tarifa;
        this.valor = valor;
        this.criadoEm = criadoEm;
        this.dataDeSaida = dataDeSaida;
    }

    public static Ticket create(
        GeradorDeCodigoTicket gerador,
        UUID vagaId,
        String placa,
        Tarifa tarifa,
        LocalDateTime criadoEm
    ) {
        if (criadoEm.isAfter(LocalDateTime.now())) {
            throw new DomainException("O campo CRIADO_EM não pode ser no futuro");
        }

        UUID id = UUID.randomUUID();
        CodigoTicket codigo = new CodigoTicket(gerador.novoCodigo());
        StatusTicket status = StatusTicket.PENDENTE;
        Placa placaVO = new Placa(placa);

        return new Ticket(
            id,
            codigo,
            vagaId,
            status,
            placaVO,
            tarifa,
            BigDecimal.ZERO,
            criadoEm,
            null
        );
    }

    public void pagar(BigDecimal valor, LocalDateTime dataDeSaida) {
        if (this.status.equals(StatusTicket.CANCELADO)) {
            throw new DomainException("Não é possível pagar um Ticket cancelado");
        }

        if (this.status.equals(StatusTicket.PAGO)) {
            throw new DomainException("Este Ticket já está pago");
        }

        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("O valor da tarifa não pode ser negativo ou nulo");
        }

        this.status = StatusTicket.PAGO;
        this.dataDeSaida = dataDeSaida;
        this.valor = valor.setScale(2, RoundingMode.HALF_UP);
    }

    public void cancelar(LocalDateTime dataDeSaida) {
        if (this.status.equals(StatusTicket.PAGO)) {
            throw new DomainException("Não é possível cancelar um Ticket pago");
        }

        if (this.status.equals(StatusTicket.CANCELADO)) {
            throw new DomainException("Este Ticket já está cancelado");
        }

        this.status = StatusTicket.CANCELADO;
        this.dataDeSaida = dataDeSaida;
    }

    public UUID getId() {
        return id;
    }

    public CodigoTicket getCodigo() {
        return codigo;
    }

    public UUID getVagaId() {
        return vagaId;
    }

    public StatusTicket getStatus() {
        return status;
    }

    public Placa getPlaca() {
        return placa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getDataDeSaida() {
        return dataDeSaida;
    }
}
