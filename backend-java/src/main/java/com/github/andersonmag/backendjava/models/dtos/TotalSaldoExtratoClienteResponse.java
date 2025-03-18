package com.github.andersonmag.backendjava.models.dtos;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TotalSaldoExtratoClienteResponse(Long total, LocalDateTime dataExtrato, Long limite) {
}
