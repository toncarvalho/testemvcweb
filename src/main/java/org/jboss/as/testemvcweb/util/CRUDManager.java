package org.jboss.as.testemvcweb.util;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Classe de operações de CRUD para as entidades. Esta classe permite a realização simplificada das operações de criação, leitura, alteração
 * e exclusão de entidades do model.
 */
@Stateless
@LocalBean
public class CRUDManager implements Serializable {

    @Inject
    private EntityManager em;

    public CRUDManager() {
    }

    /**
     * Executa uma inserção no banco de dados.
     *
     * @param entity Entidade com os valores a serem inseridos.
     * @return Id da entidade criada.
     */
    public <T extends ModelBase> Long save(T entity) {
        em.persist(entity);
        em.flush();
        return entity.getId();
    }

    /**
     * Executa uma leitura do banco de dados.
     *
     * @param id Id da entidade a ser lida.
     * @return Entidade lida.
     */
    public <T extends ModelBase> T read(Class<T> clazz, Long id) {
        return em.find(clazz, id);
    }

    /**
     * Executa uma atualização de entidade no banco de dados.
     *
     * @param entity Entidade com os valores a serem alterados.
     */
    public <T extends ModelBase> void update(Class<T> clazz, T entity) {
        em.merge(entity);
        em.flush();
    }

    /**
     * Executa uma remoção de entidade do banco de dados.
     *
     */
    public <T extends ModelBase> void delete(Class<T> clazz, Long id) {
        T entity = em.find(clazz, id);
        em.remove(entity);
        em.flush();
    }


    public EntityManager getEm() {
        return em;
    }
}
