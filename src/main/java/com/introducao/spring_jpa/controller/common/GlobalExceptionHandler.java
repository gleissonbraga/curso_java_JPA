package com.introducao.spring_jpa.controller.common;

import com.introducao.spring_jpa.controller.dto.ErroCampo;
import com.introducao.spring_jpa.controller.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // Serve para capturar exceptions e retornar
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // retorna um status chumbado onde qualquer requisição retorna este status
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldError = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldError.stream().map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErroResposta((
                HttpStatus.UNPROCESSABLE_ENTITY.value()),
                "Erro de validação",
                listaErros
        );
    }

}
