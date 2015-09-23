package org.jboss.as.testemvcweb.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.jboss.as.testemvcweb.util.ModelBase;
import org.jboss.as.testemvcweb.util.ValidationMessage;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by tonca on 19/09/2015.
 */
@Entity
@Indexed
public class Produto extends ModelBase implements Serializable{

    @NotNull(message = ValidationMessage.NOT_NULL)
    @Size(min = 1, max = 200, message = ValidationMessage.SIZE)
    @Field(index = Index.YES, store = Store.YES)
    private String fabricante;

    @NotNull(message = ValidationMessage.NOT_NULL)
    @Size(min = 3, max = 200, message = ValidationMessage.SIZE)
    @Field(index = Index.YES, store = Store.YES)
    private String descricao;

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    @Override
    public String toString() {
        return "Produto{" +
                "fabricante='" + fabricante + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }



}
