package com.github.andersonmag.backendjava.repositories;

import com.github.andersonmag.backendjava.models.entities.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {}
