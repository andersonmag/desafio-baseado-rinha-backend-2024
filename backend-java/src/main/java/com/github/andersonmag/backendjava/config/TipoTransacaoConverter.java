package com.github.andersonmag.backendjava.config;

import com.github.andersonmag.backendjava.models.enums.TipoTransacao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoTransacaoConverter implements AttributeConverter<TipoTransacao, String> {

    @Override
    public String convertToDatabaseColumn(TipoTransacao tipoTransacao) {
        if (tipoTransacao == null) {
            return null;
        }
        return tipoTransacao.getTipo();
    }

    @Override
    public TipoTransacao convertToEntityAttribute(String tipo) {
        if (tipo == null) {
            return null;
        }

        return TipoTransacao.of(tipo);
    }
}
