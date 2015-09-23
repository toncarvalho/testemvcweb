package org.jboss.as.testemvcweb.data;


import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.jboss.as.testemvcweb.model.Produto;
import org.jboss.as.testemvcweb.util.CRUDManager;
import org.jboss.as.testemvcweb.util.ICrudBasic;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * Created by tonca on 19/09/2015.
 */
@Stateless
@LocalBean
public class ProdutoRepository implements ICrudBasic<Produto>, Serializable {

    @Inject
    private CRUDManager em;

    @Override
    public Produto create() {

        Produto produto = new Produto();
        produto.setFabricante("000");
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

    public List<Produto> pesquisaGenerica(String searchKey) {

        List<Produto> result = null;

        if (!searchKey.isEmpty()) {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em.getEm());
            QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Produto.class).get();
            org.apache.lucene.search.Query luceneQuery = qb.keyword().onFields("fabricante", "descricao").matching(searchKey).createQuery();
            // wrap Lucene query in a javax.persistence.Query
            javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Produto.class);
            // execute search
            result = jpaQuery.getResultList();
            return result;
        } else {

            Query consulta = em.getEm().createQuery(" from Produto p where p.descricao like :searchKey");


            consulta.setParameter("searchKey", "%" + searchKey + "%");


            return consulta.getResultList();
        }
    }
}
