package com.introducao.spring_jpa.repository;

import com.introducao.spring_jpa.model.Autor;
import com.introducao.spring_jpa.model.GeneroLivro;
import com.introducao.spring_jpa.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

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

    @Test
    public void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Antonio");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1997, 5, 23));

        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitle("O roubo da casa misteriosa");
        livro.setDataPublicacao(LocalDate.of(1970, 2, 2));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("77777-77777");
        livro2.setPreco(BigDecimal.valueOf(204));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitle("O roubo da casa assombrada");
        livro2.setDataPublicacao(LocalDate.of(2001, 5, 10));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    public void listarListaAutorTest(){
        var id = UUID.fromString("def0c372-4d42-4d68-9d04-ff84d2bc812c");
        Autor autor = repository.findById(id).get();

        // buscar os livros do autor

        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }

}
