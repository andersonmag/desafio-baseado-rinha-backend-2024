package com.github.andersonmag.backendjava.utils;

import com.github.andersonmag.backendjava.models.dtos.TransacaoClienteRequest;
import com.github.andersonmag.backendjava.models.entities.Cliente;
import com.github.andersonmag.backendjava.models.entities.Transacao;
import com.github.andersonmag.backendjava.models.enums.TipoTransacao;

import java.util.List;

public class BuilderTestUtils {

    public static Cliente getClienteTest(Long idCliente, long limite, long saldoAtual) {
        return new Cliente(idCliente, "Cliente 1", limite, saldoAtual);
    }

    public static TransacaoClienteRequest getTransacaoClienteRequestTest(String valor, TipoTransacao tipo) {
        return new TransacaoClienteRequest(valor, tipo.getTipo(), "Transacao01");
    }

    public static List<Transacao> getTransacoesExtratoClienteTest(Cliente cliente) {
        return List.of(
            new Transacao(50L, TipoTransacao.DEBITO, "trans1", cliente),
            new Transacao(200L, TipoTransacao.DEBITO, "trans2", cliente),
            new Transacao(400L, TipoTransacao.RECEBIVEIS, "trans3", cliente),
            new Transacao(200L, TipoTransacao.DEBITO, "trans4", cliente),
            new Transacao(100L, TipoTransacao.DEBITO, "trans5", cliente),
            new Transacao(320L, TipoTransacao.RECEBIVEIS, "trans6", cliente),
            new Transacao(200L, TipoTransacao.RECEBIVEIS, "trans7", cliente),
            new Transacao(40L, TipoTransacao.DEBITO, "trans8", cliente),
            new Transacao(40L, TipoTransacao.RECEBIVEIS, "trans9", cliente),
            new Transacao(150L, TipoTransacao.RECEBIVEIS, "trans10", cliente)
        );
    }
}
