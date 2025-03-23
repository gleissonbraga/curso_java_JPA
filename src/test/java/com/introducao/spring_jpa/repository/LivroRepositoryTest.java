package com.introducao.spring_jpa.repository;

import com.introducao.spring_jpa.model.Autor;
import com.introducao.spring_jpa.model.GeneroLivro;
import com.introducao.spring_jpa.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitle("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = autorRepository.findById(UUID.fromString("2a68f409-b0d4-4c8e-9026-e048692563a8")).orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }

    // ESTA É UTILIZADA COM CASCADE, PORÉM NÃO É MUITO RECOMENDADO
    @Test
    public void salvarCascadTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitle("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1960, 1, 31));

        livro.setAutor(autor);

        repository.save(livro);
    }


    // A FORMA MAIS COMUM DE SE UTILIZAR SERIA ESTA
    @Test
    public void salvarAutorELivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitle("Terceiro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("José");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1960, 1, 31));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    public void atualizarAutorLivro(){
        UUID id = UUID.fromString("d2f939c9-c677-4449-8e9c-9f5459be805f");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("2a68f409-b0d4-4c8e-9026-e048692563a8");
        Autor maria = autorRepository.findById(idAutor).orElse(null);


        livroParaAtualizar.setAutor(maria);

        repository.save(livroParaAtualizar);

    }

    @Test
    public void deletar(){
        UUID byId = UUID.fromString("d2f939c9-c677-4449-8e9c-9f5459be805f");
        repository.deleteById(byId);
    }


    @Test
    @Transactional // caso você declare na entidade como LAzy, para realizar um novo select é preciso utilizar esta anotação
    //                para buscar um dados diferente da entidade principal
    public void buscarLivro(){
        UUID id = UUID.fromString("c27822ad-cfef-4692-a6e9-dadd38c32b5c");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitle());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    public void pesquisaPorTituloTest(){
        List<Livro> lista = repository.findByTitulo("O Roubo da casa assombrada");
        lista.forEach(System.out::println);
    }

//    @Test
//    public void pesquisaPorTituloTest(){
//        List<Livro> lista = repository.findByTitulo("Roubo da casa assombrada");
//        lista.forEach(System.out::println);
//    }
}
