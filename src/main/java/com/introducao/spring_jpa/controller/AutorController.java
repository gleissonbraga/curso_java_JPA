package com.introducao.spring_jpa.controller;


import com.introducao.spring_jpa.controller.dto.AutorDTO;
import com.introducao.spring_jpa.controller.dto.ErroResposta;
import com.introducao.spring_jpa.controller.mappers.AutorMapper;
import com.introducao.spring_jpa.exceptions.OperacaoNaoPermitidaException;
import com.introducao.spring_jpa.exceptions.RegistroDuplicadoException;
import com.introducao.spring_jpa.model.Autor;
import com.introducao.spring_jpa.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
// localhost:8080/autores
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;
    // O COMPONENTE @RequiredArgConstructor na hora de compilar o código cria o construtor automaticamente
//    public AutorController(AutorService service){
//        this.service = service;
//    }

    @PostMapping
    // @Valid ativa a valudação do campo do @@NotBlank e @NotNull
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        Autor autor = mapper.toEntity(dto);
        service.salvar(autor);
        // http://localhost:8080/autores/"retorna o código UUID"
        URI location = gerarHeaderLocation(autor.getId());
        // return new ResponseEntity("Autor salvo com sucesso! " + autor, HttpStatus.CREATED);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return service
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deletar(autorOptional.get());
        // retorna o status code
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            // utilizado para o QUERY PARAMS no postman (quanod passamos dois @RequestParam temos que adicionar o VALUE
            @RequestParam(value = "nome") String nome,
            @RequestParam(value = "nacionalidade") String nacionalidade) {
        List<Autor> resultado = service.pesquisar(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream()
                .map(mapper::toDTO
                ).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());
        service.atualizar(autor);
        return ResponseEntity.noContent().build();
    }
}
