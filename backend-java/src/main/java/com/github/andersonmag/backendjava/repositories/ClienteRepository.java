package com.github.andersonmag.backendjava.repositories;

import com.github.andersonmag.backendjava.models.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
