package com.introducao.spring_jpa.service;

import com.introducao.spring_jpa.exceptions.OperacaoNaoPermitidaException;
import com.introducao.spring_jpa.model.Autor;
import com.introducao.spring_jpa.repository.AutorRepository;
import com.introducao.spring_jpa.repository.LivroRepository;
import com.introducao.spring_jpa.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    // O COMPONENTE @RequiredArgConstructor na hora de compilar o código cria o construtor automaticamente
//    public AutorService(AutorRepository repository, AutorValidator validator, LivroRepository livroRepository){
//        this.repository = repository;
//        this.validator = validator;
//        this.livroRepository = livroRepository;
//    }

    public Autor salvar(Autor autor){
        validator.validar(autor);
        return repository.save(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que Autor já esteja salvo na base");
        }

        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException(autor.getNome() + " possui livros cadastrados");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if(nome != null){
            return repository.findByNome(nome);
        }

        if(nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existByAutor(autor);
    }
}
