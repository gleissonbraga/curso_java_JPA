package com.introducao.spring_jpa.controller.dto;

import com.introducao.spring_jpa.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

// DTO = Data Transfer Object
public record AutorDTO(
        UUID id,
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 2, max = 100, message = "Campo fora do tamanho padrão") // define o tamanho da string
        String nome,
        @NotNull(message = "Campo obrigatório") // JSON enviado no body não pode ser nullo
        @Past(message = "Não pode ser uma data futura") // para data
        LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatório") // JSON enviado pelo body não pode ser vazio
        @Size(max = 50, min = 2, message = "Campo fora do tamanho padrão")  // define o tamanho da string
        String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }


}


