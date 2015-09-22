package org.jboss.as.testemvcweb.model;

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
public class Produto extends ModelBase implements Serializable{

    @NotNull(message = ValidationMessage.NOT_NULL)
    @Size(min = 1, max = 20, message = ValidationMessage.SIZE)
    private String codigo;

    @NotNull(message = ValidationMessage.NOT_NULL)
    @Size(min = 3, max = 200, message = ValidationMessage.SIZE)
    private String descricao;

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


    @Override
    public String toString() {
        return "Produto{" +
                "codigo='" + codigo + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }


}
