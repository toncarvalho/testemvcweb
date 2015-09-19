package org.jboss.as.testemvcweb.data;

import org.jboss.as.testemvcweb.model.Produto;
import org.jboss.as.testemvcweb.util.CRUDManager;
import org.jboss.as.testemvcweb.util.ICrudBasic;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonca on 19/09/2015.
 */
@ApplicationScoped
public class ProdutoRepository implements ICrudBasic<Produto> {

    @Inject
    private CRUDManager em;

    @Override
    public Produto create() {

        Produto produto = new Produto();
        produto.setCodigo("000");
        produto.setDescricao("entre com a descrição");
        return produto;
    }

    @Override
    public Long save(Produto resource) {
        return em.save(resource);
    }

    @Override
    public Produto read(Long id) {
        return em.read(Produto.class, id);
    }

    @Override
    public void update(Long id, Produto resource) {
        em.update(Produto.class, resource);
    }

    @Override
    public void delete(Long id) {
        em.delete(Produto.class, id);
    }

    public List<Produto> findAllOrderedByName() {

        return em.getEm().createQuery(" from Produto p ").getResultList();
    }
}
