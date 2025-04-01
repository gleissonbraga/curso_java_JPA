package com.introducao.spring_jpa.controller.mappers;

import com.introducao.spring_jpa.controller.dto.AutorDTO;
import com.introducao.spring_jpa.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
