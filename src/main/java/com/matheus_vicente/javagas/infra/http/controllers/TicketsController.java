package com.matheus_vicente.javagas.infra.http.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import com.matheus_vicente.javagas.application.dtos.InfosTicketDTO;
import com.matheus_vicente.javagas.application.dtos.TicketResponseDTO;
import com.matheus_vicente.javagas.application.use_cases.CancelarTicketUseCase;
import com.matheus_vicente.javagas.application.use_cases.FecharTicketUseCase;
import com.matheus_vicente.javagas.application.use_cases.GerarTicketUseCase;
import com.matheus_vicente.javagas.domain.entities.ticket.Ticket;

@RestController
@RequestMapping("v1/tickets")
public class TicketsController {
    private GerarTicketUseCase gerarTicketUseCase;
    private CancelarTicketUseCase cancelarTicketUseCase;
    private FecharTicketUseCase fecharTicketUseCase;

    public TicketsController(
        GerarTicketUseCase gerarTicketUseCase,
        CancelarTicketUseCase cancelarTicketUseCase,
        FecharTicketUseCase fecharTicketUseCase
    ) {
        this.gerarTicketUseCase = gerarTicketUseCase;
        this.cancelarTicketUseCase = cancelarTicketUseCase;
        this.fecharTicketUseCase = fecharTicketUseCase;
    }

    @PostMapping("/gerar/{vagaId}")
    public ResponseEntity<TicketResponseDTO> gerarTicket(@PathVariable String vagaId, @RequestBody InfosTicketDTO ticketDTO) {
        Ticket ticket = this.gerarTicketUseCase.execute(vagaId, ticketDTO);

        return ResponseEntity.ok(TicketResponseDTO.fromDomain(ticket));
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<TicketResponseDTO> cancelarTicket(@PathVariable String id) {
        Ticket ticket = this.cancelarTicketUseCase.execute(id);

        return ResponseEntity.ok(TicketResponseDTO.fromDomain(ticket));
    }

    @PutMapping("/fechar/{id}")
    public ResponseEntity<TicketResponseDTO> fecharTicket(@PathVariable String id) {
        Ticket ticket = this.fecharTicketUseCase.execute(id);

        return ResponseEntity.ok(TicketResponseDTO.fromDomain(ticket));
    }
}
