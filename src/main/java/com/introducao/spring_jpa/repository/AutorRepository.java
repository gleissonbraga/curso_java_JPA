package com.introducao.spring_jpa.repository;

import com.introducao.spring_jpa.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository // é opcional colocar esta anotação
public interface AutorRepository extends JpaRepository<Autor, UUID> {

//    public Autor autorRepository;
}
