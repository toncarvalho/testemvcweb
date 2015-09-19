package org.jboss.as.testemvcweb.model;

import org.jboss.as.testemvcweb.util.ModelBase;
import org.jboss.as.testemvcweb.util.ValidationMessage;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by tonca on 19/09/2015.
 */
@Entity
public class Produto extends ModelBase{



    @NotNull(message = ValidationMessage.NOT_NULL)
    @Size(min = 3, max = 20, message = ValidationMessage.SIZE)
    private String codigo;

    @NotNull(message = ValidationMessage.NOT_NULL)
    @Size(min = 3, max = 200, message = ValidationMessage.SIZE)
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }




}
