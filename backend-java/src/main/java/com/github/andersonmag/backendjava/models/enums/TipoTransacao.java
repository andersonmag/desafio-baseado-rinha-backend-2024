package com.github.andersonmag.backendjava.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoTransacao {
	RECEBIVEIS("r"), DEBITO("d");
	private String tipo;

	@JsonCreator
	TipoTransacao(String tipo) {
		this.tipo = tipo;
	}

	public static TipoTransacao fromString(String tipo) {
		if (tipo == null) {
			return null;
		}

		for (TipoTransacao tipoTransacao : TipoTransacao.values()) {
			if (tipoTransacao.getTipo().equalsIgnoreCase(tipo)) {
				return tipoTransacao;
			}
		}
		throw new IllegalArgumentException("Tipo de transação inválido: " + tipo);
	}

	@JsonValue
	public String getTipo() {
		return tipo;
	}
}
