package com.introducao.spring_jpa.repository;

import com.introducao.spring_jpa.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService transacaoService;

    /*
    * Commit -> confirmar as alterações
    * Roolback -> desfazer as alterações
    * */
    @Test
    void transacaoSimples(){
        // salvar um livro
        // salvar autor
        // alugar o livro
        // enviar email pro locatario
        // notificar que o livro saiu da livraria

        transacaoService.executar();
    }

    @Test
    void TrancaoEstadoTest(){
        transacaoService.atualizacaoSemAtualizar();
    }
}
