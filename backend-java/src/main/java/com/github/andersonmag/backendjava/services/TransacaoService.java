package com.github.andersonmag.backendjava.services;

import com.github.andersonmag.backendjava.exceptions.RegistroNaoEncontradoException;
import com.github.andersonmag.backendjava.models.dtos.TransacaoClienteRequest;
import com.github.andersonmag.backendjava.models.dtos.TransacaoClienteResponse;
import com.github.andersonmag.backendjava.models.entities.Cliente;
import com.github.andersonmag.backendjava.models.enums.TipoTransacao;
import com.github.andersonmag.backendjava.repositories.ClienteRepository;
import com.github.andersonmag.backendjava.repositories.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TransacaoService {

	private final ClienteRepository clienteRepository;
	private final TransacaoRepository transacaoRepository;

	public TransacaoService(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
		this.clienteRepository = clienteRepository;
		this.transacaoRepository = transacaoRepository;
	}

	public TransacaoClienteResponse realizarTransacao(Long idCliente, TransacaoClienteRequest request) {
		final Cliente cliente = getClientePorId(idCliente);
		final Long valor = Long.valueOf(request.valor());
		final Long novoSaldo = (
			Objects.equals(TipoTransacao.DEBITO, TipoTransacao.fromString(request.tipo())) ?
				cliente.getSaldoAtual() - valor :
				cliente.getSaldoAtual() + valor
		);

		if (novoSaldo < (cliente.getLimite() * -1)) {
			throw new IllegalArgumentException("Saldo inconsistente");
		}

		cliente.setSaldoAtual(novoSaldo);
		clienteRepository.save(cliente);
		transacaoRepository.save(request.toModel());

		return new TransacaoClienteResponse(cliente.getLimite(), novoSaldo);
	}

	private Cliente getClientePorId(Long idCliente) {
		return clienteRepository.findById(idCliente)
			.orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado!"));
	}
}
