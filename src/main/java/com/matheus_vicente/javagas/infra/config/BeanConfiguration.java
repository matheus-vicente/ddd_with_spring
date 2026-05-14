package com.matheus_vicente.javagas.infra.config;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.matheus_vicente.javagas.application.use_cases.AtualizarInfosVagaUseCase;
import com.matheus_vicente.javagas.application.use_cases.CancelarTicketUseCase;
import com.matheus_vicente.javagas.application.use_cases.CriarVagaUseCase;
import com.matheus_vicente.javagas.application.use_cases.DeletarVagasUseCase;
import com.matheus_vicente.javagas.application.use_cases.FecharTicketUseCase;
import com.matheus_vicente.javagas.application.use_cases.GerarTicketUseCase;
import com.matheus_vicente.javagas.application.use_cases.ListarVagasUseCase;
import com.matheus_vicente.javagas.domain.repositories.CalcularTarifa;
import com.matheus_vicente.javagas.domain.repositories.GeradorDeCodigoTicket;
import com.matheus_vicente.javagas.domain.repositories.TicketsRepository;
import com.matheus_vicente.javagas.domain.repositories.VagasRepository;

@Configuration
public class BeanConfiguration {
    @Bean
    CriarVagaUseCase criarVagaUseCase(VagasRepository vagasRepository) {
        return new CriarVagaUseCase(vagasRepository);
    }

    @Bean
    ListarVagasUseCase listarVagasUseCase(VagasRepository vagasRepository) {
        return new ListarVagasUseCase(vagasRepository);
    }

    @Bean
    AtualizarInfosVagaUseCase atualizarInfosVagaUseCase(VagasRepository vagasRepository) {
        return new AtualizarInfosVagaUseCase(vagasRepository);
    }

    @Bean
    DeletarVagasUseCase deletarVagasUseCase(VagasRepository vagasRepository) {
        return new DeletarVagasUseCase(vagasRepository);
    }

    @Bean
    GerarTicketUseCase gerarTicketUseCase(
        VagasRepository vagasRepository,
        TicketsRepository ticketsRepository,
        GeradorDeCodigoTicket geradorDeCodigoTicket
    ) {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        return new GerarTicketUseCase(
            vagasRepository,
            ticketsRepository,
            geradorDeCodigoTicket,
            clock
        );
    }

    @Bean
    CancelarTicketUseCase cancelarTicketUseCase(
        VagasRepository vagasRepository,
        TicketsRepository ticketsRepository
    ) {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        return new CancelarTicketUseCase(
            vagasRepository,
            ticketsRepository,
            clock
        );
    }

    @Bean
    FecharTicketUseCase fecharTicketUseCase(
        VagasRepository vagasRepository,
        TicketsRepository ticketsRepository,
        CalcularTarifa calcularTarifa
    ) {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        return new FecharTicketUseCase(
            vagasRepository,
            ticketsRepository,
            calcularTarifa,
            clock
        );
    }
}
