package com.introducao.spring_jpa.controller.dto;

import com.introducao.spring_jpa.model.Autor;

import java.time.LocalDate;

// DTO = Data Transfer Object
public record AutorDTO(
        String nome,
        LocalDate dataNascimento,
        String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
