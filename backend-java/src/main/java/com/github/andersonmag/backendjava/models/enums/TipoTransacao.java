package com.github.andersonmag.backendjava.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;
import java.util.stream.Stream;

public enum TipoTransacao {
    RECEBIVEIS("r"), DEBITO("d");
    private String tipo;

    @JsonCreator
    TipoTransacao(String tipo) {
        this.tipo = tipo;
    }

    public static TipoTransacao of(String tipo) {
        return Stream.of(TipoTransacao.values())
                .filter(t -> Objects.equals(t.getTipo(), tipo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de transação inválido: " + tipo));
    }

    @JsonValue
    public String getTipo() {
        return tipo;
    }
}
