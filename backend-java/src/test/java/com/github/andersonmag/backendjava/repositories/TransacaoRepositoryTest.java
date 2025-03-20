package com.github.andersonmag.backendjava.repositories;

import com.github.andersonmag.backendjava.models.entities.Cliente;
import com.github.andersonmag.backendjava.models.entities.Transacao;
import com.github.andersonmag.backendjava.models.enums.TipoTransacao;
import com.github.andersonmag.backendjava.utils.BuilderTestUtils;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@DataJpaTest
public class TransacaoRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Test
    public void deveBuscarAsUltimas10TransacoesDataDecrescrente() {
        final long idCliente = 1L;
        final Cliente cliente = BuilderTestUtils.getClienteTest(idCliente, 1000, 0L);
        BuilderTestUtils.getTransacoesExtratoClienteTest(cliente).forEach(entityManager::persist);
        entityManager.persist(new Transacao(100L, TipoTransacao.DEBITO, "trans11", cliente));

        final Pageable paginacao = PageRequest.of(0, 10, Sort.by("realizadaEm").descending());
        Page<Transacao> transacoesPaginado = transacaoRepository.findAllByClienteId(paginacao, idCliente);

        Assertions.assertEquals(10, transacoesPaginado.getNumberOfElements());
        Assertions.assertEquals("trans11", transacoesPaginado.getContent().get(0).getDescricao());
        Assertions.assertEquals("trans2", transacoesPaginado.getContent().get(9).getDescricao());
    }
}
