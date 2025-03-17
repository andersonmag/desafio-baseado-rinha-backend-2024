package com.github.andersonmag.backendjava.exceptions.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroResponse(String mensagem, List<ErroCampoResponse> campos) {}
