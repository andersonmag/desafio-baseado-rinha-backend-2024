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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.github.andersonmag.backendjava.utils.BuilderTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    private final Long ID_CLIENTE = 1L;
    private TransacaoService service;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private TransacaoRepository transacaoRepository;

    @BeforeEach
    void setUp() {
        service = new TransacaoService(clienteRepository, transacaoRepository);
    }

    @Test
    public void deveLancarExceptionAoRealizarTransacaoClienteInexistente() {
        final TransacaoClienteRequest request = getTransacaoClienteRequestTest("10", TipoTransacao.RECEBIVEIS);
        assertThrows(RegistroNaoEncontradoException.class, () -> service.realizarTransacao(6L, request));
    }

    @Test
    public void deveLancarExceptionAoRealizarTransacaoSaldoInconsistente() {
        final TransacaoClienteRequest request = getTransacaoClienteRequestTest("12", TipoTransacao.DEBITO);
        final Cliente cliente = getClienteTest(ID_CLIENTE, 10L, 0L);
        when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(cliente));

        assertThrows(IllegalArgumentException.class, () -> service.realizarTransacao(ID_CLIENTE, request));
    }

    @Test
    public void deveRealizarTransacaoRecebiveisComSucesso() {
        final TransacaoClienteRequest request = getTransacaoClienteRequestTest("12", TipoTransacao.RECEBIVEIS);
        final Cliente cliente = getClienteTest(ID_CLIENTE, 1000L, 2L);
        when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(cliente));

        final TransacaoClienteResponse response = service.realizarTransacao(ID_CLIENTE, request);

        Assertions.assertEquals(cliente.getLimite(), response.limite());
        Assertions.assertEquals(14L, response.saldo());
    }

    @Test
    public void deveRealizarTransacaoDebitoComSucesso() {
        final TransacaoClienteRequest request = getTransacaoClienteRequestTest("12", TipoTransacao.DEBITO);
        final Cliente cliente = getClienteTest(ID_CLIENTE, 1000L, 2L);
        when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(cliente));

        final TransacaoClienteResponse response = service.realizarTransacao(ID_CLIENTE, request);

        Assertions.assertEquals(cliente.getLimite(), response.limite());
        Assertions.assertEquals(-10L, response.saldo());
    }

    @Test
    public void deveBuscarExtratoComSucesso() {
        final Cliente cliente = getClienteTest(ID_CLIENTE, 1000L, 2L);
        when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(cliente));
        final List<Transacao> transacoes = getTransacoesExtratoClienteTest(cliente);
        when(transacaoRepository.findAllByClienteId(any(Pageable.class), eq(ID_CLIENTE))).thenReturn(new PageImpl<>(transacoes));

        final ExtratoClienteResponse response = service.getExtratoCliente(ID_CLIENTE);

        Assertions.assertEquals(cliente.getLimite(), response.saldo().limite());
        Assertions.assertEquals(cliente.getSaldoAtual(), response.saldo().total());
        Assertions.assertEquals(10, response.ultimasTransacoes().size());
    }
}
