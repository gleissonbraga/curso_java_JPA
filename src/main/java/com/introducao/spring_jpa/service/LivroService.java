package com.introducao.spring_jpa.service;


import com.introducao.spring_jpa.model.Livro;
import com.introducao.spring_jpa.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    public Livro salvar(Livro livro){
        return repository.save(livro);
    }

}
