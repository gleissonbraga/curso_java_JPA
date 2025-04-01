package com.introducao.spring_jpa.repository;

import com.introducao.spring_jpa.model.Autor;
import com.introducao.spring_jpa.model.GeneroLivro;
import com.introducao.spring_jpa.model.Livro;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    // sleect * from livro where titulo = titulo
    List<Livro> findByTitle(String titulo);

    // sleect * from livro where isbn = isbn
    List<Livro> findByIsbn(String isbn);

    List<Livro> findByTitleAndPrecoOrderByTitle(String titulo, BigDecimal preco);

    // select * from livro where data_publicacao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inici, LocalDate fim);

    // JPQL -> referencia as entidades e as propriedades
    // select l.* from livro as l order by l.livro, l.preco
    @Query("select l from Livro as l order by l.title, l.preco")
    List<Livro> listarTodosOrdenadoPorTitleAndPreco();

    @Query("select a from Livro l join l.autor a ")
    List<Autor> listarAtoresDosLivros();

    @Query("select distinct l.title from Livro l")
    List<String> listarNomesDiferentesLivro();

    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileira'
            order by l.genero
            """)
    List<String> listarGenerosAtoresBrasileiros();

    // named parameters -> parametros nomeados
    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro, @Param("paramOrdenacao") String nomePropriedade);

    // positional parameters
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro, String nomePropriedade);


    // métodos para realizar querys no delete e update
    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);

    boolean existsByAutor(Autor autor);
}
