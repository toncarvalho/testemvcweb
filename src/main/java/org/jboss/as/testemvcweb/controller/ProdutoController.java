package org.jboss.as.testemvcweb.controller;

import org.jboss.as.testemvcweb.data.ProdutoRepository;
import org.jboss.as.testemvcweb.model.Produto;
import org.jboss.as.testemvcweb.util.IErrorMessageUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by tonca on 19/09/2015.
 */
@Model
@ManagedBean
public class ProdutoController implements IErrorMessageUtil {

    @Inject
    private FacesContext facesContext;

    @Inject
    private ProdutoRepository produtoRepository;

    private List<Produto> produtoList;

    private Produto produto;


    @Produces
    @Named
    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }


    public void onProdutosListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Produto produto) {
        retrieveAllProdutosOrderedByName();
    }

    @PostConstruct
    public void retrieveAllProdutosOrderedByName() {
        produtoList = produtoRepository.findAllOrderedByName();
    }

    public String novo() {

        System.out.println(" deve abrir o cadastro de produtos");

        return "cadastrodeproduto";

    }

    public void alterar(ActionEvent event) {
        System.out.println("Alterando dados");
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}

