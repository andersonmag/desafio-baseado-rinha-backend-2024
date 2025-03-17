package com.github.andersonmag.backendjava.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andersonmag.backendjava.exceptions.handler.GlobalExceptionHandler;
import com.github.andersonmag.backendjava.models.entities.Cliente;
import com.github.andersonmag.backendjava.models.enums.TipoTransacao;
import com.github.andersonmag.backendjava.repositories.ClienteRepository;
import com.github.andersonmag.backendjava.repositories.TransacaoRepository;
import com.github.andersonmag.backendjava.services.TransacaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClienteController.class)
@ContextConfiguration(classes = {
        ClienteController.class, HttpServletRequest.class, HttpServletResponse.class, TransacaoService.class,
        GlobalExceptionHandler.class
})
public class ClienteControllerTest {

    private final Long ID_CLIENTE = 1L;
    private final String BASE_URL = "/clientes/" + ID_CLIENTE;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ClienteRepository clienteRepository;

    @MockitoBean
    private TransacaoRepository transacaoRepository;

    @Test
    public void deveRetornarStatus422AoConterErroValidacao() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL.concat("/transacoes"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getTransacaoRequest("1.2", "01234567899", "s")))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.campos", hasSize(3)))
                .andExpect(jsonPath("$.campos[*].campo", hasItems("valor", "descricao", "tipo")));
    }

    @Test
    public void deveRetornarStatus422AoConterSaldoInconsistente() throws Exception {
        final Cliente cliente = getCliente(10L, 0L);
        BDDMockito.given(clienteRepository.findById(ID_CLIENTE)).willReturn(Optional.of(cliente));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL.concat("/transacoes"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getTransacaoRequest("12", "0123456789", TipoTransacao.DEBITO.getTipo())))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void deveRetornarStatus404ClienteInexistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL.concat("/transacoes"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getTransacaoRequest("10", "0123456789", TipoTransacao.RECEBIVEIS.getTipo())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveRetornarStatus200Sucesso() throws Exception {
        final Cliente cliente = getCliente(1000L, 0L);
        BDDMockito.given(clienteRepository.findById(ID_CLIENTE)).willReturn(Optional.of(cliente));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL.concat("/transacoes"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(getTransacaoRequest("10", "0123456789", TipoTransacao.RECEBIVEIS.getTipo())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limite").value(1000L))
                .andExpect(jsonPath("$.saldo").value(cliente.getSaldoAtual()));
    }

    private String getTransacaoRequest(String valor, String descricao, String tipo) throws JsonProcessingException {
        Map<String, Object> valores = new HashMap<>();
        valores.put("valor", valor);
        valores.put("descricao", descricao);
        valores.put("tipo", tipo);

        return new ObjectMapper().writeValueAsString(valores);
    }

    private Cliente getCliente(long limite, long saldoAtual) {
        return new Cliente(1L, "Cliente 1", limite, saldoAtual);
    }
}
