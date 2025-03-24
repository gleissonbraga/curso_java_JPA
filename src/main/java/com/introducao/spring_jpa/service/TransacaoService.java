package com.introducao.spring_jpa.service;

import com.introducao.spring_jpa.model.Autor;
import com.introducao.spring_jpa.model.GeneroLivro;
import com.introducao.spring_jpa.model.Livro;
import com.introducao.spring_jpa.repository.AutorRepository;
import com.introducao.spring_jpa.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void salvarLivroComFoto(){
        //salva o livro
        // repository.save(livro)

        // pega o id do livro = livro.get();
        // var id =livro.getId();

        // salvae foto do livro -> bucket na nuvem
        // bucketService.salvar(livro.getFoto(), id + ".png");

        // atualizar o nome arquivo que foi salvo
        // livro.setNomeArquivoFoto(id + "png");
        // repository.save(livro);
    }

    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("17c5ca02-9274-4f28-bc8d-e65ae4c8c41d")).orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024, 6, 1));


    }

    @Transactional
    public void executar(){
        // salva o autor
        Autor autor = new Autor();
        autor.setNome("Francisco");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1960, 1, 31));

        autorRepository.save(autor);



        // salva o livro
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(300));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitle("Teste livro Francisco");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));



        livro.setAutor(autor);

        livroRepository.save(livro);

        if(autor.getNome().equals("Francisco")){
            throw new RuntimeException("Rollback!");
        }
    }
}
