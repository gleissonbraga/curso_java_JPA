package com.introducao.spring_jpa.controller;


import com.introducao.spring_jpa.controller.dto.AutorDTO;
import com.introducao.spring_jpa.model.Autor;
import com.introducao.spring_jpa.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("autores")
// localhost:8080/autores
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service){
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity salvar(@RequestBody AutorDTO autor){
        Autor autorEntidade = autor.mapearParaAutor();
        service.salvar(autorEntidade);

        http://localhost:8080/autores/"retorna o código UUID"
        URI location =  ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(autorEntidade.getId()).toUri();

        return new ResponseEntity("Autor salvo com sucesso! " + autor, HttpStatus.CREATED);
    }
}
