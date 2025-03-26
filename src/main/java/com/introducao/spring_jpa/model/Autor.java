package com.introducao.spring_jpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor")
@Getter // utilizando o lombock
@Setter
@ToString(exclude = "livros")
// Utilizado para monitorar se vai haver atualização no código, e jogar a data no update usuário
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate // quando cria um usuário preenche uma data e hora automaticamente
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate // quando atualiza um usuário preenche uma data e hora automaticamente
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;
}
