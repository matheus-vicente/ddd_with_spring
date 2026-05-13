package com.matheus_vicente.javagas.infra.persistence.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheus_vicente.javagas.infra.persistence.jpa.entities.JpaVaga;

public interface JpaVagasRepository extends JpaRepository<JpaVaga, UUID> {
    Optional<JpaVaga> findByCodigo(String codigo);
}
