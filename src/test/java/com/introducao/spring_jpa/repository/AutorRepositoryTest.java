package com.introducao.spring_jpa.repository;

import com.introducao.spring_jpa.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1960, 1, 31));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("b6f5db65-a925-451b-87cf-0ecd97505308");

        // utilizar o Optional, pois pode ser que não exista esse ator
        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){

            Autor autorEncontrado = possivelAutor.get();

            System.out.println("Dados do Autor");
            System.out.println(possivelAutor.get());

            autorEncontrado.setDataNascimento(LocalDate.of(1940, 1, 30));

            repository.save(autorEncontrado);
        }

    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("b6f5db65-a925-451b-87cf-0ecd97505308");

        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("2a68f409-b0d4-4c8e-9026-e048692563a8");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }
}
