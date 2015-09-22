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
public class CRUDManager implements Serializable{

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
        if (entity != null && entity.getId() != null) {
            T existing = em.find(clazz, entity.getId());
            if (existing != null) {
                // Necessário para que o Hibernate não tente inserir um registro novo. Segundo
                // a documentação ele deveria chegar a chave primária mas se a version não estiver
                // setada ele tenta inserir um novo registro em vez de atualizar o existente.
                entity.setVersion(existing.getVersion());
                System.out.println(entity);
                em.merge(entity);
                em.flush();
            } else {
                throw new IllegalArgumentException(String.format("Não existe registro de %s existente com id %d",
                        clazz.getSimpleName(), entity.getId()));
            }
        } else {
            throw new IllegalArgumentException(String.format("Dados da entidade %s devem existir e incluir o valor de id",
                    clazz.getSimpleName()));
        }
    }


    /**
     * Executa uma remoção de entidade do banco de dados.
     *
     * @param id Id da entidade a ser removida.
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
