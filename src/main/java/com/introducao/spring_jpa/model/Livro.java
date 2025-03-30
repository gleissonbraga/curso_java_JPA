package com.introducao.spring_jpa.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
// esta anotação '@Data' possui as seguintes anotations (@Getter, @Setter, @EqualsAndHashCode, @RequiredArgsConstructor)
@ToString(exclude = "autor")
@EntityListeners(AuditingEntityListener.class)
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "title", length = 150, nullable = false)
    private String title;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING) // declara a entrada no banco com os valores corretos em String e não numero sequenciais
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco; // indicado para trabalhar com valores monetários grandes

    @ManyToOne(
            fetch = FetchType.LAZY // utilizado paraer os dados do livro somente e não do autor quando é realizado o join das tabelas
    )                  //(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_autor")
    private Autor autor; // definindo a chave estrangeira do ID do autor

    @CreatedDate // quando cria um usuário preenche uma data e hora automaticamente
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate // quando atualiza um usuário preenche uma data e hora automaticamente
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;

}
