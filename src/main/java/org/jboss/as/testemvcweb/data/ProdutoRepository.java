package org.jboss.as.testemvcweb.data;

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

        /*try {
            FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(em.getEm());

            fullTextEntityManager = Search.getFullTextEntityManager(em.getEm());

            fullTextEntityManager.createIndexer().startAndWait();


            //em.getTransaction().begin();

        *//* create native Lucene query unsing the query DSL
         alternatively you can write the Lucene query using the Lucene query parser
         or the Lucene programmatic API. The Hibernate Search DSL is recommended though*//*
            QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Produto.class).get();
            org.apache.lucene.search.Query luceneQuery = qb.keyword().onFields("codigo", "descricao").matching(searchKey).createQuery();

            // wrap Lucene query in a javax.persistence.Query
            javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Produto.class);

            // execute search
            result = jpaQuery.getResultList();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;*/

        Query consulta = em.getEm().createQuery(" from Produto p where p.descricao like :searchKey");


        consulta.setParameter("searchKey", "%" + searchKey + "%");



        return consulta.getResultList();
    }
}
