package com.introducao.spring_jpa.controller.mappers;


import com.introducao.spring_jpa.controller.dto.CadastroLivroDTO;
import com.introducao.spring_jpa.controller.dto.ResultadoPesquisaLivroDTO;
import com.introducao.spring_jpa.model.Livro;
import com.introducao.spring_jpa.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    public AutorRepository autorMapperRepository;

    @Mapping(target = "autor", expression = "java( autorMapperRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);


    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
