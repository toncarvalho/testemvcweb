package org.jboss.as.testemvcweb.test;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.testemvcweb.data.ProdutoRepository;
import org.jboss.as.testemvcweb.model.Member;
import org.jboss.as.testemvcweb.model.Produto;
import org.jboss.as.testemvcweb.service.MemberRegistration;
import org.jboss.as.testemvcweb.util.*;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Created by tonca on 19/09/2015.
 */
@RunWith(Arquillian.class)
public class ProdutoRepositoryTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Produto.class, ProdutoRepository.class, Resources.class, CRUDBase.class, CRUDManager.class, ICrudBasic.class, ModelBase.class, HashCodeBuilder.class, EqualsBuilder.class)
                .addAsLibraries(Maven.resolver()
                        .loadPomFromFile("pom.xml")
                        .resolve("org.apache.commons:commons-lang3",
                                "commons-beanutils:commons-beanutils",
                                "org.jboss.logging:jboss-logging")
                        .withTransitivity()
                        .asFile())
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                        // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }


    @Inject
    ProdutoRepository produtoRepository;

    @Test
    public void testeProduto(){

        Produto produto = new Produto();
        produto.setCodigo("001");
        produto.setDescricao("Descricao do produto");

        produtoRepository.save(produto);

    }

}
