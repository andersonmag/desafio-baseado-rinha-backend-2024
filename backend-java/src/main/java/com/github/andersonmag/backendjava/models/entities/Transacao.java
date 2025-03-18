package com.github.andersonmag.backendjava.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.andersonmag.backendjava.config.TipoTransacaoConverter;
import com.github.andersonmag.backendjava.models.enums.TipoTransacao;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "transacoes")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    private Long valor;
    @Convert(converter = TipoTransacaoConverter.class)
    private TipoTransacao tipo;
    private String descricao;
    private LocalDateTime realizadaEm;

    public Transacao(Long valor, TipoTransacao tipo, String descricao) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizadaEm = LocalDateTime.now();
    }

    public Transacao(Long valor, TipoTransacao tipo, String descricao, Cliente cliente) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizadaEm = LocalDateTime.now();
        this.cliente = cliente;
    }

    public Transacao() {
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getRealizadaEm() {
        return realizadaEm;
    }

    public void setRealizadaEm(LocalDateTime realizadaEm) {
        this.realizadaEm = realizadaEm;
    }
}
