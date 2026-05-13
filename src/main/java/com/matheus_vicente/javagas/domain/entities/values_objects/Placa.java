package com.matheus_vicente.javagas.domain.entities.values_objects;

import com.matheus_vicente.javagas.domain.exceptions.DomainException;

public record Placa(
    String placa
) {
    private static final String PLACA_REGEX = "^[A-Z]{3}[0-9]{4}$|^[A-Z]{3}[0-9][A-Z][0-9]{2}$";

    public Placa {
        if (placa == null || placa.isEmpty()) {
            throw new DomainException("O campo placa deve ser preenchido");
        }

        placa = placa.trim().toUpperCase().replace("-", "");

        if (!placa.matches(PLACA_REGEX)) {
            throw new DomainException("Placa inválida. Use o formato ABC-1234 ou ABC1D23");
        }

    }

    public boolean isEmpty() {
        return placa.isEmpty();
    }

    public boolean equals(String anString) {
        return placa.equals(anString);
    }
}
