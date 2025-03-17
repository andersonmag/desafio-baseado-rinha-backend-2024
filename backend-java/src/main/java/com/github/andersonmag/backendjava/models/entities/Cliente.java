package com.github.andersonmag.backendjava.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Long limite;
	private Long saldoAtual;

	public Cliente() {}

	public Cliente(Long id, String nome, Long limite, Long saldoAtual) {
		this.id = id;
		this.nome = nome;
		this.limite = limite;
		this.saldoAtual = saldoAtual;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(Long saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public Long getLimite() {
		return limite;
	}

	public void setLimite(Long limite) {
		this.limite = limite;
	}
}
