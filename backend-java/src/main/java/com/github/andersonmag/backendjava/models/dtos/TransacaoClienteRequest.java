package com.github.andersonmag.backendjava.models.dtos;

import com.github.andersonmag.backendjava.models.entities.Cliente;
import com.github.andersonmag.backendjava.models.entities.Transacao;
import com.github.andersonmag.backendjava.models.enums.TipoTransacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TransacaoClienteRequest(@NotNull(message = "É obrigátorio.")
									  @Pattern(regexp = "^\\d+$", message = "Deve ser um número inteiro válido.")
									  String valor,
									  @NotNull(message = "É obrigátorio.")
									  @Pattern(regexp = "^(r|d)$", message = "Só aceita os valores 'r' ou 'd'.")
									  String tipo,
									  @NotNull(message = "É obrigátorio.")
									  @Size(min = 1, max = 10, message = "Deve ter entre 1 e 10 caracteres.")
									  String descricao)
{

	public Transacao toModel(Cliente cliente) {
		return new Transacao(Long.valueOf(this.valor), TipoTransacao.fromString(this.tipo), descricao, cliente);
	}
}