package com.matheus_vicente.javagas.application.services;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.matheus_vicente.javagas.domain.repositories.GeradorDeCodigoTicket;

public class GeradorDeCodigoTicketImpl implements GeradorDeCodigoTicket {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CARACTERES_VALIDOS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");

    @Override
    public String novoCodigo() {
        StringBuilder str = new StringBuilder(45);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                int index = RANDOM.nextInt(CARACTERES_VALIDOS.length());
                str.append(CARACTERES_VALIDOS.charAt(index));
            }

            str.append("-");
        }

        str.append(LocalDateTime.now().format(FORMATADOR_DATA));

        return str.toString();
    }
}
