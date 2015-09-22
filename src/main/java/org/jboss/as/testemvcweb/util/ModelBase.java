package org.jboss.as.testemvcweb.util;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Classe  base que contem atributos padrão para todas as entidades que vierem a compor  o modelo
 */
@MappedSuperclass
public class ModelBase implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /*@Version
    protected Integer version;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }*/

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
