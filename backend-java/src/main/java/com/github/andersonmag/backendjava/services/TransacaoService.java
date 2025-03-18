package com.github.andersonmag.backendjava.services;

import com.github.andersonmag.backendjava.exceptions.RegistroNaoEncontradoException;
import com.github.andersonmag.backendjava.models.dtos.ExtratoClienteResponse;
import com.github.andersonmag.backendjava.models.dtos.TransacaoClienteRequest;
import com.github.andersonmag.backendjava.models.dtos.TransacaoClienteResponse;
import com.github.andersonmag.backendjava.models.entities.Cliente;
import com.github.andersonmag.backendjava.models.entities.Transacao;
import com.github.andersonmag.backendjava.models.enums.TipoTransacao;
import com.github.andersonmag.backendjava.repositories.ClienteRepository;
import com.github.andersonmag.backendjava.repositories.TransacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                Objects.equals(TipoTransacao.DEBITO, TipoTransacao.of(request.tipo())) ?
                        cliente.getSaldoAtual() - valor :
                        cliente.getSaldoAtual() + valor
        );

        if (novoSaldo < (cliente.getLimite() * -1)) {
            throw new IllegalArgumentException("Saldo inconsistente");
        }

        cliente.setSaldoAtual(novoSaldo);
        clienteRepository.save(cliente);
        transacaoRepository.save(request.toModel(cliente));

        return new TransacaoClienteResponse(cliente.getLimite(), novoSaldo);
    }

    public ExtratoClienteResponse getExtratoCliente(Long idCliente) {
        final Cliente cliente = getClientePorId(idCliente);
        final Pageable paginacao = PageRequest.of(0, 10, Sort.by("realizada_em").descending());
        Page<Transacao> transacoesClientePaginado = transacaoRepository.findAllByCliente(paginacao, idCliente);

        return new ExtratoClienteResponse(
                cliente.getSaldoAtual(), LocalDateTime.now(), cliente.getLimite(), transacoesClientePaginado.getContent()
        );
    }

    private Cliente getClientePorId(Long idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado!"));
    }
}
