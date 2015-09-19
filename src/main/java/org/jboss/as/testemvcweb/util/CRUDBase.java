package org.jboss.as.testemvcweb.util;

import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;


public abstract class CRUDBase<T extends ModelBase> {


    @Inject
    private EntityManager em;

    @Inject
    private CRUDManager crudManager;

    @Inject
    protected transient Logger logger;

    protected Class<T> persistentClass;

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void configPersistentClass() {
        try {
            persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (ClassCastException e) {
            // can be raised when DAO is inherited twice
            persistentClass = (Class<T>) ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }


    public abstract T novo();

    public T buscaPorId(Long id) {

        T entidade = null;
        entidade = em.find(persistentClass, id);
        if (entidade == null) {
            // throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return entidade;

    }

    public T incluir(T entidade) {
        em.persist(entidade);
        em.flush();
        return entidade;
    }


    public T atualizar(T entidade) {
        em.merge(entidade);
        em.flush();
        return entidade;
    }


    public void remover(Long id) {

        T ent = em.find(persistentClass, id);
        em.remove(ent);
        em.flush();

    }




}
