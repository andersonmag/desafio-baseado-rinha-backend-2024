package com.github.andersonmag.backendjava.controllers;

import com.github.andersonmag.backendjava.models.dtos.TransacaoClienteRequest;
import com.github.andersonmag.backendjava.models.dtos.TransacaoClienteResponse;
import com.github.andersonmag.backendjava.services.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private final TransacaoService transacaoService;

	public ClienteController(TransacaoService transacaoService) {
		this.transacaoService = transacaoService;
	}

	@PostMapping("/{id}/transacoes")
	public ResponseEntity<TransacaoClienteResponse> realizarTransacao(
		@PathVariable("id") Long idCliente, @RequestBody @Valid TransacaoClienteRequest request
	) {
		return ResponseEntity.ok(transacaoService.realizarTransacao(idCliente, request));
	}
}
