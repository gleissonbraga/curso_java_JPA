package com.introducao.spring_jpa.controller;


import com.introducao.spring_jpa.controller.dto.AutorDTO;
import com.introducao.spring_jpa.controller.dto.ErroResposta;
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
public class AutorController {

    private final AutorService service;
    // O COMPONENTE @RequiredArgConstructor na hora de compilar o código cria o construtor automaticamente
//    public AutorController(AutorService service){
//        this.service = service;
//    }

    @PostMapping
    // @Valid ativa a valudação do campo do @@NotBlank e @NotNull
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autor){
        try{
            Autor autorEntidade = autor.mapearParaAutor();
            service.salvar(autorEntidade);

            // http://localhost:8080/autores/"retorna o código UUID"
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();


//      return new ResponseEntity("Autor salvo com sucesso! " + autor, HttpStatus.CREATED);
            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id){
        try{
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if(autorOptional.isEmpty()){
                return ResponseEntity.notFound().build();
            }

            service.deletar(autorOptional.get());

            // retorna o status code
            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException e) {
            var erroDTO = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
    // utilizado para o QUERY PARAMS no postman (quanod passamos dois @RequestParam temos que adicionar o VALUE
            @RequestParam(value = "nome") String nome,
            @RequestParam(value = "nacionalidade") String nacionalidade){
        List<Autor> resultado = service.pesquisar(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream()
                .map(autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())
                ).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if(autorOptional.isEmpty()){
                return ResponseEntity.notFound().build();
            }

            var autor = autorOptional.get();

            autor.setNome(dto.nome());
            autor.setNacionalidade(dto.nacionalidade());
            autor.setDataNascimento(dto.dataNascimento());

            service.atualizar(autor);

            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
