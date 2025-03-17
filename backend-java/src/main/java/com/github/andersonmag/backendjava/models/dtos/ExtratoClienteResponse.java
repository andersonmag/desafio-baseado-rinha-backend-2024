package com.github.andersonmag.backendjava.models.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.andersonmag.backendjava.models.entities.Transacao;

import java.time.LocalDateTime;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ExtratoClienteResponse(Long total, LocalDateTime dataExtrato, Long limite,
                                     List<Transacao> ultimasTransacoes) {
}
