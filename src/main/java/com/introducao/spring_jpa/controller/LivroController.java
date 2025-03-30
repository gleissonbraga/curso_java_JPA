package com.introducao.spring_jpa.controller;

import com.introducao.spring_jpa.controller.dto.CadastroLivroDTO;
import com.introducao.spring_jpa.controller.dto.ErroResposta;
import com.introducao.spring_jpa.exceptions.RegistroDuplicadoException;
import com.introducao.spring_jpa.service.LicroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController {

    private final LicroService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody CadastroLivroDTO dto){
        try {
            // mapear dto para entidade
            // enviar a entidade para o service validar e salvar na base
            // criar url para acesso do dados do livro
            // retornar codico created com header location
            return ResponseEntity.ok(dto);
        } catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
