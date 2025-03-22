package com.introducao.spring_jpa.repository;

import com.introducao.spring_jpa.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

}
