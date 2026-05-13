package com.matheus_vicente.javagas.infra.persistence.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheus_vicente.javagas.infra.persistence.jpa.entities.JpaTicket;

public interface JpaTicketsRepository extends JpaRepository<JpaTicket, UUID> {
    Optional<JpaTicket> findByCodigo(String codigo);
}
