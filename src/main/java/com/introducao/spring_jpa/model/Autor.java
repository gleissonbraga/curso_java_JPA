package com.introducao.spring_jpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor")
@Getter // utilizando o lombock
@Setter
@ToString(exclude = "livros")
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

//    @Transient // Faz com que o JPA não considere o List<Livro> uma coluna
    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY) // 'mappedBy' é utilizado para dizer que não é uma coluna da tabela
    private List<Livro> livros;
}
