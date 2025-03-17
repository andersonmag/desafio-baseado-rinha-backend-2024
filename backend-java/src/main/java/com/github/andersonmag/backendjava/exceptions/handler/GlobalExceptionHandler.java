package com.github.andersonmag.backendjava.exceptions.handler;

import com.github.andersonmag.backendjava.exceptions.RegistroNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RegistroNaoEncontradoException.class)
	public ResponseEntity<ErroResponse> handleNaoEncontradoException(RegistroNaoEncontradoException ex) {
		return new ResponseEntity<>(new ErroResponse(ex.getMessage(), null), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErroResponse> handleAcaoNaoSuportadaException(IllegalArgumentException ex) {
		return new ResponseEntity<>(new ErroResponse(ex.getMessage(), null), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroResponse> handleValidacaoException(MethodArgumentNotValidException ex) {
		final List<ErroCampoResponse> erros = new ArrayList<>();

		ex.getBindingResult().getAllErrors().forEach(erro -> {
			final String campo = ((FieldError) erro).getField();
			erros.add(new ErroCampoResponse(campo, erro.getDefaultMessage()));
		});

		return ResponseEntity.unprocessableEntity().body(new ErroResponse("Erros de validação", erros));
	}
}
