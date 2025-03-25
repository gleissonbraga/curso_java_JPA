package com.introducao.spring_jpa.service;

import com.introducao.spring_jpa.model.Autor;
import com.introducao.spring_jpa.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository){
        this.repository = repository;
    }

    public Autor salvar(Autor autor){
        return repository.save(autor);
    }
}
