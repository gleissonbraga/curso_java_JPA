package com.introducao.spring_jpa.controller;

import com.introducao.spring_jpa.controller.dto.CadastroLivroDTO;
import com.introducao.spring_jpa.controller.dto.ErroResposta;
import com.introducao.spring_jpa.controller.dto.ResultadoPesquisaLivroDTO;
import com.introducao.spring_jpa.controller.mappers.LivroMapper;
import com.introducao.spring_jpa.exceptions.RegistroDuplicadoException;
import com.introducao.spring_jpa.model.Livro;
import com.introducao.spring_jpa.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody CadastroLivroDTO dto) {
        // mapear dto para entidade
        Livro livro = mapper.toEntity(dto);
        // enviar a entidade para o service validar e salvar na base
        service.salvar(livro);
        // criar url para acesso do dados do livro
        var url = gerarHeaderLocation(livro.getId());
        // retornar codico created com header location
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
