package com.matheus_vicente.javagas.application.dtos;

import java.time.LocalDateTime;

public record ErroResponseDTO(
    String mensagem,
    int status,
    LocalDateTime timestamp,
    String path
) {}
